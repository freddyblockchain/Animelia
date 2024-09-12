#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;

void main() {
    vec4 textureColor = texture2D(u_texture, v_texCoords);

    // Convert the texture's RGB to grayscale
    float gray = (textureColor.r + textureColor.g + textureColor.b) / 3.0;
    vec3 grayscale = vec3(gray, gray, gray);

    // Preserve the original alpha value from the texture
    gl_FragColor = vec4(grayscale, textureColor.a);  // Keep alpha from texture
}