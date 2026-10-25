/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avcodec;

import org.bytedeco.ffmpeg.avcodec.AVCodecContext;
import org.bytedeco.ffmpeg.avcodec.AVCodecParserContext;
import org.bytedeco.ffmpeg.presets.avcodec;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.FunctionPointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avcodec.class})
public class AVCodecParser
extends Pointer {
    public AVCodecParser() {
        super((Pointer)null);
        this.allocate();
    }

    public AVCodecParser(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVCodecParser(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVCodecParser position(long position) {
        return (AVCodecParser)super.position(position);
    }

    public AVCodecParser getPointer(long i) {
        return (AVCodecParser)new AVCodecParser(this).offsetAddress(i);
    }

    public native int codec_ids(int var1);

    public native AVCodecParser codec_ids(int var1, int var2);

    @MemberGetter
    public native IntPointer codec_ids();

    @Deprecated
    public native int priv_data_size();

    public native AVCodecParser priv_data_size(int var1);

    public native Parser_init_AVCodecParserContext parser_init();

    public native AVCodecParser parser_init(Parser_init_AVCodecParserContext var1);

    public native Parser_parse_AVCodecParserContext_AVCodecContext_PointerPointer_IntPointer_BytePointer_int parser_parse();

    public native AVCodecParser parser_parse(Parser_parse_AVCodecParserContext_AVCodecContext_PointerPointer_IntPointer_BytePointer_int var1);

    public native Parser_close_AVCodecParserContext parser_close();

    public native AVCodecParser parser_close(Parser_close_AVCodecParserContext var1);

    public native Split_AVCodecContext_BytePointer_int split();

    public native AVCodecParser split(Split_AVCodecContext_BytePointer_int var1);

    static {
        Loader.load();
    }

    public static class Split_AVCodecContext_BytePointer_int
    extends FunctionPointer {
        public Split_AVCodecContext_BytePointer_int(Pointer p) {
            super(p);
        }

        protected Split_AVCodecContext_BytePointer_int() {
            this.allocate();
        }

        private native void allocate();

        @Deprecated
        public native int call(AVCodecContext var1, @Cast(value={"const uint8_t*"}) BytePointer var2, int var3);

        static {
            Loader.load();
        }
    }

    public static class Parser_close_AVCodecParserContext
    extends FunctionPointer {
        public Parser_close_AVCodecParserContext(Pointer p) {
            super(p);
        }

        protected Parser_close_AVCodecParserContext() {
            this.allocate();
        }

        private native void allocate();

        @Deprecated
        public native void call(AVCodecParserContext var1);

        static {
            Loader.load();
        }
    }

    public static class Parser_parse_AVCodecParserContext_AVCodecContext_PointerPointer_IntPointer_BytePointer_int
    extends FunctionPointer {
        public Parser_parse_AVCodecParserContext_AVCodecContext_PointerPointer_IntPointer_BytePointer_int(Pointer p) {
            super(p);
        }

        protected Parser_parse_AVCodecParserContext_AVCodecContext_PointerPointer_IntPointer_BytePointer_int() {
            this.allocate();
        }

        private native void allocate();

        @Deprecated
        public native int call(AVCodecParserContext var1, AVCodecContext var2, @Cast(value={"const uint8_t**"}) PointerPointer var3, IntPointer var4, @Cast(value={"const uint8_t*"}) BytePointer var5, int var6);

        static {
            Loader.load();
        }
    }

    public static class Parser_init_AVCodecParserContext
    extends FunctionPointer {
        public Parser_init_AVCodecParserContext(Pointer p) {
            super(p);
        }

        protected Parser_init_AVCodecParserContext() {
            this.allocate();
        }

        private native void allocate();

        @Deprecated
        public native int call(AVCodecParserContext var1);

        static {
            Loader.load();
        }
    }
}

