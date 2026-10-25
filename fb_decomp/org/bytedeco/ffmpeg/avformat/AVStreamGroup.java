/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avformat;

import org.bytedeco.ffmpeg.avformat.AVIAMFAudioElement;
import org.bytedeco.ffmpeg.avformat.AVIAMFMixPresentation;
import org.bytedeco.ffmpeg.avformat.AVStream;
import org.bytedeco.ffmpeg.avformat.AVStreamGroupLCEVC;
import org.bytedeco.ffmpeg.avformat.AVStreamGroupTileGrid;
import org.bytedeco.ffmpeg.avutil.AVClass;
import org.bytedeco.ffmpeg.avutil.AVDictionary;
import org.bytedeco.ffmpeg.presets.avformat;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avformat.class})
public class AVStreamGroup
extends Pointer {
    public AVStreamGroup() {
        super((Pointer)null);
        this.allocate();
    }

    public AVStreamGroup(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVStreamGroup(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVStreamGroup position(long position) {
        return (AVStreamGroup)super.position(position);
    }

    public AVStreamGroup getPointer(long i) {
        return (AVStreamGroup)new AVStreamGroup(this).offsetAddress(i);
    }

    @Const
    public native AVClass av_class();

    public native AVStreamGroup av_class(AVClass var1);

    public native Pointer priv_data();

    public native AVStreamGroup priv_data(Pointer var1);

    @Cast(value={"unsigned int"})
    public native int index();

    public native AVStreamGroup index(int var1);

    @Cast(value={"int64_t"})
    public native long id();

    public native AVStreamGroup id(long var1);

    @Cast(value={"AVStreamGroupParamsType"})
    public native int type();

    public native AVStreamGroup type(int var1);

    @Name(value={"params.iamf_audio_element"})
    public native AVIAMFAudioElement params_iamf_audio_element();

    public native AVStreamGroup params_iamf_audio_element(AVIAMFAudioElement var1);

    @Name(value={"params.iamf_mix_presentation"})
    public native AVIAMFMixPresentation params_iamf_mix_presentation();

    public native AVStreamGroup params_iamf_mix_presentation(AVIAMFMixPresentation var1);

    @Name(value={"params.tile_grid"})
    public native AVStreamGroupTileGrid params_tile_grid();

    public native AVStreamGroup params_tile_grid(AVStreamGroupTileGrid var1);

    @Name(value={"params.lcevc"})
    public native AVStreamGroupLCEVC params_lcevc();

    public native AVStreamGroup params_lcevc(AVStreamGroupLCEVC var1);

    public native AVDictionary metadata();

    public native AVStreamGroup metadata(AVDictionary var1);

    @Cast(value={"unsigned int"})
    public native int nb_streams();

    public native AVStreamGroup nb_streams(int var1);

    public native AVStream streams(int var1);

    public native AVStreamGroup streams(int var1, AVStream var2);

    @Cast(value={"AVStream**"})
    public native PointerPointer streams();

    public native AVStreamGroup streams(PointerPointer var1);

    public native int disposition();

    public native AVStreamGroup disposition(int var1);

    static {
        Loader.load();
    }
}

