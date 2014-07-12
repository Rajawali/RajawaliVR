package rajawali.vr;

import rajawali.math.Matrix4;
import rajawali.math.Quaternion;
import rajawali.renderer.RajawaliSideBySideRenderer;
import android.content.Context;

import com.google.vrtoolkit.cardboard.HeadTransform;
import com.google.vrtoolkit.cardboard.sensors.HeadTracker;

public class RajawaliVRRenderer extends RajawaliSideBySideRenderer {
	protected HeadTracker mHeadTracker;
	protected HeadTransform mHeadTransform;
	protected float[] mHeadViewMatrix;
	protected Matrix4 mHeadViewMatrix4;
	private Quaternion mCameraOrientation;
	
	public RajawaliVRRenderer(Context context) {
		super(context);
		mHeadTransform = new HeadTransform();
		mHeadViewMatrix = new float[16];
		mHeadViewMatrix4 = new Matrix4();
		mCameraOrientation = new Quaternion();
	}
	
	@Override
	public void initScene() {
		super.initScene();
	}
	
	public void setHeadTracker(HeadTracker headTracker) {
		mHeadTracker = headTracker;
	
	}
	
	@Override
	public void onRender(double deltaTime) {
		mHeadTracker.getLastHeadView(mHeadViewMatrix, 0);
		mHeadViewMatrix4.setAll(mHeadViewMatrix);
		
		mCameraOrientation.fromMatrix(mHeadViewMatrix4);
		mCameraOrientation.x *= -1;
		mCameraOrientation.y *= -1;
		mCameraOrientation.z *= -1;
		setCameraOrientation(mCameraOrientation);
		super.onRender(deltaTime);
	}
}
