package com.mygdx.game.Ability.Abilities.Sound

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Ability.AbilityName
import com.mygdx.game.Ability.ELEMENTAL_TYPE
import com.mygdx.game.Ability.KeyAbility
import com.mygdx.game.Animation.EffectAnimation
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.SoundProjectile
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Particles.AnimeliaEffect

class LionRoar(override val attachedFightableObject: FightableObject): KeyAbility() {
    override val abilityName = AbilityName.LionRoar
    override val ELEMENTALTYPES = ELEMENTAL_TYPE.SOUND

    override val activeFrames = 90
    override var currentFrame = 0
    val size = Vector2(16f,32f)
    val particleEffect = DefaultParticleHandler.getParticle("firestart.p")
    val fireballEffect = AnimeliaEffect(particleEffect)

    var pos = Vector2()
    var effectPos = Vector2()
    override fun onActivate() {
        pos = attachedFightableObject.currentMiddle + (attachedFightableObject.currentUnitVector * 40f) - Vector2(
            size.x / 2,
            size.y / 2)

        effectPos = attachedFightableObject.currentMiddle + (attachedFightableObject.currentUnitVector * 20f)

        fireballEffect.start()
        fireballEffect.particleEffect.emitters.forEach { it.reset()
        }
        fireballEffect.particleEffect.setPosition(effectPos.x, effectPos.y)
        val animation = EffectAnimation(fireballEffect, 35)
        AnimationManager.animationManager.add(animation)
    }

    override fun onDeactivate() {

    }

    override fun frameAction() {
        if(currentFrame == 30 || currentFrame == 50 || currentFrame == 70){
            shootThreeProjectiles()
        }
    }

    fun shootThreeProjectiles(){
        val slantedVector1 = getRotatedUnitVectorClockwise(attachedFightableObject.currentUnitVector, 45f)
        val slantedVector2 = getRotatedUnitVectorClockwise(attachedFightableObject.currentUnitVector, 315f)

        val soundGun1 = SoundProjectile(GameObjectData(x = pos.x.toInt(), y = pos.y.toInt()), size, attachedFightableObject.currentUnitVector, attachedFightableObject)
        val soundGun2 = SoundProjectile(GameObjectData(x = pos.x.toInt(), y = pos.y.toInt()), size, slantedVector1, attachedFightableObject)
        val soundGun3 = SoundProjectile(GameObjectData(x = pos.x.toInt(), y = pos.y.toInt()), size, slantedVector2, attachedFightableObject)

        soundGun1.add()
        soundGun2.add()
        soundGun3.add()
    }
}