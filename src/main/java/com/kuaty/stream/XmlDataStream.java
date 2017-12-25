/**
 * Copyright (c) 2017, kuaty.com - All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are PROHIBITED without prior written permission from
 * the author. This product may NOT be used anywhere and on any computer
 * except the server platform of kuaty Inc. located at www.kuaty.com.
 * Federal copyright law prohibits unauthorized reproduction by any means
 * and imposes fines up to $100,000 for violation. If you received
 * this code accidentally and without intent to use it, please report this
 * incident to the author by email.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package com.kuaty.stream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.cactoos.Text;
import org.cactoos.scalar.UncheckedScalar;
import org.cactoos.text.UncheckedText;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 * 
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 
 */
public final class XmlDataStream implements DataStream {
    private UncheckedScalar<Document> doc;

    public XmlDataStream(final String root) {
        this(
            new UncheckedScalar<>(
                () -> {
                    final Document xml = DocumentBuilderFactory
                        .newInstance()
                        .newDocumentBuilder()
                        .newDocument();
                    xml.appendChild(xml.createElement(root));
                    return xml;
                }
            )
        );
    }

    public XmlDataStream(final InputStream input) {
        this(
            new UncheckedScalar<>(
                () -> {
                    return DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(input);
                }
            )
         );
    }

    public XmlDataStream(final Document document) {
        this(new UncheckedScalar<>(() -> document));
    }

    public XmlDataStream(final UncheckedScalar<Document> document) {
        this.doc = document;
    }

    @Override
    public OutputStream stream() throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(this.asString().getBytes());
        return baos;
    }

    @Override
    public DataStream with(final String name, final Object value) {
        final Document doc = this.doc.value();
        final Element root = doc.getDocumentElement();
        final Element element = doc.createElement(name);
        element.appendChild(doc.createTextNode(value.toString()));
        root.appendChild(element);
        return new XmlDataStream(doc);
    }

    @Override
    public DataStream merge(final DataStream stream) {
        try {
            final DocumentBuilder db = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder();
            final Document origin = this.doc.value();
            final Node originRoot = origin.getDocumentElement();
            final Document copied = db.newDocument();
            final Node copiedRoot = copied.importNode(originRoot, true);
            copied.appendChild(copiedRoot);
            final Document inserted = db.parse(
                new InputSource(
                    new StringReader(stream.toString())
                )
            );
            final Node src = inserted.getDocumentElement();
            final Node dest = copied.getDocumentElement();
            final Node root = copied.importNode(src, true);
            dest.appendChild(root);
            return new XmlDataStream(copied);
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String asString() throws IOException {
        final StringWriter sw = new StringWriter();
        try {
            final Transformer transformer = TransformerFactory
                .newInstance()
                .newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(
                new DOMSource(this.doc.value()),
                new StreamResult(sw)
            );
        } catch (final TransformerException ex) {
            throw new IOException(ex);
        }
        return sw.toString();
    }

    @Override
    public int compareTo(final Text text) {
        return new UncheckedText(this).compareTo(text);
    }

    @Override
    public String toString() {
        return new UncheckedText(this).asString();
    }
}
