package org.rajawali3d.vr.surface;

/**
 * Copyright 2015 Dennis Ippel
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.View;

import com.google.vrtoolkit.cardboard.CardboardView;

import org.rajawali3d.surface.IRajawaliSurface;
import org.rajawali3d.surface.IRajawaliSurfaceRenderer;
import org.rajawali3d.util.Capabilities;
import org.rajawali3d.util.egl.RajawaliEGLConfigChooser;

/**
 *
 */
public class RajawaliVRSurfaceView extends CardboardView implements IRajawaliSurface {

    public RajawaliVRSurfaceView(Context context) {
        super(context);
    }

    public RajawaliVRSurfaceView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            onPause();
        } else {
            onResume();
        }
        super.onVisibilityChanged(changedView, visibility);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onResume();
    }

    @Override
    public void setFrameRate(double v) {

    }

    @Override
    public void setAntiAliasingMode(ANTI_ALIASING_CONFIG anti_aliasing_config) {

    }

    @Override
    public void setSampleCount(int i) {

    }

    @Override
    public void setSurfaceRenderer(IRajawaliSurfaceRenderer iRajawaliSurfaceRenderer) throws IllegalStateException {

    }

    @Override
    public void requestRenderUpdate() {
        requestRender();
    }
}
