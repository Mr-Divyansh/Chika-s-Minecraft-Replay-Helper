/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.avutil.AVOption;
import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.FunctionPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVClass
extends Pointer {
    public AVClass() {
        super((Pointer)null);
        this.allocate();
    }

    public AVClass(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVClass(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVClass position(long position) {
        return (AVClass)super.position(position);
    }

    public AVClass getPointer(long i) {
        return (AVClass)new AVClass(this).offsetAddress(i);
    }

    @Cast(value={"const char*"})
    public native BytePointer class_name();

    public native AVClass class_name(BytePointer var1);

    public native Item_name_Pointer item_name();

    public native AVClass item_name(Item_name_Pointer var1);

    @Const
    public native AVOption option();

    public native AVClass option(AVOption var1);

    public native int version();

    public native AVClass version(int var1);

    public native int log_level_offset_offset();

    public native AVClass log_level_offset_offset(int var1);

    public native int parent_log_context_offset();

    public native AVClass parent_log_context_offset(int var1);

    @Cast(value={"AVClassCategory"})
    public native int category();

    public native AVClass category(int var1);

    public native Get_category_Pointer get_category();

    public native AVClass get_category(Get_category_Pointer var1);

    public native Query_ranges_PointerPointer_Pointer_BytePointer_int query_ranges();

    public native AVClass query_ranges(Query_ranges_PointerPointer_Pointer_BytePointer_int var1);

    public native Child_next_Pointer_Pointer child_next();

    public native AVClass child_next(Child_next_Pointer_Pointer var1);

    public native Child_class_iterate_PointerPointer child_class_iterate();

    public native AVClass child_class_iterate(Child_class_iterate_PointerPointer var1);

    public native int state_flags_offset();

    public native AVClass state_flags_offset(int var1);

    static {
        Loader.load();
    }

    public static class Child_class_iterate_PointerPointer
    extends FunctionPointer {
        public Child_class_iterate_PointerPointer(Pointer p) {
            super(p);
        }

        protected Child_class_iterate_PointerPointer() {
            this.allocate();
        }

        private native void allocate();

        @Const
        public native AVClass call(@Cast(value={"void**"}) PointerPointer var1);

        static {
            Loader.load();
        }
    }

    public static class Child_next_Pointer_Pointer
    extends FunctionPointer {
        public Child_next_Pointer_Pointer(Pointer p) {
            super(p);
        }

        protected Child_next_Pointer_Pointer() {
            this.allocate();
        }

        private native void allocate();

        public native Pointer call(Pointer var1, Pointer var2);

        static {
            Loader.load();
        }
    }

    public static class Query_ranges_PointerPointer_Pointer_BytePointer_int
    extends FunctionPointer {
        public Query_ranges_PointerPointer_Pointer_BytePointer_int(Pointer p) {
            super(p);
        }

        protected Query_ranges_PointerPointer_Pointer_BytePointer_int() {
            this.allocate();
        }

        private native void allocate();

        public native int call(@Cast(value={"AVOptionRanges**"}) PointerPointer var1, Pointer var2, @Cast(value={"const char*"}) BytePointer var3, int var4);

        static {
            Loader.load();
        }
    }

    public static class Get_category_Pointer
    extends FunctionPointer {
        public Get_category_Pointer(Pointer p) {
            super(p);
        }

        protected Get_category_Pointer() {
            this.allocate();
        }

        private native void allocate();

        @Cast(value={"AVClassCategory"})
        public native int call(Pointer var1);

        static {
            Loader.load();
        }
    }

    public static class Item_name_Pointer
    extends FunctionPointer {
        public Item_name_Pointer(Pointer p) {
            super(p);
        }

        protected Item_name_Pointer() {
            this.allocate();
        }

        private native void allocate();

        @Cast(value={"const char*"})
        public native BytePointer call(Pointer var1);

        static {
            Loader.load();
        }
    }
}

