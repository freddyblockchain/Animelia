package com.mygdx.game.Ability.Abilities.Fighting

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Ability.AbilityName
import com.mygdx.game.Ability.KeyAbility
import com.mygdx.game.Animation.EffectAnimation
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.Fireball
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.RockProjectile
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Particles.AnimeliaEffect

class RockThrowAbility(override val attachedFightableObject: FightableObject): KeyAbility {
    override val triggerKey = Input.Keys.NUM_3
    override val abilityName = AbilityName.RockThrow

    override val activeFrames = 30
    override var currentFrame = 0
    val size = Vector2(32f,32f)
    var pos = Vector2()
    /*val particleEffect = DefaultParticleHandler.getParticle("firestart.p")
    val fireballEffect = AnimeliaEffect(particleEffect)

    var pos = Vector2()
    var effectPos = Vector2()*/
    override fun onActivate() {
        pos = attachedFightableObject.currentMiddle + (attachedFightableObject.currentUnitVector * 40f) - Vector2(
            size.x / 2,
            size.y / 2)

        /*effectPos = attachedFightableObject.currentMiddle + (attachedFightableObject.currentUnitVector * 20f)

        fireballEffect.start()
        fireballEffect.particleEffect.emitters.forEach { it.reset()
        }
        fireballEffect.particleEffect.setPosition(effectPos.x, effectPos.y)
        val animation = EffectAnimation(fireballEffect, 25)
        AnimationManager.animationManager.add(animation)*/
    }

    override fun onDeactivate() {

    }

    override fun frameAction() {
        if(currentFrame == 20){
            val rockProjectile = RockProjectile(GameObjectData(x = pos.x.toInt(), y = pos.y.toInt()), size, attachedFightableObject.currentUnitVector, attachedFightableObject)
            rockProjectile.add()
        }
    }
}