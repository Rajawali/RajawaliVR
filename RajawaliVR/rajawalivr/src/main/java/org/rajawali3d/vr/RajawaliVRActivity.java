package org.rajawali3d.vr;

import android.app.ActionBar;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.vrtoolkit.cardboard.CardboardActivity;
import com.google.vrtoolkit.cardboard.CardboardView;

import org.rajawali3d.vr.renderer.RajawaliVRRenderer;
import org.rajawali3d.vr.surface.RajawaliVRSurfaceView;

public class RajawaliVRActivity extends CardboardActivity {
    private CardboardView mSurfaceView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        mSurfaceView = new CardboardView(this);

        addContentView(mSurfaceView, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));

        mSurfaceView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
	            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
	            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
	            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
	            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
	            | View.SYSTEM_UI_FLAG_IMMERSIVE);

        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
	}
	
	protected void setRenderer(RajawaliVRRenderer renderer) {
	    mSurfaceView.setRenderer(renderer);
	}

    public CardboardView getSurfaceView() {
        return mSurfaceView;
    }
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
}

