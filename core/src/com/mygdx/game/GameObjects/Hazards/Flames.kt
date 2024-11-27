package com.mygdx.game.GameObjects.Hazards

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.DefaultParticleHandler
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Particles.AnimeliaEffect

class Flames(gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())) {
    override val texture = DefaultTextureHandler.getTexture("sensor.png")
    override val layer = Layer.ONGROUND

    var effect: ParticleEffect = ParticleEffect()
    lateinit var animeliaEffect: AnimeliaEffect

    override fun initObject() {
        super.initObject()
        effect.load(Gdx.files.internal("Particles/flames.p"), Gdx.files.internal("Particles"))
        animeliaEffect = AnimeliaEffect(effect)
        animeliaEffect.particleEffect.setPosition(this.currentMiddle.x, this.currentMiddle.y)
        animeliaEffect.particleEffect.start()
    }

    override fun render(batch: SpriteBatch) {
        animeliaEffect.render(batch)
    }
}