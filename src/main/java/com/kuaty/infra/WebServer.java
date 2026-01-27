/*
 * SPDX-FileCopyrightText: Copyright (C) 2018-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.kuaty.infra;

import com.kuaty.web.TkRoutes;
import java.util.Arrays;
import java.util.List;
import org.takes.http.Exit;
import org.takes.http.FtCli;

/**
 * HTTP web server.
 *
 * @since 0.1
 */
public final class WebServer implements Server {
    /**
     * WebServer parameters.
     */
    private final List<String> params;

    /**
     * Ctor.
     *
     * @param parameters Parameters to the WebServer
     */
    public WebServer(final String... parameters) {
        this.params = Arrays.asList(parameters);
    }

    @Override
    public void start() throws Exception {
        new FtCli(new TkRoutes(), this.params).start(Exit.NEVER);
    }

    @Override
    public void stop() throws Exception {
        throw new UnsupportedOperationException("#stop");
    }
}
