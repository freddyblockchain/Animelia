package com.mygdx.game.UI.Scene2d

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

fun createLargerTexture(originalTexture: Texture, scaleFactor: Float): Texture {
    // Load the original texture as a Pixmap
    val originalPixmap = originalTexture.textureData.consumePixmap()

    // Create a new Pixmap with the scaled size
    val width = (originalPixmap.width * scaleFactor).toInt()
    val height = (originalPixmap.height * scaleFactor).toInt()
    val largerPixmap = Pixmap(width, height, originalPixmap.format)

    // Draw the original Pixmap onto the larger Pixmap
    largerPixmap.drawPixmap(originalPixmap, 0, 0, originalPixmap.width, originalPixmap.height, 0, 0, width, height)

    // Create a new Texture from the larger Pixmap
    val largerTexture = Texture(largerPixmap)

    // Dispose of the Pixmap resources
    originalPixmap.dispose()
    largerPixmap.dispose()

    return largerTexture
}