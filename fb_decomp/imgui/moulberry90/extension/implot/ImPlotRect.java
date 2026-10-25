/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90.extension.implot;

import imgui.moulberry90.ImVec2;
import imgui.moulberry90.extension.implot.ImPlotPoint;
import imgui.moulberry90.extension.implot.ImPlotRange;
import java.util.Objects;

public final class ImPlotRect
implements Cloneable {
    public final ImPlotRange x = new ImPlotRange();
    public final ImPlotRange y = new ImPlotRange();

    public ImPlotRect() {
    }

    public ImPlotRect(double xMin, double yMin, double xMax, double yMax) {
        this.set(xMin, yMin, xMax, yMax);
    }

    public ImPlotRect(ImVec2 min, ImVec2 max) {
        this.set(min, max);
    }

    public ImPlotRect(ImPlotRect value) {
        this.set(value);
    }

    public boolean contains(double x, double y) {
        return this.x.contains(x) && this.y.contains(y);
    }

    public boolean contains(ImPlotPoint value) {
        return this.contains(value.x, value.y);
    }

    public ImPlotPoint size() {
        return new ImPlotPoint(this.x.size(), this.y.size());
    }

    public ImPlotPoint clamp(double x, double y) {
        return new ImPlotPoint(this.x.clamp(x), this.y.clamp(y));
    }

    public ImPlotPoint clamp(ImPlotPoint p) {
        return this.clamp(p.x, p.y);
    }

    public ImPlotPoint min() {
        return new ImPlotPoint(this.x.min, this.y.min);
    }

    public ImPlotPoint max() {
        return new ImPlotPoint(this.x.max, this.y.max);
    }

    public void set(double xMin, double yMin, double xMax, double yMax) {
        this.x.min = xMin;
        this.x.max = xMax;
        this.y.min = yMin;
        this.y.max = yMax;
    }

    public void set(ImVec2 min, ImVec2 max) {
        this.set(min.x, min.y, max.x, max.y);
    }

    public void set(ImPlotRange x, ImPlotRange y) {
        this.set(x.min, y.min, x.max, y.max);
    }

    public void set(ImPlotRect value) {
        this.set(value.x, value.y);
    }

    public String toString() {
        return "ImPlotRect{x=" + this.x + ", y=" + this.y + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ImPlotRect imPlotRect = (ImPlotRect)o;
        return Objects.equals(this.x, imPlotRect.x) && Objects.equals(this.y, imPlotRect.y);
    }

    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    public ImPlotRect clone() {
        return new ImPlotRect(this);
    }
}

