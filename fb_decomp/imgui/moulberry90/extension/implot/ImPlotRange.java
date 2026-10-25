/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90.extension.implot;

import java.util.Objects;

public final class ImPlotRange
implements Cloneable {
    public double min;
    public double max;

    public ImPlotRange() {
    }

    public ImPlotRange(double min, double max) {
        this.set(min, max);
    }

    public ImPlotRange(ImPlotRange value) {
        this.set(value.min, value.max);
    }

    public boolean contains(double value) {
        return value >= this.min && value <= this.max;
    }

    public double size() {
        return this.max - this.min;
    }

    public double clamp(double value) {
        return Math.max(this.min, Math.min(this.max, value));
    }

    public ImPlotRange set(double min, double max) {
        this.min = min;
        this.max = max;
        return this;
    }

    public ImPlotRange set(ImPlotRange value) {
        return this.set(value.min, value.max);
    }

    public ImPlotRange plus(double min, double max) {
        this.min += min;
        this.max += max;
        return this;
    }

    public ImPlotRange plus(ImPlotRange value) {
        return this.plus(value.min, value.max);
    }

    public ImPlotRange minus(double min, double max) {
        this.min -= min;
        this.max -= max;
        return this;
    }

    public ImPlotRange minus(ImPlotRange value) {
        return this.minus(value.min, value.max);
    }

    public ImPlotRange times(double min, double max) {
        this.min *= min;
        this.max *= max;
        return this;
    }

    public ImPlotRange times(ImPlotRange value) {
        return this.times(value.min, value.max);
    }

    public String toString() {
        return "ImPlotRange{min=" + this.min + ", max=" + this.max + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ImPlotRange ImPlotRange2 = (ImPlotRange)o;
        return Double.compare(ImPlotRange2.min, this.min) == 0 && Double.compare(ImPlotRange2.max, this.max) == 0;
    }

    public int hashCode() {
        return Objects.hash(this.min, this.max);
    }

    public ImPlotRange clone() {
        return new ImPlotRange(this);
    }
}

