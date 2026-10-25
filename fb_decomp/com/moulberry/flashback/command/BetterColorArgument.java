/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 *  net.minecraft.ChatFormatting
 *  net.minecraft.commands.SharedSuggestionProvider
 *  net.minecraft.network.chat.Component
 */
package com.moulberry.flashback.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;

public class BetterColorArgument
implements ArgumentType<Integer> {
    private static final Collection<String> EXAMPLES = Arrays.asList("red", "green");
    public static final DynamicCommandExceptionType ERROR_INVALID_VALUE = new DynamicCommandExceptionType(object -> Component.translatableEscape((String)"argument.color.invalid", (Object[])new Object[]{object}));

    private BetterColorArgument() {
    }

    public static BetterColorArgument color() {
        return new BetterColorArgument();
    }

    public Integer parse(StringReader stringReader) {
        String string = stringReader.readUnquotedString();
        ChatFormatting chatFormatting = ChatFormatting.getByName((String)string);
        if (chatFormatting != null && chatFormatting.getColor() != null) {
            return chatFormatting.getColor();
        }
        if (string.startsWith("#")) {
            string = string.substring(1);
        }
        return Integer.parseInt(string.trim(), 16);
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        return SharedSuggestionProvider.suggest((Iterable)ChatFormatting.getNames((boolean)true, (boolean)false), (SuggestionsBuilder)suggestionsBuilder);
    }

    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}

