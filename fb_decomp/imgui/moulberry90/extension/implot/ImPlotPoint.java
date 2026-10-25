/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90.extension.implot;

import imgui.moulberry90.ImVec2;
import java.util.Objects;

public final class ImPlotPoint
implements Cloneable {
    public double x;
    public double y;

    public ImPlotPoint() {
    }

    public ImPlotPoint(double x, double y) {
        this.set(x, y);
    }

    public ImPlotPoint(ImVec2 value) {
        this.set(value.x, value.y);
    }

    public ImPlotPoint(ImPlotPoint value) {
        this.set(value.x, value.y);
    }

    public ImPlotPoint set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public ImPlotPoint set(ImPlotPoint value) {
        return this.set(value.x, value.y);
    }

    public ImPlotPoint set(ImVec2 value) {
        return this.set(value.x, value.y);
    }

    public ImPlotPoint plus(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public ImPlotPoint plus(ImPlotPoint value) {
        return this.plus(value.x, value.y);
    }

    public ImPlotPoint minus(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public ImPlotPoint minus(ImPlotPoint value) {
        return this.minus(value.x, value.y);
    }

    public ImPlotPoint times(double x, double y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    public ImPlotPoint times(ImPlotPoint value) {
        return this.times(value.x, value.y);
    }

    public String toString() {
        return "ImPlotPoint{x=" + this.x + ", y=" + this.y + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ImPlotPoint imPlotPoint = (ImPlotPoint)o;
        return Double.compare(imPlotPoint.x, this.x) == 0 && Double.compare(imPlotPoint.y, this.y) == 0;
    }

    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    public ImPlotPoint clone() {
        return new ImPlotPoint(this);
    }
}

