package com.mygdx.game.Rendering

import com.badlogic.gdx.graphics.Texture

interface TextureHandler {
    fun getTexture(textureName: String): Texture
}