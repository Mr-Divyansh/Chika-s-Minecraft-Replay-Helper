/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avformat;

import org.bytedeco.ffmpeg.avutil.AVClass;
import org.bytedeco.ffmpeg.presets.avformat;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.FunctionPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avformat.class})
public class AVIOContext
extends Pointer {
    public AVIOContext() {
        super((Pointer)null);
        this.allocate();
    }

    public AVIOContext(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVIOContext(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVIOContext position(long position) {
        return (AVIOContext)super.position(position);
    }

    public AVIOContext getPointer(long i) {
        return (AVIOContext)new AVIOContext(this).offsetAddress(i);
    }

    @Const
    public native AVClass av_class();

    public native AVIOContext av_class(AVClass var1);

    @Cast(value={"unsigned char*"})
    public native BytePointer buffer();

    public native AVIOContext buffer(BytePointer var1);

    public native int buffer_size();

    public native AVIOContext buffer_size(int var1);

    @Cast(value={"unsigned char*"})
    public native BytePointer buf_ptr();

    public native AVIOContext buf_ptr(BytePointer var1);

    @Cast(value={"unsigned char*"})
    public native BytePointer buf_end();

    public native AVIOContext buf_end(BytePointer var1);

    public native Pointer opaque();

    public native AVIOContext opaque(Pointer var1);

    public native Read_packet_Pointer_BytePointer_int read_packet();

    public native AVIOContext read_packet(Read_packet_Pointer_BytePointer_int var1);

    public native Write_packet_Pointer_BytePointer_int write_packet();

    public native AVIOContext write_packet(Write_packet_Pointer_BytePointer_int var1);

    public native Seek_Pointer_long_int seek();

    public native AVIOContext seek(Seek_Pointer_long_int var1);

    @Cast(value={"int64_t"})
    public native long pos();

    public native AVIOContext pos(long var1);

    public native int eof_reached();

    public native AVIOContext eof_reached(int var1);

    public native int error();

    public native AVIOContext error(int var1);

    public native int write_flag();

    public native AVIOContext write_flag(int var1);

    public native int max_packet_size();

    public native AVIOContext max_packet_size(int var1);

    public native int min_packet_size();

    public native AVIOContext min_packet_size(int var1);

    @Cast(value={"unsigned long"})
    public native long checksum();

    public native AVIOContext checksum(long var1);

    @Cast(value={"unsigned char*"})
    public native BytePointer checksum_ptr();

    public native AVIOContext checksum_ptr(BytePointer var1);

    public native Update_checksum_long_BytePointer_int update_checksum();

    public native AVIOContext update_checksum(Update_checksum_long_BytePointer_int var1);

    public native Read_pause_Pointer_int read_pause();

    public native AVIOContext read_pause(Read_pause_Pointer_int var1);

    public native Read_seek_Pointer_int_long_int read_seek();

    public native AVIOContext read_seek(Read_seek_Pointer_int_long_int var1);

    public native int seekable();

    public native AVIOContext seekable(int var1);

    public native int direct();

    public native AVIOContext direct(int var1);

    @Cast(value={"const char*"})
    public native BytePointer protocol_whitelist();

    public native AVIOContext protocol_whitelist(BytePointer var1);

    @Cast(value={"const char*"})
    public native BytePointer protocol_blacklist();

    public native AVIOContext protocol_blacklist(BytePointer var1);

    public native Write_data_type_Pointer_BytePointer_int_int_long write_data_type();

    public native AVIOContext write_data_type(Write_data_type_Pointer_BytePointer_int_int_long var1);

    public native int ignore_boundary_point();

    public native AVIOContext ignore_boundary_point(int var1);

    @Cast(value={"unsigned char*"})
    public native BytePointer buf_ptr_max();

    public native AVIOContext buf_ptr_max(BytePointer var1);

    @Cast(value={"int64_t"})
    public native long bytes_read();

    public native AVIOContext bytes_read(long var1);

    @Cast(value={"int64_t"})
    public native long bytes_written();

    public native AVIOContext bytes_written(long var1);

    static {
        Loader.load();
    }

    public static class Write_data_type_Pointer_BytePointer_int_int_long
    extends FunctionPointer {
        public Write_data_type_Pointer_BytePointer_int_int_long(Pointer p) {
            super(p);
        }

        protected Write_data_type_Pointer_BytePointer_int_int_long() {
            this.allocate();
        }

        private native void allocate();

        public native int call(Pointer var1, @Cast(value={"const uint8_t*"}) BytePointer var2, int var3, @Cast(value={"AVIODataMarkerType"}) int var4, @Cast(value={"int64_t"}) long var5);

        static {
            Loader.load();
        }
    }

    public static class Read_seek_Pointer_int_long_int
    extends FunctionPointer {
        public Read_seek_Pointer_int_long_int(Pointer p) {
            super(p);
        }

        protected Read_seek_Pointer_int_long_int() {
            this.allocate();
        }

        private native void allocate();

        @Cast(value={"int64_t"})
        public native long call(Pointer var1, int var2, @Cast(value={"int64_t"}) long var3, int var5);

        static {
            Loader.load();
        }
    }

    public static class Read_pause_Pointer_int
    extends FunctionPointer {
        public Read_pause_Pointer_int(Pointer p) {
            super(p);
        }

        protected Read_pause_Pointer_int() {
            this.allocate();
        }

        private native void allocate();

        public native int call(Pointer var1, int var2);

        static {
            Loader.load();
        }
    }

    public static class Update_checksum_long_BytePointer_int
    extends FunctionPointer {
        public Update_checksum_long_BytePointer_int(Pointer p) {
            super(p);
        }

        protected Update_checksum_long_BytePointer_int() {
            this.allocate();
        }

        private native void allocate();

        @Cast(value={"unsigned long"})
        public native long call(@Cast(value={"unsigned long"}) long var1, @Cast(value={"const uint8_t*"}) BytePointer var3, @Cast(value={"unsigned int"}) int var4);

        static {
            Loader.load();
        }
    }

    public static class Seek_Pointer_long_int
    extends FunctionPointer {
        public Seek_Pointer_long_int(Pointer p) {
            super(p);
        }

        protected Seek_Pointer_long_int() {
            this.allocate();
        }

        private native void allocate();

        @Cast(value={"int64_t"})
        public native long call(Pointer var1, @Cast(value={"int64_t"}) long var2, int var4);

        static {
            Loader.load();
        }
    }

    public static class Write_packet_Pointer_BytePointer_int
    extends FunctionPointer {
        public Write_packet_Pointer_BytePointer_int(Pointer p) {
            super(p);
        }

        protected Write_packet_Pointer_BytePointer_int() {
            this.allocate();
        }

        private native void allocate();

        public native int call(Pointer var1, @Cast(value={"const uint8_t*"}) BytePointer var2, int var3);

        static {
            Loader.load();
        }
    }

    public static class Read_packet_Pointer_BytePointer_int
    extends FunctionPointer {
        public Read_packet_Pointer_BytePointer_int(Pointer p) {
            super(p);
        }

        protected Read_packet_Pointer_BytePointer_int() {
            this.allocate();
        }

        private native void allocate();

        public native int call(Pointer var1, @Cast(value={"uint8_t*"}) BytePointer var2, int var3);

        static {
            Loader.load();
        }
    }
}

