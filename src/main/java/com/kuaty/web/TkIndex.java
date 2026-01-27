/*
 * SPDX-FileCopyrightText: Copyright (C) 2018-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.kuaty.web;

import org.cactoos.io.ResourceOf;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsHtml;

/**
 * Index.
 *
 * @since 0.1
 */
public final class TkIndex implements Take {
    @Override
    public Response act(final Request req) throws Exception {
        return new RsHtml(
            new ResourceOf(
                "html/index.html"
            ).stream()
        );
    }
}
