/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.bytedeco.javacpp.tools;

import org.bytedeco.javacpp.tools.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jLogger
extends Logger {
    final org.slf4j.Logger logger;

    public Slf4jLogger(Class cls) {
        this.logger = LoggerFactory.getLogger((Class)cls);
    }

    @Override
    public boolean isDebugEnabled() {
        return this.logger.isDebugEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return this.logger.isInfoEnabled();
    }

    @Override
    public boolean isWarnEnabled() {
        return this.logger.isWarnEnabled();
    }

    @Override
    public boolean isErrorEnabled() {
        return this.logger.isErrorEnabled();
    }

    @Override
    public void debug(String s) {
        this.logger.debug(s);
    }

    @Override
    public void info(String s) {
        this.logger.info(s);
    }

    @Override
    public void warn(String s) {
        this.logger.warn(s);
    }

    @Override
    public void error(String s) {
        this.logger.error(s);
    }
}

