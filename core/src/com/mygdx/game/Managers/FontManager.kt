import com.badlogic.gdx.Gdx
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

        fun initFonts() {

            TextFont = initFont(12)
            ChapterFont = initFont(100)
            MediumFont = initFont(70)
            SmallToMediumFont = initFont(40)
            generator.dispose() // Dispose of the generator to avoid memory leaks
        }

        fun initFont(size: Int): BitmapFont{
            val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()

            parameter.size = size
            parameter.minFilter = Texture.TextureFilter.Linear
            parameter.magFilter = Texture.TextureFilter.Linear
            // Optionally enable mipMap generation and linear filtering
            parameter.genMipMaps = true
            return generator.generateFont(parameter)
        }
    }
}