package com.mygdx.game.Ability.Abilities.Fire

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Ability.AbilityName
import com.mygdx.game.Ability.ELEMENTAL_TYPE
import com.mygdx.game.Ability.KeyAbility
import com.mygdx.game.Animation.EffectAnimation
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.GameObjects.GameObject.rotateByAmount
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Particles.AnimeliaEffect
import com.mygdx.game.getRotatedUnitVectorClockwise
import com.mygdx.game.minus
import com.mygdx.game.plus
import com.mygdx.game.times

class FlameBreath(override val attachedFightableObject: FightableObject) : KeyAbility {

    private lateinit var breatheEffect: AnimeliaEffect

    val particleEffect = ParticleEffect()

    var initEffect: AnimeliaEffect
    val particleEffect2 = ParticleEffect()
    var increment = 0f

    var pos = Vector2()
    var effectPos = Vector2()
    val size = Vector2(48f,24f)

    init {

        particleEffect2.load(Gdx.files.internal("Particles/firestart.p"), Gdx.files.internal("Particles"))
        initEffect = AnimeliaEffect(particleEffect2)
    }

    override fun onActivate() {


        pos = attachedFightableObject.currentMiddle + (attachedFightableObject.currentUnitVector * 40f) - Vector2(
            size.x / 2,
            size.y / 2)

        effectPos = attachedFightableObject.currentMiddle + (attachedFightableObject.currentUnitVector * 20f)

        initEffect.start()
        initEffect.particleEffect.emitters.forEach { it.reset()
        }
        initEffect.particleEffect.setPosition(effectPos.x, effectPos.y)
        val animation = EffectAnimation(initEffect, 25)
        AnimationManager.animationManager.add(animation)
    }

    override fun onDeactivate() {

    }

    override val activeFrames = 90
    override var currentFrame = 0

    override fun frameAction() {
        if(currentFrame == 25){
            //rotateByAmount(-30f,attachedFightableObject)
            particleEffect.load(Gdx.files.internal("Particles/flamebreath.p"), Gdx.files.internal("Particles"))
            breatheEffect = AnimeliaEffect(particleEffect)

            effectPos = attachedFightableObject.currentMiddle + (attachedFightableObject.currentUnitVector * 15f)
            breatheEffect.particleEffect.allowCompletion()
            breatheEffect.particleEffect.emitters.forEach { it.reset()
                val angleModifier = getAngleModifier(attachedFightableObject)
                it.angle.highMin += angleModifier
                it.angle.highMax += angleModifier
            }
            breatheEffect.start()
            breatheEffect.particleEffect.setPosition(effectPos.x, effectPos.y)
            val animation = EffectAnimation(breatheEffect, 65)
            AnimationManager.animationManager.add(animation)
        }
        if(currentFrame > 25){
            breatheEffect.particleEffect.emitters.forEach {
                val low = it.angle.highMin
                val high = it.angle.highMax
                it.angle.highMin = low + 2f
                it.angle.highMax = high + 2f
            }
            //rotateByAmount(1f, attachedFightableObject)
        }
    }

    fun getAngleModifier(attachedFightableObject: FightableObject): Float{
        return when(attachedFightableObject.currentUnitVector){
            Vector2(1f,0f) -> 270f
            Vector2(-1f, 0f) -> 90f
            Vector2(0f,1f) -> 0f
            else -> 180f
        }
    }

    override val abilityName = AbilityName.FlameBreath
    override val ELEMENTALTYPES = ELEMENTAL_TYPE.FIRE
}