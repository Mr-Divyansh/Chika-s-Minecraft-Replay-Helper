/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.maven.plugin.MojoExecutionException
 *  org.apache.maven.plugins.annotations.LifecyclePhase
 *  org.apache.maven.plugins.annotations.Mojo
 */
package org.bytedeco.javacpp.tools;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.bytedeco.javacpp.tools.BuildMojo;

@Mojo(name="parse", defaultPhase=LifecyclePhase.GENERATE_SOURCES, threadSafe=true)
public class ParseMojo
extends BuildMojo {
    @Override
    public void execute() throws MojoExecutionException {
        this.generate = false;
        super.execute();
    }
}

