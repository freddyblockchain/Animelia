package com.mygdx.game.UI

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

fun createBackgroundDrawable(color: Color): Drawable {
    // Create a pixmap with the specified color
    val pixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
    pixmap.setColor(color)
    pixmap.fill()

    // Create a texture from the pixmap
    val texture = Texture(pixmap)

    // Dispose of the pixmap to free up memory
    pixmap.dispose()

    // Create a drawable from the texture
    return TextureRegionDrawable(texture)
}