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
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.cactoos.Text;
import org.cactoos.text.SplitText;
import org.cactoos.text.UncheckedText;

/**
 * 
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 
 */
public final class TextDataStream implements DataStream {
    private final String subject;
    private final String delimiter;
    private final String eol;
    private final Map<String, String> data;

    public TextDataStream(final String subject) {
        this(subject, "\n");
    }

    public TextDataStream(final String subject, final String eol) {
        this(subject, "=", eol);
    }

    public TextDataStream(
        final String subject,
        final String delimiter,
        final String eol
    ) {
        this(subject, delimiter, eol, new HashMap<>());
    }

    public TextDataStream(
        final String subject,
        final String delimiter,
        final String eol,
        final Map<String, String> data
    ) {
        this.subject = subject;
        this.delimiter = delimiter;
        this.eol = eol;
        this.data = data;
    }

    @Override
    public OutputStream stream() throws IOException {
        final byte[] bytes = this.asString().getBytes();
        final OutputStream baos = new ByteArrayOutputStream(bytes.length);
        baos.write(bytes, 0, bytes.length);
        return baos;
    }

    @Override
    public DataStream with(final String name, final Object value) {
        final Map<String, String> data = new HashMap<>(this.data);        
        data.put(name, value.toString());
        return new TextDataStream(this.subject, this.delimiter, this.eol, data);
    }

    @Override
    public DataStream merge(final DataStream stream) {
        final Iterator<Text> iter = new SplitText(stream, this.eol).iterator();
        final Map<String, String> merged = new HashMap<>(this.data);
        while(iter.hasNext()) {
            final String[] tokens = new UncheckedText(
                iter.next()
            ).asString().split(this.delimiter);
            merged.put(tokens[0], tokens[1]);
        }
        return new TextDataStream(this.subject, this.delimiter, this.eol, merged);
    }

    @Override
    public int compareTo(final Text text) {
        return new UncheckedText(this).compareTo(text);
    }

    @Override
    public String asString() throws IOException {
        final StringBuilder sb = new StringBuilder(0);
        for (final String key : this.data.keySet()) {
            sb.append(
                String.format(
                    "%s.%s%s%s%s",
                    this.subject,
                    key,
                    this.delimiter,
                    this.data.get(key),
                    this.eol
                )
            );
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return new UncheckedText(this).asString();
    }
}
