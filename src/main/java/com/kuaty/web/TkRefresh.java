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

import java.io.File;
import java.io.IOException;
import org.takes.facets.fork.FkFixed;
import org.takes.facets.fork.FkHitRefresh;
import org.takes.facets.fork.TkFork;
import org.takes.tk.TkClasspath;
import org.takes.tk.TkFiles;
import org.takes.tk.TkWrap;
import com.jcabi.log.VerboseProcess;

/**
 * Refresh on hit.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id: 9794b044778737118ce2864c4687178453c86288 $
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
final class TkRefresh extends TkWrap {

    /**
     * Ctor.
     * @param path Path of files
     * @throws IOException If something goes wrong
     */
    TkRefresh(final String path) throws IOException {
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
