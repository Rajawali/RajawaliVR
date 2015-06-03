package org.rajawali3d.vr.renderer;

import android.content.Context;
import android.view.MotionEvent;

import com.google.vrtoolkit.cardboard.CardboardView;
import com.google.vrtoolkit.cardboard.Eye;
import com.google.vrtoolkit.cardboard.HeadTransform;
import com.google.vrtoolkit.cardboard.Viewport;

import org.rajawali3d.renderer.RajawaliRenderer;
import org.rajawali3d.util.RajLog;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;

public class RajawaliVRRenderer extends RajawaliRenderer implements CardboardView.StereoRenderer {

	public RajawaliVRRenderer(Context context) {
		super(context);
	}
	
	@Override
	public void initScene() {

	}

	@Override
	public void onRender(long elapsedTime, double deltaTime) {
		super.onRender(elapsedTime, deltaTime);
	}

    @Override
    public void onOffsetsChanged(float v, float v2, float v3, float v4, int i, int i2) {

    }

    @Override
    public void onTouchEvent(MotionEvent motionEvent) {

    }

    @Override
    public void onNewFrame(HeadTransform headTransform) {

    }

    @Override
    public void onDrawEye(Eye eye) {
        super.onRenderFrame(null);
    }

    @Override
    public void onFinishFrame(Viewport viewport) {

    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        super.onRenderSurfaceSizeChanged(null, width, height);
    }

    @Override
    public void onSurfaceCreated(EGLConfig eglConfig) {
        super.onRenderSurfaceCreated(eglConfig, null, -1, -1);
    }

    @Override
    public void onRendererShutdown() {
        super.onRenderSurfaceDestroyed(null);
    }
}
