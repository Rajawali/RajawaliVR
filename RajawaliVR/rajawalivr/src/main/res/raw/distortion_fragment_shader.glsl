#ifdef GL_ES
#ifdef GL_FRAGMENT_PRECISION_HIGH
precision highp float;
#else
precision mediump float;
#endif
#endif

uniform sampler2D uDistortionTextureFBTex;

varying vec2 vTextureCoord;
varying float vVignette;

void main() {
  gl_FragColor = vVignette * texture2D(uDistortionTextureFBTex, vTextureCoord);
}
