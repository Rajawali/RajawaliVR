package org.rajawali3d.vr.materials.shaders;

import android.opengl.GLES20;

import org.rajawali3d.util.RawShaderLoader;
import org.rajawali3d.vr.R;

public class DistortionAberrationVertexShader extends DistortionVertexShader {
    public static final int DATA_RUV_OFFSET = 3;
    public static final int DATA_GUV_OFFSET = 5;

    private int maRedTextureCoordHandle;
    private int maGreenTextureCoordHandle;

    public DistortionAberrationVertexShader() {
        super();
    }

    @Override
    public void initialize() {
        setTextureCoordScale(1);
        mShaderString = RawShaderLoader.fetch(R.raw.distortion_aberration_vertex_shader);
    }

    @Override
    public void setVertices(final int vertexBufferHandle) {
        super.setVertices(vertexBufferHandle);

        GLES20.glVertexAttribPointer(maRedTextureCoordHandle, DATA_UV_COMPONENTS, GLES20.GL_FLOAT,
                false, DATA_STRIDE_BYTES, DATA_RUV_OFFSET * BYTES_PER_FLOAT);
        GLES20.glEnableVertexAttribArray(maRedTextureCoordHandle);
        GLES20.glVertexAttribPointer(maGreenTextureCoordHandle, DATA_UV_COMPONENTS, GLES20.GL_FLOAT,
                false, DATA_STRIDE_BYTES, DATA_GUV_OFFSET * BYTES_PER_FLOAT);
        GLES20.glEnableVertexAttribArray(maGreenTextureCoordHandle);
    }

    @Override
    public void setLocations(final int programHandle) {
        super.setLocations(programHandle);
        maRedTextureCoordHandle = getAttribLocation(programHandle, "aRedTextureCoord");
        maGreenTextureCoordHandle = getAttribLocation(programHandle, "aGreenTextureCoord");
    }
}

