package com.mygdx.game.Managers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator

class FontManager {

    companion object {


        val generator = FreeTypeFontGenerator(Gdx.files.internal("Fonts/Raleway-SemiBold.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()

        lateinit var NormalFont: BitmapFont

        fun initFonts() {
            parameter.size = 8;
            NormalFont = generator.generateFont(parameter) // font size is in pixels
            generator.dispose() // avoid memory leaks, dispose the generator when done using it
        }
    }
}