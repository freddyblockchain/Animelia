import com.badlogic.gdx.graphics.Texture

interface TextureHandler {
    fun getTexture(textureName: String): Texture
}