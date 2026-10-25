/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jogamp.opencl.CLImage2d
 */
package org.bytedeco.javacv;

import com.jogamp.opencl.CLImage2d;
import org.bytedeco.javacv.ImageAligner;

public interface ImageAlignerCL
extends ImageAligner {
    public CLImage2d getTemplateImageCL();

    public void setTemplateImageCL(CLImage2d var1, double[] var2);

    public CLImage2d getTargetImageCL();

    public void setTargetImageCL(CLImage2d var1);

    public CLImage2d getTransformedImageCL();

    public CLImage2d getResidualImageCL();

    public CLImage2d getMaskImageCL();

    public CLImage2d[] getImagesCL();
}

