/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.configuration.ClientConfigurationPacketListener
 *  net.minecraft.server.network.ConfigurationTask
 */
package com.moulberry.flashback.ext;

import java.util.List;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.configuration.ClientConfigurationPacketListener;
import net.minecraft.server.network.ConfigurationTask;

public interface ServerConfigurationPacketListenerImplExt {
    public void flashback$startConfiguration(List<Packet<? super ClientConfigurationPacketListener>> var1, List<ConfigurationTask> var2);
}

