/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.Identifier
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.action;

import com.moulberry.flashback.action.Action;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

public class ActionRegistry {
    private static final List<Action> actions = new ArrayList<Action>();
    private static final Map<Identifier, Action> resourceToAction = new HashMap<Identifier, Action>();
    private static final Set<Class<? extends Action>> registeredActions = new HashSet<Class<? extends Action>>();

    public static void register(Action action) {
        if (!registeredActions.add(action.getClass())) {
            throw new IllegalArgumentException("Action already registered: " + String.valueOf(action.getClass()));
        }
        if (resourceToAction.containsKey(action.name())) {
            throw new IllegalArgumentException("Action already registered by name: " + String.valueOf(action.name()));
        }
        actions.add(action);
        resourceToAction.put(action.name(), action);
    }

    public static List<Action> getActions() {
        return Collections.unmodifiableList(actions);
    }

    @Nullable
    public static Action getAction(Identifier actionName) {
        return resourceToAction.get(actionName);
    }
}

