package org.rajawali3d.vr.materials.shaders;

import org.rajawali3d.util.RawShaderLoader;
import org.rajawali3d.vr.R;

public class DistortionAberrationFragmentShader extends DistortionFragmentShader {
    public DistortionAberrationFragmentShader() {
        super();
    }

    @Override
    public void initialize() {
        mShaderString = RawShaderLoader.fetch(R.raw.distortion_aberration_fragment_shader);
    }
}
