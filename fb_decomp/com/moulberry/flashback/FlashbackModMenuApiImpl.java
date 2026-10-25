/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.terraformersmc.modmenu.api.ConfigScreenFactory
 *  com.terraformersmc.modmenu.api.ModMenuApi
 */
package com.moulberry.flashback;

import com.moulberry.flashback.Flashback;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class FlashbackModMenuApiImpl
implements ModMenuApi {
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return Flashback::createConfigScreen;
    }
}

