/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.CommonComponents
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.ComponentContents
 *  net.minecraft.network.chat.contents.PlainTextContents
 */
package com.moulberry.flashback;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.UnsafeWrapper;
import com.moulberry.flashback.playback.ReplayServer;
import com.moulberry.flashback.state.EditorScene;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Random;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.contents.PlainTextContents;

public class Utils {
    private static final int SECOND_TO_TICKS = 20;
    private static final int MINUTE_TO_TICKS = 1200;
    private static final int HOUR_TO_TICKS = 72000;
    private static final int MINUTE_TO_SECONDS = 60;
    private static final int HOUR_TO_SECONDS = 3600;
    public static int exportSequenceCount = 1;

    public static <T> T chooseClosest(float position, float leftPosition, T left, float rightPosition, T right, float threshold) {
        if (left == null) {
            if (Math.abs(rightPosition - position) <= threshold) {
                return right;
            }
            return null;
        }
        if (right == null) {
            if (Math.abs(leftPosition - position) <= threshold) {
                return left;
            }
            return null;
        }
        return switch (Utils.findClosest(position, leftPosition, rightPosition, threshold).ordinal()) {
            default -> throw new MatchException(null, null);
            case 0 -> left;
            case 1 -> right;
            case 2 -> null;
        };
    }

    public static ClosestElement findClosest(float position, float leftPosition, float rightPosition, float threshold) {
        boolean rightValid;
        boolean leftValid = Math.abs(leftPosition - position) <= threshold;
        boolean bl = rightValid = Math.abs(rightPosition - position) <= threshold;
        if (leftValid && rightValid) {
            if (position <= leftPosition) {
                return ClosestElement.LEFT;
            }
            return ClosestElement.RIGHT;
        }
        if (leftValid) {
            return ClosestElement.LEFT;
        }
        if (rightValid) {
            return ClosestElement.RIGHT;
        }
        return ClosestElement.NONE;
    }

    public static float fovToFocalLength(float fov) {
        return 1.0f / (float)Math.tan(Math.toRadians(fov) * 0.5);
    }

    public static float focalLengthToFov(float focalLength) {
        return (float)Math.toDegrees(Math.atan(1.0 / (double)focalLength) * 2.0);
    }

    public static Random getInternalMathRandom() {
        for (Class<?> declaredClass : Math.class.getDeclaredClasses()) {
            try {
                if (!declaredClass.getSimpleName().endsWith("RandomNumberGeneratorHolder")) continue;
                Field field = declaredClass.getDeclaredField("randomNumberGenerator");
                if (field.trySetAccessible()) {
                    return (Random)field.get(null);
                }
                return (Random)UnsafeWrapper.UNSAFE.getObject(UnsafeWrapper.UNSAFE.staticFieldBase(field), UnsafeWrapper.UNSAFE.staticFieldOffset(field));
            }
            catch (Exception e) {
                Flashback.LOGGER.error("Failed to get internal Math random", (Throwable)e);
            }
        }
        return null;
    }

    public static int stringToTime(String string) {
        int numberStart = 0;
        int total = 0;
        char[] characters = string.toCharArray();
        for (int i = 0; i < characters.length; ++i) {
            char c = characters[i];
            if (c != 't' && c != 's' && c != 'm' && c != 'h') continue;
            try {
                double value = Double.parseDouble(string.substring(numberStart, i));
                int multiplier = switch (c) {
                    case 't' -> 1;
                    case 's' -> 20;
                    case 'm' -> 1200;
                    case 'h' -> 72000;
                    default -> throw new IllegalStateException("Unexpected value: " + c);
                };
                total += (int)(value * (double)multiplier);
                numberStart = ++i;
                continue;
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        if (numberStart < characters.length) {
            try {
                double value = Double.parseDouble(string.substring(numberStart));
                total += (int)(value * 20.0);
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return total;
    }

    public static String timeInTicksToString(int ticks) {
        StringBuilder builder = new StringBuilder();
        Utils.timeInTicksToString(builder, ticks);
        if (builder.isEmpty()) {
            return "0s";
        }
        return builder.toString();
    }

    private static void timeInTicksToString(StringBuilder builder, int ticks) {
        if (ticks >= 72000) {
            int hours = ticks / 72000;
            builder.append(hours).append('h');
            Utils.timeInTicksToString(builder, ticks - hours * 72000);
        } else if (ticks >= 1200) {
            int minutes = ticks / 1200;
            builder.append(minutes).append('m');
            Utils.timeInTicksToString(builder, ticks - minutes * 1200);
        } else if (ticks >= 20) {
            if (ticks % 20 == 0) {
                int seconds = ticks / 20;
                builder.append(seconds).append('s');
            } else {
                double seconds = (double)ticks / 20.0;
                builder.append(seconds).append('s');
            }
        } else if (ticks > 0) {
            builder.append(ticks).append('t');
        }
    }

    public static String timeInSecondsToString(int seconds) {
        StringBuilder builder = new StringBuilder();
        Utils.timeInSecondsToString(builder, seconds);
        if (builder.isEmpty()) {
            return "0s";
        }
        return builder.toString();
    }

    private static void timeInSecondsToString(StringBuilder builder, int seconds) {
        if (seconds >= 3600) {
            int hours = seconds / 3600;
            builder.append(hours).append('h');
            Utils.timeInSecondsToString(builder, seconds - hours * 3600);
        } else if (seconds >= 60) {
            int minutes = seconds / 60;
            builder.append(minutes).append('m');
            Utils.timeInSecondsToString(builder, seconds - minutes * 60);
        } else if (seconds > 0) {
            builder.append(seconds).append('s');
        }
    }

    public static boolean isComponentEmpty(Component component) {
        if (component == CommonComponents.EMPTY) {
            return true;
        }
        boolean isSelfEmpty = false;
        if (component.getContents() == PlainTextContents.EMPTY) {
            isSelfEmpty = true;
        } else {
            ComponentContents componentContents = component.getContents();
            if (componentContents instanceof PlainTextContents) {
                PlainTextContents plainTextContents = (PlainTextContents)componentContents;
                isSelfEmpty = ChatFormatting.stripFormatting((String)plainTextContents.text()).isEmpty();
            }
        }
        if (isSelfEmpty) {
            for (Component sibling : component.getSiblings()) {
                if (Utils.isComponentEmpty(sibling)) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String resolveFilenameTemplate(String template) {
        LocalDateTime dateTime = LocalDateTime.now();
        dateTime = dateTime.withNano(0);
        String date = dateTime.toLocalDate().toString();
        String time = dateTime.toLocalTime().toString();
        String replay = "unknown";
        ReplayServer replayServer = Flashback.getReplayServer();
        if (replayServer != null) {
            replay = replayServer.getMetadata().name;
        }
        String sceneName = "unknown";
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null) {
            long stamp = editorState.acquireRead();
            try {
                EditorScene scene = editorState.getCurrentScene(stamp);
                sceneName = scene.name;
            }
            finally {
                editorState.release(stamp);
            }
        }
        return template.replace("%date%", date).replace("%time%", time).replace("%replay%", replay).replace("%scene%", sceneName).replace("%seq%", "" + exportSequenceCount);
    }

    public static enum ClosestElement {
        LEFT,
        RIGHT,
        NONE;

    }
}

