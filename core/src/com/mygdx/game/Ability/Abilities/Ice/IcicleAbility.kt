package com.mygdx.game.Ability.Abilities.Ice

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Ability.AbilityName
import com.mygdx.game.Ability.ELEMENTAL_TYPE
import com.mygdx.game.Ability.KeyAbility
import com.mygdx.game.Animation.EffectAnimation
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.Icicle
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Particles.AnimeliaEffect

class IcicleAbility(override val attachedFightableObject: FightableObject): KeyAbility {
    override val abilityName = AbilityName.Icicle
    override val ELEMENTALTYPES = ELEMENTAL_TYPE.ICE

    override val activeFrames = 30
    override var currentFrame = 0
    val size = Vector2(24f,16f)
    val particleEffect = DefaultParticleHandler.getParticle("icestart.p")
    val iceEffect = AnimeliaEffect(particleEffect)

    var pos = Vector2()
    var effectPos = Vector2()
    override fun onActivate() {
        pos = attachedFightableObject.currentMiddle + (attachedFightableObject.currentUnitVector * 40f) - Vector2(
            size.x / 2,
            size.y / 2)

        effectPos = attachedFightableObject.currentMiddle + (attachedFightableObject.currentUnitVector * 20f)

        iceEffect.start()
        iceEffect.particleEffect.emitters.forEach { it.reset()
        }
        iceEffect.particleEffect.setPosition(effectPos.x, effectPos.y)
        val animation = EffectAnimation(iceEffect, 25)
        AnimationManager.animationManager.add(animation)
    }

    override fun onDeactivate() {

    }

    override fun frameAction() {
        if(currentFrame == 20){
            val icicle = Icicle(GameObjectData(x = pos.x.toInt(), y = pos.y.toInt()), size, attachedFightableObject.currentUnitVector, attachedFightableObject)
            icicle.add()
        }
    }
}