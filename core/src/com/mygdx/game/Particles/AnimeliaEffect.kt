package com.mygdx.game.Particles

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Rendering.Renderable

open class AnimeliaEffect(val particleEffect: ParticleEffect): Renderable {
    override val layer = Layer.FOREGROUND

    fun start(){
        particleEffect.start()
    }

    override fun render(batch: SpriteBatch) {
        particleEffect.draw(batch, Gdx.graphics.deltaTime)
    }

    fun reset(){
        particleEffect.reset()
    }
}