/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.StringSplitter
 *  net.minecraft.client.StringSplitter$LinePosConsumer
 *  net.minecraft.client.gui.Font
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 *  net.minecraft.resources.Identifier
 *  net.minecraft.resources.ResourceKey
 */
package com.moulberry.flashback;

import com.google.common.collect.Sets;
import com.moulberry.flashback.FlashbackTextComponents;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.Font;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

public class RegistryMetaHelper {
    private static final List<String> REGISTRY_ORDER = List.of("minecraft:block", "minecraft:item", "minecraft:entity_type", "minecraft:fluid", "minecraft:particle_type", "minecraft:block_entity_type", "minecraft:sound_event");

    public static LinkedHashMap<String, LinkedHashSet<String>> calculateNamespacesForRegistries() {
        LinkedHashMap<String, LinkedHashSet<String>> namespacesForRegistries = new LinkedHashMap<String, LinkedHashSet<String>>();
        for (Identifier registryName : BuiltInRegistries.REGISTRY.keySet()) {
            Registry registry = (Registry)BuiltInRegistries.REGISTRY.getValue(registryName);
            if (registry == null) continue;
            LinkedHashSet<String> namespaces = new LinkedHashSet<String>();
            for (Map.Entry entry : registry.entrySet()) {
                Identifier location = ((ResourceKey)entry.getKey()).identifier();
                String namespace = location.getNamespace();
                if (namespace.equals("minecraft") || namespace.equals("brigadier")) continue;
                namespaces.add(namespace);
            }
            if (namespaces.isEmpty()) continue;
            namespacesForRegistries.put(registryName.toString(), namespaces);
        }
        return namespacesForRegistries;
    }

    public static Component createMismatchWarning(LinkedHashMap<String, LinkedHashSet<String>> current, LinkedHashMap<String, LinkedHashSet<String>> replay) {
        MutableComponent mutableComponent = Component.empty();
        mutableComponent.append((Component)Component.translatable((String)"flashback.registry.mismatch1").withStyle(ChatFormatting.RED)).append(FlashbackTextComponents.NEWLINE);
        mutableComponent.append((Component)Component.translatable((String)"flashback.registry.mismatch2").withStyle(ChatFormatting.RED)).append(FlashbackTextComponents.NEWLINE);
        mutableComponent.append((Component)Component.translatable((String)"flashback.registry.mismatch3", (Object[])new Object[]{Component.translatable((String)"selectWorld.backupJoinSkipButton")}).withStyle(ChatFormatting.RED)).append(FlashbackTextComponents.NEWLINE);
        mutableComponent.append(FlashbackTextComponents.NEWLINE);
        record MismatchEntry(String registryName, LinkedHashSet<String> mismatchedElements) {
        }
        ArrayList<MismatchEntry> mismatches = new ArrayList<MismatchEntry>();
        for (String registryName : current.keySet()) {
            LinkedHashSet<String> replayEntries;
            if (!replay.containsKey(registryName)) {
                mismatches.add(new MismatchEntry(registryName, current.get(registryName)));
                continue;
            }
            LinkedHashSet<String> currentEntries = current.get(registryName);
            LinkedHashSet<String> mismatchesForRegistry = new LinkedHashSet<String>((Collection<String>)Sets.symmetricDifference(currentEntries, replayEntries = replay.get(registryName)));
            if (mismatchesForRegistry.isEmpty()) continue;
            mismatches.add(new MismatchEntry(registryName, mismatchesForRegistry));
        }
        for (String registryName : replay.keySet()) {
            if (current.containsKey(registryName)) continue;
            mismatches.add(new MismatchEntry(registryName, replay.get(registryName)));
        }
        mismatches.sort(Comparator.comparingInt(entry -> {
            int index = REGISTRY_ORDER.indexOf(entry.registryName);
            if (index < 0) {
                index = REGISTRY_ORDER.size();
            }
            return index;
        }));
        int maxRows = 15;
        int usedRows = 4;
        Font font = Minecraft.getInstance().font;
        StringSplitter splitter = font.getSplitter();
        Iterator iterator = mismatches.iterator();
        while (15 - usedRows >= 3 && iterator.hasNext()) {
            MismatchEntry entry2 = (MismatchEntry)iterator.next();
            mutableComponent.append((Component)Component.literal((String)("Registry: " + entry2.registryName + "\n")).withStyle(ChatFormatting.UNDERLINE));
            ++usedRows;
            StringBuilder missingElements = new StringBuilder();
            boolean first = true;
            for (String element : entry2.mismatchedElements) {
                if (first) {
                    first = false;
                } else {
                    missingElements.append(", ");
                }
                missingElements.append(element);
            }
            final ArrayList lines = new ArrayList();
            final String missingElementsStr = missingElements.toString();
            splitter.splitLines(missingElementsStr, 300, Style.EMPTY, false, new StringSplitter.LinePosConsumer(){

                public void accept(Style style, int from, int to) {
                    lines.add(missingElementsStr.substring(from, to));
                }
            });
            for (String line : lines) {
                if (15 - usedRows >= 2) {
                    mutableComponent.append((Component)Component.literal((String)(line + "\n")).withStyle(ChatFormatting.YELLOW));
                    ++usedRows;
                    continue;
                }
                mutableComponent.append((Component)Component.literal((String)"...and more elements\n").withStyle(ChatFormatting.YELLOW));
                ++usedRows;
            }
        }
        if (iterator.hasNext()) {
            int remaining = 0;
            while (iterator.hasNext()) {
                ++remaining;
                iterator.next();
            }
            mutableComponent.append("...and " + remaining + " more registries");
            ++usedRows;
        }
        return mutableComponent;
    }
}

