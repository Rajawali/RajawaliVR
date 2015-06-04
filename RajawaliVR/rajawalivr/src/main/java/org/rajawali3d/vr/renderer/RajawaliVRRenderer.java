package org.rajawali3d.vr.renderer;

import android.content.Context;
import android.opengl.Matrix;
import android.view.MotionEvent;

import com.google.vrtoolkit.cardboard.CardboardView;
import com.google.vrtoolkit.cardboard.Eye;
import com.google.vrtoolkit.cardboard.HeadTransform;
import com.google.vrtoolkit.cardboard.Viewport;

import org.rajawali3d.Object3D;
import org.rajawali3d.math.Matrix4;
import org.rajawali3d.math.Quaternion;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.renderer.RajawaliRenderer;
import org.rajawali3d.util.RajLog;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;

public class RajawaliVRRenderer extends RajawaliRenderer implements CardboardView.StereoRenderer {
    private static final float YAW_LIMIT = 0.12f;
    private static final float PITCH_LIMIT = 0.12f;

    protected Matrix4 mCurrentEyeMatrix;
    protected Matrix4 mHeadViewMatrix;
    protected Quaternion mCurrentEyeOrientation;

    private Matrix4 mLookingAtMatrix;
    private float[] mHeadView;

	public RajawaliVRRenderer(Context context) {
		super(context);
        mCurrentEyeMatrix = new Matrix4();
        mHeadViewMatrix = new Matrix4();
        mLookingAtMatrix = new Matrix4();
        mCurrentEyeOrientation = new Quaternion();
        mHeadView = new float[16];
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
        headTransform.getHeadView(mHeadView, 0);
    }

    @Override
    public void onDrawEye(Eye eye) {
        mCurrentEyeMatrix.setAll(eye.getEyeView());
        mCurrentEyeOrientation.fromMatrix(mCurrentEyeMatrix);
        getCurrentCamera().setOrientation(mCurrentEyeOrientation);
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

    public boolean isLookingAtObject(Object3D target) {
        return this.isLookingAtObject(target, PITCH_LIMIT, YAW_LIMIT);
    }

    public boolean isLookingAtObject(Object3D target, float pitchLimit, float yawLimit) {
        mLookingAtMatrix.setAll(target.getModelViewMatrix());
        Vector3 forward = new Vector3(0, 0, 1);
        forward.multiply(mLookingAtMatrix);

        float pitch = (float) Math.atan2(forward.y, -forward.z);
        float yaw = (float) Math.atan2(forward.x, -forward.z);

        return Math.abs(pitch) < pitchLimit && Math.abs(yaw) < yawLimit;
    }
}
