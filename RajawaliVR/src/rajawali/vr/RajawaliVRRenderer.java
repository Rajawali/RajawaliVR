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
	protected Quaternion cam1, cam2;
	
	public RajawaliVRRenderer(Context context) {
		super(context);
		mHeadTransform = new HeadTransform();
		mHeadViewMatrix = new float[16];
		mHeadViewMatrix4 = new Matrix4();
		cam1 = new Quaternion();
		cam2 = new Quaternion();
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
		
		cam1.fromMatrix(mHeadViewMatrix4);
		cam1.x *= -1;
		cam1.y *= -1;
		cam1.z *= -1;

		cam2.slerp(cam1, .3);
		setCameraOrientation(cam2);
		super.onRender(deltaTime);
	}
}
