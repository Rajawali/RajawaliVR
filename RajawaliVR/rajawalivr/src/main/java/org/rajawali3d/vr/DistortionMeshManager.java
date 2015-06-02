package org.rajawali3d.vr;

import com.google.vrtoolkit.cardboard.CardboardDeviceParams;
import com.google.vrtoolkit.cardboard.FieldOfView;
import com.google.vrtoolkit.cardboard.HeadMountedDisplay;
import com.google.vrtoolkit.cardboard.ScreenParams;
import com.google.vrtoolkit.cardboard.Viewport;

public class DistortionMeshManager {
    private float mResolutionScale = 1.0f;
    private boolean mChromaticAberrationCorrectionEnabled;
    private boolean mVignetteEnabled;
    private DistortionMesh mLeftEyeDistortionMesh;
    private DistortionMesh mRightEyeDistortionMesh;
    private HeadMountedDisplay mHmd;
    private EyeViewport mLeftEyeViewport;
    private EyeViewport mRightEyeViewport;
    private boolean mFovsChanged;
    private boolean mViewportsChanged;
    private float mXPxPerTanAngle;
    private float mYPxPerTanAngle;
    private float mMetersPerTanAngle;

    public void beforeDrawFrame() {
        if (mFovsChanged) {
            updateDistortionMesh(false);
            mFovsChanged = false;
        }
    }

    public void setResolutionScale(float scale) {
        mResolutionScale = scale;
        mViewportsChanged = true;
    }

    public void setChromaticAberrationCorrectionEnabled(boolean enabled) {
        mChromaticAberrationCorrectionEnabled = enabled;
    }

    public void setVignetteEnabled(boolean enabled) {
        mVignetteEnabled = enabled;
        mFovsChanged = true;
    }

    public void onFovChanged(HeadMountedDisplay hmd, FieldOfView leftFov, FieldOfView rightFov, float virtualEyeToScreenDistance) {
        mHmd = new HeadMountedDisplay(hmd);
        mLeftEyeViewport = initViewportForEye(leftFov, 0.0f);
        mRightEyeViewport = initViewportForEye(rightFov, mLeftEyeViewport.width);
        mMetersPerTanAngle = virtualEyeToScreenDistance;
        ScreenParams screen = hmd.getScreenParams();
        mXPxPerTanAngle = (float)screen.getWidth() / (screen.getWidthMeters() / mMetersPerTanAngle);
        mYPxPerTanAngle = (float)screen.getHeight() / (screen.getHeightMeters() / mMetersPerTanAngle);
        mFovsChanged = true;
        mViewportsChanged = true;
    }

    public boolean haveViewportsChanged() {
        return mViewportsChanged;
    }

    public void updateViewports(Viewport leftViewport, Viewport rightViewport) {
        leftViewport.setViewport(
                Math.round(mLeftEyeViewport.x * mXPxPerTanAngle * mResolutionScale),
                Math.round(mLeftEyeViewport.y * mYPxPerTanAngle * mResolutionScale),
                Math.round(mLeftEyeViewport.width * mXPxPerTanAngle * mResolutionScale),
                Math.round(mLeftEyeViewport.height * mYPxPerTanAngle * mResolutionScale));

        rightViewport.setViewport(
                Math.round(mRightEyeViewport.x * mXPxPerTanAngle * mResolutionScale),
                Math.round(mRightEyeViewport.y * mYPxPerTanAngle * mResolutionScale),
                Math.round(mRightEyeViewport.width * mXPxPerTanAngle * mResolutionScale),
                Math.round(mRightEyeViewport.height * mYPxPerTanAngle * mResolutionScale));

        mViewportsChanged = false;
    }

    private void updateDistortionMesh(boolean flip180) {
        ScreenParams screen = mHmd.getScreenParams();
        CardboardDeviceParams cdp = mHmd.getCardboardDeviceParams();

        float textureWidthTanAngle = mLeftEyeViewport.width + mRightEyeViewport.width;
        float textureHeightTanAngle = Math.max(mLeftEyeViewport.height, mRightEyeViewport.height);
        float xEyeOffsetTanAngleScreen = (screen.getWidthMeters() / 2.0f - cdp.getInterLensDistance() / 2.0f) / mMetersPerTanAngle;
        float yEyeOffsetTanAngleScreen = getYEyeOffsetMeters(cdp, screen) / mMetersPerTanAngle;
        mLeftEyeDistortionMesh = createDistortionMesh(
                mLeftEyeViewport,
                textureWidthTanAngle, textureHeightTanAngle, xEyeOffsetTanAngleScreen, yEyeOffsetTanAngleScreen,
                flip180);
        xEyeOffsetTanAngleScreen = screen.getWidthMeters() / mMetersPerTanAngle - xEyeOffsetTanAngleScreen;
        mRightEyeDistortionMesh = createDistortionMesh(mRightEyeViewport, textureWidthTanAngle, textureHeightTanAngle, xEyeOffsetTanAngleScreen, yEyeOffsetTanAngleScreen, flip180);
    }

    private DistortionMesh createDistortionMesh(EyeViewport eyeViewport, float textureWidthTanAngle, float textureHeightTanAngle, float xEyeOffsetTanAngleScreen, float yEyeOffsetTanAngleScreen, boolean flip180) {
        return new DistortionMesh(
                mHmd.getCardboardDeviceParams().getDistortion(),
                mHmd.getCardboardDeviceParams().getDistortion(),
                mHmd.getCardboardDeviceParams().getDistortion(),
                mHmd.getScreenParams().getWidthMeters() / mMetersPerTanAngle,
                mHmd.getScreenParams().getHeightMeters() / mMetersPerTanAngle,
                xEyeOffsetTanAngleScreen, yEyeOffsetTanAngleScreen, textureWidthTanAngle, textureHeightTanAngle,
                eyeViewport.eyeX, eyeViewport.eyeY, eyeViewport.x, eyeViewport.y, eyeViewport.width, eyeViewport.height,
                flip180, mVignetteEnabled, mChromaticAberrationCorrectionEnabled);
    }

    private EyeViewport initViewportForEye(FieldOfView fov, float xOffset) {
        float left = (float)Math.tan(Math.toRadians(fov.getLeft()));
        float right = (float)Math.tan(Math.toRadians(fov.getRight()));
        float bottom = (float)Math.tan(Math.toRadians(fov.getBottom()));
        float top = (float)Math.tan(Math.toRadians(fov.getTop()));
        EyeViewport vp = new EyeViewport();
        vp.x = xOffset;
        vp.y = 0.0f;
        vp.width = left + right;
        vp.height = bottom + top;
        vp.eyeX = left + xOffset;
        vp.eyeY = bottom;
        return vp;
    }

    float getYEyeOffsetMeters(CardboardDeviceParams cdp, ScreenParams screen) {
        switch (cdp.getVerticalAlignment()) {
            default: {
                return screen.getHeightMeters() / 2.0f;
            }
            case BOTTOM: {
                return cdp.getVerticalDistanceToLensCenter() - screen.getBorderSizeMeters();
            }
            case TOP:
        }
        return screen.getHeightMeters() - (cdp.getVerticalDistanceToLensCenter() - screen.getBorderSizeMeters());
    }

    public DistortionMesh getLeftEyeDistortionMesh() {
        return mLeftEyeDistortionMesh;
    }

    public DistortionMesh getRightEyeDistortionMesh() {
        return mRightEyeDistortionMesh;
    }

    private class EyeViewport {
        public float x;
        public float y;
        public float width;
        public float height;
        public float eyeX;
        public float eyeY;
    }
}
