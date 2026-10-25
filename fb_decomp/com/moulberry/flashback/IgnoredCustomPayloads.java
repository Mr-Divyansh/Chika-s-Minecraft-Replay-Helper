/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.Identifier
 */
package com.moulberry.flashback;

import com.moulberry.flashback.Flashback;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import net.minecraft.resources.Identifier;

public class IgnoredCustomPayloads {
    private static final Set<String> DEFAULT_IGNORED_NAMESPACES = Set.of("fabric-screen-handler-api");
    private static final Set<Identifier> DEFAULT_IGNORED_IDENTIFIERS = Set.of(Identifier.fromNamespaceAndPath((String)"fabric", (String)"recipe_sync"));
    private final HashSet<String> ignoredNamespaces = new HashSet<String>(DEFAULT_IGNORED_NAMESPACES);
    private final HashSet<Identifier> ignoredIdentifiers = new HashSet<Identifier>(DEFAULT_IGNORED_IDENTIFIERS);
    private String lastConfigString = null;

    public void setFromConfigString(String configString) {
        if (Objects.equals(configString, this.lastConfigString)) {
            return;
        }
        this.lastConfigString = configString;
        this.ignoredNamespaces.clear();
        this.ignoredNamespaces.addAll(DEFAULT_IGNORED_NAMESPACES);
        this.ignoredIdentifiers.clear();
        this.ignoredIdentifiers.addAll(DEFAULT_IGNORED_IDENTIFIERS);
        configString = configString.trim();
        for (String line : configString.split("\n")) {
            boolean reallow = (line = line.trim()).startsWith("+");
            if (reallow) {
                line = line.substring(1);
            }
            if ((line = line.trim()).isEmpty()) continue;
            if (line.endsWith(":*")) {
                String namespace = line.substring(0, line.length() - 2);
                if (namespace.isBlank()) continue;
                if (reallow) {
                    this.ignoredNamespaces.remove(namespace);
                    continue;
                }
                this.ignoredNamespaces.add(namespace);
                continue;
            }
            try {
                Identifier identifier = Identifier.parse((String)line);
                if (reallow) {
                    this.ignoredIdentifiers.remove(identifier);
                    continue;
                }
                this.ignoredIdentifiers.add(identifier);
            }
            catch (Exception e) {
                Flashback.LOGGER.error("Invalid identifier for ignored custom payload: {}", (Object)line);
            }
        }
    }

    public boolean isIgnored(Identifier identifier) {
        return this.ignoredNamespaces.contains(identifier.getNamespace()) || this.ignoredIdentifiers.contains(identifier);
    }
}

