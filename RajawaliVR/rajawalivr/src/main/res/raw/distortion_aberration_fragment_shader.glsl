#ifdef GL_ES
#ifdef GL_FRAGMENT_PRECISION_HIGH
precision highp float;
#else
precision mediump float;
#endif
#endif

uniform sampler2D uDistortionTextureFBTex;

varying vec2 vRedTextureCoord;
varying vec2 vBlueTextureCoord;
varying vec2 vGreenTextureCoord;
varying float vVignette;

void main() {
  gl_FragColor = vVignette * vec4(texture2D(uDistortionTextureFBTex, vRedTextureCoord).r,
          texture2D(uDistortionTextureFBTex, vGreenTextureCoord).g,
          texture2D(uDistortionTextureFBTex, vBlueTextureCoord).b, 1.0);
}
