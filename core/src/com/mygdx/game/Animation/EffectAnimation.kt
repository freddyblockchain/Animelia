package com.mygdx.game.Animation

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Particles.AnimeliaEffect
import java.time.Duration

class EffectAnimation(private val animeliaEffect: AnimeliaEffect, override val duration: Int): Animation {
    override val animationAction = {}
    override var currentFrame = 0
    override var actionFrame = 0

    override val layer = animeliaEffect.layer

    override fun reset() {
        animeliaEffect.reset()
    }

    override fun render(batch: SpriteBatch) {
        animeliaEffect.render(batch)
    }
}