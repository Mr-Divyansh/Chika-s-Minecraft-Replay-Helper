/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.maven.plugin.AbstractMojo
 *  org.apache.maven.plugin.MojoExecutionException
 *  org.apache.maven.plugins.annotations.LifecyclePhase
 *  org.apache.maven.plugins.annotations.Mojo
 */
package org.bytedeco.javacpp.tools;

import java.io.IOException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.bytedeco.javacpp.Loader;

@Mojo(name="clear", defaultPhase=LifecyclePhase.NONE, requiresProject=false)
public class ClearMojo
extends AbstractMojo {
    public void execute() throws MojoExecutionException {
        try {
            Loader.clearCacheDir();
        }
        catch (IOException e) {
            throw new MojoExecutionException(e.toString());
        }
    }
}

