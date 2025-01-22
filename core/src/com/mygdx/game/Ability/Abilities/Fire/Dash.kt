package com.mygdx.game.Ability.Abilities.Fire

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.mygdx.game.Ability.AbilityName
import com.mygdx.game.Ability.ELEMENTAL_TYPE
import com.mygdx.game.Ability.KeyAbility
import com.mygdx.game.Animation.EffectAnimation
import com.mygdx.game.DefaultSoundHandler
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Managers.EventManager
import com.mygdx.game.Particles.AnimeliaEffect
import com.mygdx.game.getUnitVectorTowardsPoint
import com.mygdx.game.player

class Dash(override val attachedFightableObject: FightableObject) : KeyAbility() {

    var animeliaEffect: AnimeliaEffect
    val particleEffect = ParticleEffect()
    var increment = 0f

    init {
        particleEffect.load(Gdx.files.internal("Particles/Dash.p"), Gdx.files.internal("Particles"))
        animeliaEffect = AnimeliaEffect(particleEffect)
    }

    override fun onActivate() {
        attachedFightableObject.speed = (attachedFightableObject.baseSpeed * 3.0f)

        increment = (attachedFightableObject.speed - attachedFightableObject.baseSpeed) / activeFrames

        animeliaEffect.start()
        animeliaEffect.particleEffect.emitters.forEach { it.reset()
        }
        animeliaEffect.particleEffect.setPosition(attachedFightableObject.currentMiddle.x, attachedFightableObject.currentMiddle.y)
        val animation = EffectAnimation(animeliaEffect, 20)
        AnimationManager.animationManager.add(animation)
    }

    override fun onDeactivate() {
        attachedFightableObject.speed = attachedFightableObject.baseSpeed
    }

    override val activeFrames = 20
    override var currentFrame = 0

    override fun frameAction() {
        attachedFightableObject.forceMove(attachedFightableObject.speed)
        attachedFightableObject.speed = (attachedFightableObject.getCurrentSpeed() - increment)

        animeliaEffect.particleEffect.setPosition(attachedFightableObject.currentMiddle.x, attachedFightableObject.currentMiddle.y)
    }

    override val abilityName = AbilityName.Dash
    override val ELEMENTALTYPES = ELEMENTAL_TYPE.FIRE
}