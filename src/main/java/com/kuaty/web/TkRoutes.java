/*
 * SPDX-FileCopyrightText: Copyright (C) 2018-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.kuaty.web;

import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.tk.TkClasspath;
import org.takes.tk.TkWithType;
import org.takes.tk.TkWrap;

/**
 * Web routes.
 *
 * @since 0.1
 */
public final class TkRoutes extends TkWrap {
    /**
     * Ctor.
     *
     * @throws Exception If something goes wrong
     */
    public TkRoutes() throws Exception {
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
