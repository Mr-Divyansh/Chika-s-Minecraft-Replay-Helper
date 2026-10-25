/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.tools;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.tools.Logger;

public class CommandExecutor {
    final Logger logger;

    public CommandExecutor(Logger logger) {
        this.logger = logger;
    }

    public int executeCommand(List<String> command, File workingDirectory, Map<String, String> environmentVariables) throws IOException, InterruptedException {
        String platform = Loader.getPlatform();
        boolean windows = platform.startsWith("windows");
        for (int i = 0; i < command.size(); ++i) {
            Object arg = command.get(i);
            if (arg == null) {
                arg = "";
            }
            if (((String)arg).trim().isEmpty() && windows) {
                arg = "\"\"";
            }
            command.set(i, (String)arg);
        }
        String text = "";
        for (String s : command) {
            boolean hasSpaces;
            boolean bl = hasSpaces = s.indexOf(" ") > 0 || s.isEmpty();
            if (hasSpaces) {
                text = text + (windows ? "\"" : "'");
            }
            text = text + s;
            if (hasSpaces) {
                text = text + (windows ? "\"" : "'");
            }
            text = text + " ";
        }
        this.logger.info(text);
        ProcessBuilder pb = new ProcessBuilder(command);
        if (workingDirectory != null) {
            pb.directory(workingDirectory);
        }
        if (environmentVariables != null) {
            for (Map.Entry<String, String> e : environmentVariables.entrySet()) {
                if (e.getKey() == null || e.getValue() == null) continue;
                pb.environment().put(e.getKey(), e.getValue());
            }
        }
        return pb.inheritIO().start().waitFor();
    }
}

