import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator

class FontManager {

    companion object {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("Fonts/Raleway-SemiBold.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        val chapterParam = FreeTypeFontGenerator.FreeTypeFontParameter()

        lateinit var SpeechFont: BitmapFont
        lateinit var ChapterFont: BitmapFont

        fun initFonts() {
            // Adjust the size based on the display's DPI or use multiple font sizes
            parameter.size = 12 // Consider different sizes for different DPIs
            parameter.minFilter = Texture.TextureFilter.Linear
            parameter.magFilter = Texture.TextureFilter.Linear
            // Optionally enable mipMap generation and linear filtering
            parameter.genMipMaps = true
            SpeechFont = generator.generateFont(parameter) // Generate the font
            chapterParam.size = 100
            ChapterFont = generator.generateFont(chapterParam)
            generator.dispose() // Dispose of the generator to avoid memory leaks
        }
    }
}