import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator

class FontManager {

    companion object {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("Fonts/Raleway-SemiBold.ttf"))

        lateinit var TextFont: BitmapFont
        lateinit var ChapterFont: BitmapFont
        lateinit var MediumFont: BitmapFont
        lateinit var SmallToMediumFont: BitmapFont
        lateinit var SmallFont: BitmapFont

        fun initFonts() {
            TextFont = initFont(12, borderWidth = 1f, borderColor = Color.BLACK)
            ChapterFont = initFont(100, borderWidth = 4f, borderColor = Color.BLACK)
            MediumFont = initFont(70)
            SmallToMediumFont = initFont(40)
            SmallFont = initFont(20)
            generator.dispose() // Dispose of the generator to avoid memory leaks
        }

        fun initFont(size: Int, borderWidth: Float = 0f, borderColor: Color = Color.BLACK): BitmapFont {
            val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()

            parameter.size = size
            parameter.minFilter = Texture.TextureFilter.Linear
            parameter.magFilter = Texture.TextureFilter.Linear
            parameter.genMipMaps = true // Enable mipMap generation for better scaling

            // Set border properties
            parameter.borderWidth = borderWidth
            parameter.borderColor = borderColor

            return generator.generateFont(parameter)
        }
    }
}