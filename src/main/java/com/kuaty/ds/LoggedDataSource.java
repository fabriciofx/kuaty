/**
 * Copyright (c) 2017, kuaty.com - All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are PROHIBITED without prior written permission from
 * the author. This product may NOT be used anywhere and on any computer
 * except the server platform of kuaty Inc. located at www.kuaty.com.
 * Federal copyright law prohibits unauthorized reproduction by any means
 * and imposes fines up to $100,000 for violation. If you received
 * this code accidentally and without intent to use it, please report this
 * incident to the author by email.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package com.kuaty.ds;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * Logged DataSource.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class LoggedDataSource implements DataSource {
    private final DataSource origin;
    private final Logger logger;

    public LoggedDataSource(final DataSource dataSource) {
        this(dataSource, Logger.getLogger(LoggedDataSource.class.getName()));
    }

    public LoggedDataSource(final DataSource dataSource, final Logger logger) {
        this.origin = dataSource;
        this.logger = logger;
    }
    
    @Override
    public PrintWriter getLogWriter() throws SQLException {
        this.logger.info("#getLogWriter()");
        return this.origin.getLogWriter();
    }

    @Override
    public void setLogWriter(final PrintWriter out) throws SQLException {
        this.logger.info("#setLogWriter()");
        this.origin.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(final int seconds) throws SQLException {
        this.logger.info(
            new UncheckedText(
                new FormattedText(
                    "#setLoginTimeout(): adjusting login to %d seconds",
                    seconds
                )
            ).asString()
        );
        this.origin.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return this.origin.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return this.origin.getParentLogger();
    }

    @Override
    public <T> T unwrap(final Class<T> iface) throws SQLException {
        return this.origin.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(final Class<?> iface) throws SQLException {
        return this.isWrapperFor(iface);
    }

    @Override
    public Connection getConnection() throws SQLException {
        final Connection connection = this.origin.getConnection();
        final Properties props = connection.getClientInfo();
        for(final Object key : props.keySet()) {
            this.logger.info(
                new UncheckedText(
                    new FormattedText(
                        "#getConnection(): using property key: %s, value %s",
                        key,
                        props.get(key)
                    )
                ).asString()
            );
        }
        return connection;
    }

    @Override
    public Connection getConnection(final String username, final String password)
        throws SQLException {
        final Connection connection = this.origin.getConnection(username, password);
        final Properties props = connection.getClientInfo();
        for(final Object key : props.keySet()) {
            this.logger.info(
                new UncheckedText(
                    new FormattedText(
                        "#getConnection(username, password): using property key: %s, value %s",
                        key,
                        props.get(key)
                    )
                ).asString()
            );
        }
        return connection;
    }
}
