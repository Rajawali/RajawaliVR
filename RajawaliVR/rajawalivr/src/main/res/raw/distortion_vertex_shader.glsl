uniform float uTextureCoordScale;

attribute vec2 aPosition;
attribute float aVignette;
attribute vec2 aBlueTextureCoord;

varying vec2 vTextureCoord;
varying float vVignette;

void main() {
  gl_Position = vec4(aPosition, 0.0, 1.0);
  vTextureCoord = aBlueTextureCoord.xy * uTextureCoordScale;
  vVignette = aVignette;
}
