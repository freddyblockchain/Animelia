package com.mygdx.game

import com.mygdx.game.Rendering.TextureHandler
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader.ParticleEffectParameter
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.ParticleEffect

val textureAssets = AssetManager()
val particleAssets = AssetManager()

object DefaultTextureHandler : TextureHandler {
    override fun getTexture(textureName: String): Texture {
        if (!textureAssets.isLoaded(textureName)) {
            textureAssets.load(textureName, Texture::class.java)
            textureAssets.finishLoading()
        }
        return textureAssets.get(textureName, Texture::class.java)
    }
}

object DefaultParticleHandler {
    fun getParticle(particleEffectName: String): ParticleEffect {
        if (!particleAssets.isLoaded(particleEffectName)) {
            val particleEffectParameter = ParticleEffectParameter()
            particleAssets.load("Particles/$particleEffectName", ParticleEffect::class.java)
            particleAssets.finishLoading()
        }
        return particleAssets.get("Particles/$particleEffectName", ParticleEffect::class.java)
    }
}