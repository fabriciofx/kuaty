/*
 * SPDX-FileCopyrightText: Copyright (C) 2018-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.kuaty.web;

import org.cactoos.io.ResourceOf;
import org.takes.Response;
import org.takes.facets.fork.RqRegex;
import org.takes.facets.fork.TkRegex;
import org.takes.rs.RsHtml;

/**
 * Page.
 *
 * @since 0.1
 */
public final class TkPage implements TkRegex {
    @Override
    public Response act(final RqRegex req) throws Exception {
        return new RsHtml(
            new ResourceOf(
                String.format(
                    "html/%s",
                    req.matcher().group("path")
                )
            ).stream()
        );
    }
}
