package org.rajawali3d.vr;

import org.rajawali3d.math.Matrix4;
import org.rajawali3d.math.Quaternion;
import android.content.Context;
import android.view.MotionEvent;

import com.google.vrtoolkit.cardboard.HeadTransform;
import com.google.vrtoolkit.cardboard.sensors.HeadTracker;

public class RajawaliVRRenderer extends RajawaliSideBySideRenderer {
	protected HeadTracker mHeadTracker;
	protected HeadTransform mHeadTransform;
	protected float[] mHeadViewMatrix;
	protected Matrix4 mHeadViewMatrix4;
	private Quaternion mCameraOrientation;
    private DistortionMeshManager mDistortionMeshManager;
	
	public RajawaliVRRenderer(Context context) {
		super(context);
		mHeadTransform = new HeadTransform();
		mHeadViewMatrix = new float[16];
		mHeadViewMatrix4 = new Matrix4();
		mCameraOrientation = new Quaternion();
	}
	
	@Override
	public void initScene() {
        mDistortionMeshManager = new DistortionMeshManager();
        setLeftMesh(mDistortionMeshManager.getLeftEyeDistortionMesh());
        setRightMesh(mDistortionMeshManager.getRightEyeDistortionMesh());
		super.initScene();
	}
	
	public void setHeadTracker(HeadTracker headTracker) {
		mHeadTracker = headTracker;
	
	}
	
	@Override
	public void onRender(long elapsedTime, double deltaTime) {
		mHeadTracker.getLastHeadView(mHeadViewMatrix, 0);
		mHeadViewMatrix4.setAll(mHeadViewMatrix);
		
		mCameraOrientation.fromMatrix(mHeadViewMatrix4);
		setCameraOrientation(mCameraOrientation);
		super.onRender(elapsedTime, deltaTime);
	}

    @Override
    public void onOffsetsChanged(float v, float v2, float v3, float v4, int i, int i2) {

    }

    @Override
    public void onTouchEvent(MotionEvent motionEvent) {

    }
}
