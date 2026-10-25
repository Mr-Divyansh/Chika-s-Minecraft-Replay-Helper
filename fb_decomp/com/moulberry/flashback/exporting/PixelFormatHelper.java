/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2BooleanMap
 *  it.unimi.dsi.fastutil.ints.Int2BooleanOpenHashMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 */
package com.moulberry.flashback.exporting;

import com.moulberry.flashback.Flashback;
import it.unimi.dsi.fastutil.ints.Int2BooleanMap;
import it.unimi.dsi.fastutil.ints.Int2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import java.util.HashMap;
import java.util.Map;
import org.bytedeco.ffmpeg.avcodec.AVCodec;
import org.bytedeco.ffmpeg.avutil.AVPixFmtDescriptor;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.IntPointer;

public class PixelFormatHelper {
    private static final Map<BestFormatKey, Integer> bestPixelFormats = new HashMap<BestFormatKey, Integer>();
    private static final Int2BooleanMap pixelFormatSupportsTransparency = new Int2BooleanOpenHashMap();
    private static final Int2ObjectMap<String> pixelFormatNames = new Int2ObjectOpenHashMap();

    public static int getBestPixelFormat(String codecName, int srcPixelFormat, boolean transparent) {
        BestFormatKey key = new BestFormatKey(codecName, srcPixelFormat, transparent);
        if (bestPixelFormats.containsKey(key)) {
            return bestPixelFormats.get(key);
        }
        int bestPixelFormat = PixelFormatHelper.calculateBestPixelFormat(codecName, srcPixelFormat, transparent);
        if (bestPixelFormat == -1) {
            throw new RuntimeException("Unable to determine best alternate pixel format for " + codecName);
        }
        if (bestPixelFormat != 0) {
            Flashback.LOGGER.info("Chose to use pixel format {} for codec {} with transparent={} and srcPixelFormat={}", new Object[]{PixelFormatHelper.pixelFormatToString(bestPixelFormat), codecName, transparent, PixelFormatHelper.pixelFormatToString(srcPixelFormat)});
        }
        bestPixelFormats.put(key, bestPixelFormat);
        return bestPixelFormat;
    }

    private static int calculateBestPixelFormat(String codecName, int srcPixelFormat, boolean transparent) {
        try (AVCodec codec = avcodec.avcodec_find_encoder_by_name(codecName);){
            int format;
            int pixFmt;
            IntArrayList supportedFormats = new IntArrayList();
            IntPointer pixFmts = codec.pix_fmts();
            if (pixFmts == null) {
                Flashback.LOGGER.info("Encoder {} does not provide a list of supported pixel formats, falling back to YUV420P", (Object)codecName);
                int n = 0;
                return n;
            }
            int index = 0;
            while ((pixFmt = pixFmts.get(index)) != -1) {
                if (!transparent && pixFmt == 0) {
                    int n = 0;
                    return n;
                }
                supportedFormats.add(pixFmt);
                ++index;
            }
            supportedFormats.add(-1);
            if (transparent && (format = avcodec.avcodec_find_best_pix_fmt_of_list(supportedFormats.toIntArray(), srcPixelFormat, 1, new int[1])) != -1) {
                int n = format;
                return n;
            }
            int n = avcodec.avcodec_find_best_pix_fmt_of_list(supportedFormats.toIntArray(), srcPixelFormat, 0, new int[1]);
            return n;
        }
    }

    public static boolean doesPixelFormatSupportTransparency(int pixelFormat) {
        if (pixelFormatSupportsTransparency.containsKey(pixelFormat)) {
            return pixelFormatSupportsTransparency.get(pixelFormat);
        }
        boolean supports = PixelFormatHelper.calculateDoesPixelFormatSupportTransparency(pixelFormat);
        pixelFormatSupportsTransparency.put(pixelFormat, supports);
        return supports;
    }

    private static boolean calculateDoesPixelFormatSupportTransparency(int pixelFormat) {
        try (AVPixFmtDescriptor descriptor = avutil.av_pix_fmt_desc_get(pixelFormat);){
            if (descriptor == null || descriptor.isNull()) {
                boolean bl = false;
                return bl;
            }
            boolean bl = (descriptor.flags() & 0x80L) != 0L;
            return bl;
        }
    }

    public static String pixelFormatToString(int pixelFormat) {
        if (pixelFormatNames.containsKey(pixelFormat)) {
            return (String)pixelFormatNames.get(pixelFormat);
        }
        String name = PixelFormatHelper.pixelFormatToStringInner(pixelFormat);
        pixelFormatNames.put(pixelFormat, (Object)name);
        return name;
    }

    private static String pixelFormatToStringInner(int pixelFormat) {
        String string;
        block8: {
            BytePointer name = avutil.av_get_pix_fmt_name(pixelFormat);
            try {
                string = name.getString();
                if (name == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if (name != null) {
                        try {
                            name.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (Throwable throwable3) {
                    return "UNKNOWN(" + pixelFormat + ")";
                }
            }
            name.close();
        }
        return string;
    }

    public static boolean isYuvFormat(int pixelFormat) {
        try (AVPixFmtDescriptor descriptor = avutil.av_pix_fmt_desc_get(pixelFormat);){
            if (descriptor == null || descriptor.isNull()) {
                throw new RuntimeException();
            }
            boolean bl = (descriptor.flags() & 0x20L) == 0L && descriptor.nb_components() >= 2;
            return bl;
        }
    }

    private record BestFormatKey(String codec, int srcPixelFormat, boolean transparent) {
    }
}

