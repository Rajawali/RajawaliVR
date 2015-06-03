package org.rajawali3d.vr;

import android.app.ActionBar;
import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.vrtoolkit.cardboard.sensors.DeviceSensorLooper;
import com.google.vrtoolkit.cardboard.sensors.HeadTracker;
import com.google.vrtoolkit.cardboard.sensors.SystemClock;

import org.rajawali3d.surface.IRajawaliSurface;
import org.rajawali3d.surface.RajawaliSurfaceView;

public class RajawaliVRActivity extends Activity {
	private HeadTracker mHeadTracker;
    private RajawaliSurfaceView mSurfaceView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        mSurfaceView = new RajawaliSurfaceView(this);
        mSurfaceView.setFrameRate(60.0);
        mSurfaceView.setRenderMode(IRajawaliSurface.RENDERMODE_WHEN_DIRTY);

        addContentView(mSurfaceView, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));


        mSurfaceView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
	            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
	            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
	            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
	            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
	            | View.SYSTEM_UI_FLAG_IMMERSIVE);


        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

        mHeadTracker = new HeadTracker(new DeviceSensorLooper(sensorManager), new SystemClock(), display);
	}
	
	protected void setRenderer(RajawaliVRRenderer renderer) {
		renderer.setHeadTracker(mHeadTracker);
        mSurfaceView.setSurfaceRenderer(renderer);
	}

    public RajawaliSurfaceView getSurfaceView() {
        return mSurfaceView;
    }
	
	@Override
	public void onResume() {
		super.onResume();
		mHeadTracker.startTracking();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mHeadTracker.stopTracking();
	}
}

