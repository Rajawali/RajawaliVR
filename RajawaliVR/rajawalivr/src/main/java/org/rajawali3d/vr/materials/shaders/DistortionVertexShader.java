package org.rajawali3d.vr.materials.shaders;

import android.opengl.GLES20;

import org.rajawali3d.materials.shaders.VertexShader;
import org.rajawali3d.util.RawShaderLoader;
import org.rajawali3d.vr.R;

public class DistortionVertexShader extends VertexShader {
    public static final int BYTES_PER_FLOAT = 4;

    public static final int DATA_STRIDE_BYTES = 36;
    public static final int DATA_POS_OFFSET = 0;
    public static final int DATA_POS_COMPONENTS = 2;
    public static final int DATA_VIGNETTE_OFFSET = 2;
    public static final int DATA_VIGNETTE_COMPONENTS = 1;
    public static final int DATA_BUV_OFFSET = 7;
    public static final int DATA_UV_COMPONENTS = 2;

    private int muTextureCoordScaleHandle;

    private int maPositionHandle;
    private int maVignetteHandle;
    private int maBlueTextureCoordHandle;

    private float mTextureCoordScale;

    public DistortionVertexShader() {
        super();
        mNeedsBuild = false;
        initialize();
    }

    @Override
    public void initialize() {
        mTextureCoordScale = 1;
        mShaderString = RawShaderLoader.fetch(R.raw.distortion_vertex_shader);
    }

    @Override
    public void main() {

    }

    @Override
    public void setVertices(final int vertexBufferHandle) {
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBufferHandle);
        GLES20.glVertexAttribPointer(maPositionHandle, DATA_POS_COMPONENTS, GLES20.GL_FLOAT,
                false, DATA_STRIDE_BYTES, DATA_POS_OFFSET * BYTES_PER_FLOAT);
        GLES20.glEnableVertexAttribArray(maPositionHandle);
        GLES20.glVertexAttribPointer(maVignetteHandle, DATA_VIGNETTE_COMPONENTS, GLES20.GL_FLOAT,
                false, DATA_STRIDE_BYTES, DATA_VIGNETTE_OFFSET * BYTES_PER_FLOAT);
        GLES20.glEnableVertexAttribArray(maVignetteHandle);
        GLES20.glVertexAttribPointer(maBlueTextureCoordHandle, DATA_UV_COMPONENTS, GLES20.GL_FLOAT,
                false, DATA_STRIDE_BYTES, DATA_BUV_OFFSET * BYTES_PER_FLOAT);
        GLES20.glEnableVertexAttribArray(maBlueTextureCoordHandle);
    }

    @Override
    public void setLocations(final int programHandle)
    {
        muTextureCoordScaleHandle = getUniformLocation(programHandle, "uTextureCoordScale");

        maPositionHandle = getAttribLocation(programHandle, "aPosition");
        maVignetteHandle = getUniformLocation(programHandle, "aVignette");
        maBlueTextureCoordHandle = getUniformLocation(programHandle, "aBlueTextureCoord");
    }

    @Override
    public void applyParams()
    {
        GLES20.glUniform1f(muTextureCoordScaleHandle, mTextureCoordScale);
    }

    public void setTextureCoordScale(final float scale) {
        mTextureCoordScale = scale;
    }
}
