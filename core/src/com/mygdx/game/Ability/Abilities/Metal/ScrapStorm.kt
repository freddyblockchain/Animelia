package com.mygdx.game.Ability.Abilities.Metal

import RectangleAreaProjectile
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Ability.AbilityName
import com.mygdx.game.Ability.ELEMENTAL_TYPE
import com.mygdx.game.Ability.KeyAbility
import com.mygdx.game.Animation.EffectAnimation
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.GameObjects.GameObject.ReflectingState
import com.mygdx.game.GameObjects.GameObject.rotateByAmount
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Particles.AnimeliaEffect

class ScrapStorm(override val attachedFightableObject: FightableObject): KeyAbility() {
    override val abilityName = AbilityName.ScrapStorm
    override val ELEMENTALTYPES = ELEMENTAL_TYPE.METAL

    override val activeFrames = 60
    override var currentFrame = 0

    var effect: ParticleEffect = ParticleEffect()
    lateinit var animeliaEffect: AnimeliaEffect

    override fun onActivate() {
        val rectangleAreaProjectile = RectangleAreaProjectile(gameObjectData = GameObjectData(x = attachedFightableObject.sprite.x.toInt() - 4, y
        = attachedFightableObject.sprite.y.toInt() -4), size = Vector2(32f,32f),attachedFightableObject, 60, true
        )
        rectangleAreaProjectile.add()

        effect.load(Gdx.files.internal("Particles/scrapstorm.p"), Gdx.files.internal("Particles"))
        animeliaEffect = AnimeliaEffect(effect)
        animeliaEffect.start()
        animeliaEffect.reset()
        animeliaEffect.particleEffect.emitters.forEach { it.reset() }
        animeliaEffect.particleEffect.setPosition(attachedFightableObject.currentMiddle.x, attachedFightableObject.currentMiddle.y)
        animeliaEffect.layer = Layer.ONGROUND

        val animeliaAnimation = EffectAnimation(animeliaEffect, activeFrames)
        AnimationManager.animationManager.add(animeliaAnimation)

        attachedFightableObject.reflectState = ReflectingState.REFLECTING
    }

    override fun onDeactivate() {
        attachedFightableObject.reflectState = ReflectingState.NOTREFLECTING
    }

    override fun frameAction() {
        rotateByAmount(6.2f, attachedFightableObject)
    }
}