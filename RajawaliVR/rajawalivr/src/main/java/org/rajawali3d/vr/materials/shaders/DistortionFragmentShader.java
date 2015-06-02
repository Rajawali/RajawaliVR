package org.rajawali3d.vr.materials.shaders;

import org.rajawali3d.materials.shaders.FragmentShader;
import org.rajawali3d.util.RawShaderLoader;

import org.rajawali3d.vr.R;

public class DistortionFragmentShader extends FragmentShader {
    public DistortionFragmentShader() {
        super();
        mNeedsBuild = false;
        initialize();
    }

    @Override
    public void initialize() {
        mShaderString = RawShaderLoader.fetch(R.raw.distortion_fragment_shader);
    }

    @Override
    public void main() {

    }

    @Override
    public void setLocations(final int programHandle) {

    }

    @Override
    public void applyParams() {

    }
}
