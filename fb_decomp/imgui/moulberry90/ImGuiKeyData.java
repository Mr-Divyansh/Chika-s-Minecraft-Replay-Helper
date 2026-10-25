/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90;

import imgui.moulberry90.binding.ImGuiStructDestroyable;

public final class ImGuiKeyData
extends ImGuiStructDestroyable {
    public ImGuiKeyData() {
    }

    public ImGuiKeyData(long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return this.nCreate();
    }

    private native long nCreate();

    public boolean getDown() {
        return this.nGetDown();
    }

    public void setDown(boolean value) {
        this.nSetDown(value);
    }

    private native boolean nGetDown();

    private native void nSetDown(boolean var1);

    public float getDownDuration() {
        return this.nGetDownDuration();
    }

    public void setDownDuration(float value) {
        this.nSetDownDuration(value);
    }

    private native float nGetDownDuration();

    private native void nSetDownDuration(float var1);

    public float getDownDurationPrev() {
        return this.nGetDownDurationPrev();
    }

    public void setDownDurationPrev(float value) {
        this.nSetDownDurationPrev(value);
    }

    private native float nGetDownDurationPrev();

    private native void nSetDownDurationPrev(float var1);

    public float getAnalogValue() {
        return this.nGetAnalogValue();
    }

    public void setAnalogValue(float value) {
        this.nSetAnalogValue(value);
    }

    private native float nGetAnalogValue();

    private native void nSetAnalogValue(float var1);
}

