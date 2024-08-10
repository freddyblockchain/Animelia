package com.mygdx.game

import com.mygdx.game.Rendering.TextureHandler
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader.ParticleEffectParameter
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.ParticleEffect

val textureAssets = AssetManager()
val particleAssets = AssetManager()
val musicAssets = AssetManager()
val soundAssets = AssetManager()

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

object DefaultMusicHandler{
    fun getMusic(musicName: String): Music {
        if (!musicAssets.isLoaded(musicName)) {
            musicAssets.load(musicName, Music::class.java)
            musicAssets.finishLoading()
        }
        return musicAssets.get(musicName, Music::class.java)
    }
}

object DefaultSoundHandler{
    fun getSound(soundName: String): Sound {
        if (!soundAssets.isLoaded(soundName)) {
            soundAssets.load(soundName, Sound::class.java)
            soundAssets.finishLoading()
        }
        return soundAssets.get(soundName, Sound::class.java)
    }
}