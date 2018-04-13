/**
 * Proprietary License
 *
 * Copyright (c) 2018 Kuaty Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are PROHIBITED without prior written permission from the
 * author. This product may NOT be used anywhere and on any computer except the
 * server platform of Kuaty Inc., located at www.kuaty.com. Federal copyright
 * law prohibits unauthorized reproduction by any means and imposes fines up to
 * $300,000 for violation. If you received this code accidentally and without
 * intent to use it, please report this incident to the author by email.
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.kuaty.web;

import com.kuaty.app.Server;
import java.io.IOException;
import org.takes.http.Exit;
import org.takes.http.FtCli;

/**
 * HTTP web server.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class WebServer implements Server {
    /**
     * WebServer parameters.
     */
    private final String[] params;

    /**
     * Ctor.
     *
     * @param parameters Parameters to the WebServer
     */
    public WebServer(final String... parameters) {
        this.params = parameters;
    }

    @Override
    public void start() throws IOException {
        new FtCli(new TkRoutes(), this.params).start(Exit.NEVER);
    }

    @Override
    public void stop() throws IOException {
        throw new UnsupportedOperationException("#stop");
    }
}
