/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.tools;

import org.bytedeco.javacpp.tools.Slf4jLogger;

public class Logger {
    static boolean debug = false;

    public static Logger create(Class cls) {
        String s = System.getProperty("org.bytedeco.javacpp.logger", "").toLowerCase();
        if (s.equals("slf4j") || s.equals("slf4jlogger")) {
            return new Slf4jLogger(cls);
        }
        return new Logger();
    }

    public boolean isDebugEnabled() {
        return debug;
    }

    public boolean isInfoEnabled() {
        return true;
    }

    public boolean isWarnEnabled() {
        return true;
    }

    public boolean isErrorEnabled() {
        return true;
    }

    public void debug(String s) {
        System.err.println("Debug: " + s);
    }

    public void info(String s) {
        System.err.println("Info: " + s);
    }

    public void warn(String s) {
        System.err.println("Warning: " + s);
    }

    public void error(String s) {
        System.err.println("Error: " + s);
    }

    static {
        String s = System.getProperty("org.bytedeco.javacpp.logger.debug", "false").toLowerCase();
        debug = s.equals("true") || s.equals("t") || s.equals("");
    }
}

