/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.seibel.distanthorizons.api.DhApi
 *  com.seibel.distanthorizons.api.DhApi$Delayed
 *  com.seibel.distanthorizons.api.interfaces.override.IDhApiOverrideable
 *  com.seibel.distanthorizons.api.interfaces.override.levelHandling.IDhApiSaveStructure
 *  com.seibel.distanthorizons.api.interfaces.world.IDhApiLevelWrapper
 *  com.seibel.distanthorizons.api.methods.events.DhApiEventRegister
 *  com.seibel.distanthorizons.api.methods.events.abstractEvents.DhApiLevelLoadEvent
 *  com.seibel.distanthorizons.api.methods.events.abstractEvents.DhApiLevelLoadEvent$EventParam
 *  com.seibel.distanthorizons.api.methods.events.abstractEvents.DhApiWorldLoadEvent
 *  com.seibel.distanthorizons.api.methods.events.abstractEvents.DhApiWorldLoadEvent$EventParam
 *  com.seibel.distanthorizons.api.methods.events.abstractEvents.DhApiWorldUnloadEvent
 *  com.seibel.distanthorizons.api.methods.events.abstractEvents.DhApiWorldUnloadEvent$EventParam
 *  com.seibel.distanthorizons.api.methods.events.interfaces.IDhApiEvent
 *  com.seibel.distanthorizons.api.methods.events.sharedParameterObjects.DhApiEventParam
 */
package com.moulberry.flashback.compat;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.playback.ReplayServer;
import com.seibel.distanthorizons.api.DhApi;
import com.seibel.distanthorizons.api.interfaces.override.IDhApiOverrideable;
import com.seibel.distanthorizons.api.interfaces.override.levelHandling.IDhApiSaveStructure;
import com.seibel.distanthorizons.api.interfaces.world.IDhApiLevelWrapper;
import com.seibel.distanthorizons.api.methods.events.DhApiEventRegister;
import com.seibel.distanthorizons.api.methods.events.abstractEvents.DhApiLevelLoadEvent;
import com.seibel.distanthorizons.api.methods.events.abstractEvents.DhApiWorldLoadEvent;
import com.seibel.distanthorizons.api.methods.events.abstractEvents.DhApiWorldUnloadEvent;
import com.seibel.distanthorizons.api.methods.events.interfaces.IDhApiEvent;
import com.seibel.distanthorizons.api.methods.events.sharedParameterObjects.DhApiEventParam;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DistantHorizonsSupport {
    private static boolean boundReplaySaveStructure = false;
    private static final ReplayDhSaveStructure REPLAY_SAVE_STRUCTURE = new ReplayDhSaveStructure();

    public static void register() {
        DhApiEventRegister.on(DhApiWorldLoadEvent.class, (IDhApiEvent)new DhApiWorldLoadEvent(){

            public void onWorldLoad(DhApiEventParam<DhApiWorldLoadEvent.EventParam> dhApiEventParam) {
                if (Flashback.isInReplay()) {
                    Flashback.LOGGER.info("Forcing Distant Horizons to read-only because we're inside a replay");
                    DhApi.Delayed.worldProxy.setReadOnly(true);
                    Flashback.LOGGER.info("Binding IDhApiSaveStructure to REPLAY_SAVE_STRUCTURE");
                    ReplayServer replayServer = Flashback.getReplayServer();
                    DistantHorizonsSupport.REPLAY_SAVE_STRUCTURE.pathOverrides.clear();
                    for (Map.Entry<String, File> entry : replayServer.getMetadata().distantHorizonPaths.entrySet()) {
                        if (!entry.getValue().exists()) continue;
                        DistantHorizonsSupport.REPLAY_SAVE_STRUCTURE.pathOverrides.put(entry.getKey(), entry.getValue());
                    }
                    DhApi.overrides.bind(IDhApiSaveStructure.class, (IDhApiOverrideable)REPLAY_SAVE_STRUCTURE);
                    boundReplaySaveStructure = true;
                }
            }
        });
        DhApiEventRegister.on(DhApiWorldUnloadEvent.class, (IDhApiEvent)new DhApiWorldUnloadEvent(){

            public void onWorldUnload(DhApiEventParam<DhApiWorldUnloadEvent.EventParam> dhApiEventParam) {
                if (boundReplaySaveStructure) {
                    Flashback.LOGGER.info("Unbinding IDhApiSaveStructure from REPLAY_SAVE_STRUCTURE");
                    DhApi.overrides.unbind(IDhApiSaveStructure.class, (IDhApiOverrideable)REPLAY_SAVE_STRUCTURE);
                    boundReplaySaveStructure = false;
                }
            }
        });
        DhApiEventRegister.on(DhApiLevelLoadEvent.class, (IDhApiEvent)new DhApiLevelLoadEvent(){

            public void onLevelLoad(DhApiEventParam<DhApiLevelLoadEvent.EventParam> dhApiEventParam) {
                if (Flashback.RECORDER != null) {
                    Flashback.RECORDER.putDistantHorizonsPaths(DistantHorizonsSupport.getDimensionPaths());
                }
            }
        });
    }

    public static Map<String, File> getDimensionPaths() {
        HashMap<String, File> paths = new HashMap<String, File>();
        for (IDhApiLevelWrapper level : DhApi.Delayed.worldProxy.getAllLoadedLevelWrappers()) {
            paths.put(level.getDimensionName(), level.getDhSaveFolder());
        }
        return paths;
    }

    private static class ReplayDhSaveStructure
    implements IDhApiSaveStructure {
        public final Map<String, File> pathOverrides = new HashMap<String, File>();

        private ReplayDhSaveStructure() {
        }

        public File overrideFilePath(File file, IDhApiLevelWrapper iDhApiLevelWrapper) {
            return this.pathOverrides.get(iDhApiLevelWrapper.getDimensionName());
        }
    }
}

