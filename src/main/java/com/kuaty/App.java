/*
 * SPDX-FileCopyrightText: Copyright (C) 2018-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.kuaty;

import com.kuaty.infra.WebServer;

/**
 * Starts the application.
 *
 * @since 0.1
 */
public final class App {

    /**
     * Ctor.
     */
    private App() {
    }

    /**
     * Main procedure.
     *
     * @param args Parameters used to configure the application
     * @throws Exception If something goes wrong
     */
    public static void main(final String... args) throws Exception {
        new WebServer(args).start();
    }
}
