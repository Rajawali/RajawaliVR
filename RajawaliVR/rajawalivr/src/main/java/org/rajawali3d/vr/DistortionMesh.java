package org.rajawali3d.vr;

import com.google.vrtoolkit.cardboard.Distortion;

import org.rajawali3d.Object3D;
import org.rajawali3d.materials.Material;
import org.rajawali3d.vr.materials.shaders.DistortionAberrationFragmentShader;
import org.rajawali3d.vr.materials.shaders.DistortionAberrationVertexShader;
import org.rajawali3d.vr.materials.shaders.DistortionFragmentShader;
import org.rajawali3d.vr.materials.shaders.DistortionVertexShader;

public class DistortionMesh extends Object3D {
    public static final int BYTES_PER_SHORT = 2;
    public static final int COMPONENTS_PER_VERT = 9;
    public static final int ROWS = 40;
    public static final int COLS = 40;
    public static final float VIGNETTE_SIZE_TAN_ANGLE = 0.05f;

    private DistortionVertexShader mDistortionVertexShader;
    private DistortionFragmentShader mDistortionFragmentShader;
    private DistortionAberrationVertexShader mDistortionAberrationVertexShader;
    private DistortionAberrationFragmentShader mDistortionAberrationFragmentShader;

    public DistortionMesh(Distortion distortionRed, Distortion distortionGreen, Distortion distortionBlue,
                          float screenWidth, float screenHeight, float xEyeOffsetScreen, float yEyeOffsetScreen,
                          float textureWidth, float textureHeight, float xEyeOffsetTexture, float yEyeOffsetTexture,
                          float viewportXTexture, float viewportYTexture, float viewportWidthTexture, float viewportHeightTexture,
                          boolean flip180, boolean vignetteEnabled, boolean aberrationCorrected) {
        super();
        float[] vertexData = new float[14400];
        int vertexOffset = 0;
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                float uTextureBlue = (float)col / 39.0f * (viewportWidthTexture / textureWidth) + viewportXTexture / textureWidth;
                float xTexture = uTextureBlue * textureWidth - xEyeOffsetTexture;
                float vTextureBlue = (float)row / 39.0f * (viewportHeightTexture / textureHeight) + viewportYTexture / textureHeight;
                float yTexture = vTextureBlue * textureHeight - yEyeOffsetTexture;
                float rTexture = (float)Math.sqrt(xTexture * xTexture + yTexture * yTexture);
                float textureToScreenBlue = rTexture > 0.0f ? distortionBlue.distortInverse(rTexture) / rTexture : 1.0f;
                float xScreen = xTexture * textureToScreenBlue;
                float yScreen = yTexture * textureToScreenBlue;
                float uScreen = (xScreen + xEyeOffsetScreen) / screenWidth;
                float vScreen = (yScreen + yEyeOffsetScreen) / screenHeight;
                float rScreen = rTexture * textureToScreenBlue;
                float screenToTextureGreen = rScreen > 0.0f ? distortionGreen.distortionFactor(rScreen) : 1.0f;
                float uTextureGreen = (xScreen * screenToTextureGreen + xEyeOffsetTexture) / textureWidth;
                float vTextureGreen = (yScreen * screenToTextureGreen + yEyeOffsetTexture) / textureHeight;
                float screenToTextureRed = rScreen > 0.0f ? distortionRed.distortionFactor(rScreen) : 1.0f;
                float uTextureRed = (xScreen * screenToTextureRed + xEyeOffsetTexture) / textureWidth;
                float vTextureRed = (yScreen * screenToTextureRed + yEyeOffsetTexture) / textureHeight;
                float vignetteSizeTexture = VIGNETTE_SIZE_TAN_ANGLE / textureToScreenBlue;
                float dxTexture = xTexture + xEyeOffsetTexture - clamp(xTexture + xEyeOffsetTexture, viewportXTexture + vignetteSizeTexture, viewportXTexture + viewportWidthTexture - vignetteSizeTexture);
                float dyTexture = yTexture + yEyeOffsetTexture - clamp(yTexture + yEyeOffsetTexture, viewportYTexture + vignetteSizeTexture, viewportYTexture + viewportHeightTexture - vignetteSizeTexture);
                float drTexture = (float)Math.sqrt(dxTexture * dxTexture + dyTexture * dyTexture);
                float vignette = vignetteEnabled ? 1.0f - clamp(drTexture / vignetteSizeTexture, 0.0f, 1.0f) : 1.0f;
                if (flip180) {
                    uTextureBlue = 1.0f - uTextureBlue;
                    uTextureRed = 1.0f - uTextureRed;
                    uTextureGreen = 1.0f - uTextureGreen;
                    vTextureBlue = 1.0f - vTextureBlue;
                    vTextureRed = 1.0f - vTextureRed;
                    vTextureGreen = 1.0f - vTextureGreen;
                }
                vertexData[vertexOffset] = 2.0f * uScreen - 1.0f;
                vertexData[vertexOffset + 1] = 2.0f * vScreen - 1.0f;
                vertexData[vertexOffset + 2] = vignette;
                vertexData[vertexOffset + 3] = uTextureRed;
                vertexData[vertexOffset + 4] = vTextureRed;
                vertexData[vertexOffset + 5] = uTextureGreen;
                vertexData[vertexOffset + 6] = vTextureGreen;
                vertexData[vertexOffset + 7] = uTextureBlue;
                vertexData[vertexOffset + 8] = vTextureBlue;
                vertexOffset = (short)(vertexOffset + COMPONENTS_PER_VERT);
            }
        }
        int nIndices = 3158;
        int[] indexData = new int[nIndices];
        int indexOffset = 0;
        vertexOffset = 0;
        for (int row2 = 0; row2 < ROWS - 1; ++row2) {
            if (row2 > 0) {
                indexData[indexOffset] = indexData[indexOffset - 1];
                indexOffset = (indexOffset + 1);
            }
            for (int col = 0; col < COLS; ++col) {
                if (col > 0) {
                    vertexOffset = row2 % 2 == 0 ? (int)((short)(vertexOffset + 1)) : (int)((short)(vertexOffset - 1));
                }
                int n = indexOffset;
                indexOffset = (indexOffset + 1);
                indexData[n] = vertexOffset;
                int n2 = indexOffset;
                indexOffset = (indexOffset + 1);
                indexData[n2] = (vertexOffset + 40);
            }
            vertexOffset = (vertexOffset + 40);
        }

        setData(vertexData, null, null, null, indexData, true);

        if(aberrationCorrected) {
            mDistortionAberrationVertexShader = new DistortionAberrationVertexShader();
            mDistortionAberrationFragmentShader = new DistortionAberrationFragmentShader();
            mMaterial = new Material(mDistortionAberrationVertexShader, mDistortionAberrationFragmentShader);
        } else {
            mDistortionVertexShader = new DistortionVertexShader();
            mDistortionFragmentShader = new DistortionFragmentShader();
            mMaterial = new Material(mDistortionVertexShader, mDistortionFragmentShader);
        }
    }

    private float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public void setTextureCoordScale(final float scale) {
        if(mDistortionVertexShader != null) {
            mDistortionVertexShader.setTextureCoordScale(scale);
        }
        if(mDistortionAberrationVertexShader != null) {
            mDistortionAberrationVertexShader.setTextureCoordScale(scale);
        }
    }
}
