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

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.cactoos.Text;
import org.cactoos.text.UncheckedText;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 
 */
public final class FormattedXmlDataStream implements DataStream {
    private final XmlDataStream origin;
    
    public FormattedXmlDataStream(final XmlDataStream stream) {
        this.origin = stream;
    }
    
    @Override
    public String asString() throws IOException {
        final StringWriter sw = new StringWriter();
        try {
            final DocumentBuilder db = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder();
            final Document doc = db.parse(
                new InputSource(
                    new StringReader(this.origin.asString())
                )
            );
            final Transformer transformer = TransformerFactory
                .newInstance()
                .newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(
              "{http://xml.apache.org/xslt}indent-amount",
              "2"
            );
            transformer.transform(
                new DOMSource(doc),
                new StreamResult(sw)
            );
        } catch (
            final TransformerException |
            ParserConfigurationException |
            SAXException ex
        ) {
            throw new IOException(ex);
        }
        return sw.toString();
    }

    @Override
    public int compareTo(final Text text) {
        return this.origin.compareTo(text);
    }

    @Override
    public OutputStream stream() throws IOException {
        return this.origin.stream();
    }

    @Override
    public DataStream with(final String name, final Object value) {
        return this.origin.with(name, value);
    }

    @Override
    public DataStream merge(final DataStream stream) {
        return this.merge(stream);
    }
    
    @Override
    public String toString() {
        return new UncheckedText(this).asString();
    }
}
