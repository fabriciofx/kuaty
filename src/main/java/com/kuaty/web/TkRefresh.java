/*
 * SPDX-FileCopyrightText: Copyright (C) 2018-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.kuaty.web;

import com.jcabi.log.VerboseProcess;
import java.io.File;
import org.takes.facets.fork.FkFixed;
import org.takes.facets.fork.FkHitRefresh;
import org.takes.facets.fork.TkFork;
import org.takes.tk.TkClasspath;
import org.takes.tk.TkFiles;
import org.takes.tk.TkWrap;

/**
 * Refresh on hit.
 *
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
final class TkRefresh extends TkWrap {
    /**
     * Ctor.
     * @param path Path of files
     * @throws Exception If something goes wrong
     */
    TkRefresh(final String path) throws Exception {
        super(
            new TkFork(
                new FkHitRefresh(
                    new File(path),
                    () -> new VerboseProcess(
                        new ProcessBuilder(
                            "mvn",
                            "generate-resources"
                        )
                    ).stdout(),
                    new TkFiles("./target/classes")
                ),
                new FkFixed(new TkClasspath())
            )
        );
    }
}
