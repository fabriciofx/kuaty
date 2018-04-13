/**
 * Proprietary License
 *
 * Copyright (c) 2018, Kuaty Inc. - All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are PROHIBITED without prior written permission from the
 * author. This product may NOT be used anywhere and on any computer except the
 * server platform of Kuaty Inc. located at www.kuaty.com. Federal copyright
 * law prohibits unauthorized reproduction by any means and imposes fines up to
 * $300,000 for violation. If you received this code accidentally and without
 * intent to use it, please report this incident to the author by email.
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

import java.io.IOException;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.tk.TkClasspath;
import org.takes.tk.TkWithType;
import org.takes.tk.TkWrap;

/**
 * Web routes.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class TkRoutes extends TkWrap {

    /**
     * Ctor.
     *
     * @throws IOException If something goes wrong
     */
    public TkRoutes() throws IOException {
        super(
            new TkFork(
                new FkRegex(
                    "/robots.txt",
                    ""
                ),
                new FkRegex(
                    "/css/.+\\.css",
                    new TkWithType(
                        new TkClasspath("/html"),
                        "text/css"
                    )
                ),
                new FkRegex(
                    "/images/[a-z]+\\.jpg",
                    new TkWithType(
                        new TkRefresh("./src/main/resources"),
                        "image/png"
                    )
                ),
                new FkRegex(
                    "/",
                    new TkIndex()
                ),
                new FkRegex(
                    "/(?<path>[^/]+)",
                    new TkPage()
                )
            )
        );
    }
}
