package com.mygdx.game

import TextureHandler
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture

val assets = AssetManager()
object DefaultTextureHandler: TextureHandler{
        override fun getTexture(textureName: String): Texture {
            if(!assets.isLoaded(textureName)) {
                assets.load(textureName, Texture::class.java)
                assets.finishLoading()
            }
            return assets.get(textureName,Texture::class.java)
        }
}