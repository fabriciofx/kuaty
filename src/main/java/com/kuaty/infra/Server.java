/*
 * SPDX-FileCopyrightText: Copyright (C) 2018-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.kuaty.infra;

/**
 * Server.
 *
 * @since 0.1
 */
public interface Server {

    /**
     * Start the server.
     *
     * @throws Exception If something goes wrong
     */
    void start() throws Exception;

    /**
     * Stop the server.
     *
     * @throws Exception If something goes wrong
     */
    void stop() throws Exception;
}
