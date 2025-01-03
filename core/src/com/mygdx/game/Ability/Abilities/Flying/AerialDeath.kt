package com.mygdx.game.Ability.Abilities.Flying

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.mygdx.game.Ability.AbilityName
import com.mygdx.game.Ability.ELEMENTAL_TYPE
import com.mygdx.game.Ability.KeyAbility
import com.mygdx.game.Animation.EffectAnimation
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.GameObjects.GameObject.FlyingState
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Particles.AnimeliaEffect

class AerialDeath(override val attachedFightableObject: FightableObject): KeyAbility() {
    override val abilityName = AbilityName.AerialDeath
    override val ELEMENTALTYPES = ELEMENTAL_TYPE.FLYING

    override val activeFrames = 90
    override var currentFrame = 0

    var effect: ParticleEffect = ParticleEffect()
    lateinit var animeliaEffect: AnimeliaEffect

    override fun onActivate() {
        effect.load(Gdx.files.internal("Particles/BirdCrashdown.p"), Gdx.files.internal("Particles"))
        animeliaEffect = AnimeliaEffect(effect)

        attachedFightableObject.flyingState = FlyingState.FLYING
    }

    override fun onDeactivate() {

        attachedFightableObject.flyingState = FlyingState.NOTFLYING
    }

    override fun frameAction() {
        val width = attachedFightableObject.sprite.width
        val height = attachedFightableObject.sprite.height
        if(currentFrame < 60){
            attachedFightableObject.sprite.setSize(width + 1, height + 1)
            println("width is : " + width)
        }
        else if(currentFrame > 60){
            attachedFightableObject.sprite.setSize(width  - 2, height - 2)
        }
        attachedFightableObject.forceMove(1f)

        if(currentFrame == activeFrames - 5){
            animeliaEffect.reset()
            animeliaEffect.particleEffect.emitters.forEach { it.reset() }
            animeliaEffect.start()
            animeliaEffect.particleEffect.setPosition(attachedFightableObject.currentMiddle.x, attachedFightableObject.currentMiddle.y)
            val animeliaAnimation = EffectAnimation(animeliaEffect, 30)
            AnimationManager.animationManager.add(animeliaAnimation)
        }
    }
}