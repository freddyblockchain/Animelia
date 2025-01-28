package com.mygdx.game.Ability.Abilities.Flying

import RectangleAreaProjectile
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Ability.AbilityName
import com.mygdx.game.Ability.ELEMENTAL_TYPE
import com.mygdx.game.Ability.KeyAbility
import com.mygdx.game.Animation.EffectAnimation
import com.mygdx.game.CannotMoveStrategy.CannotMoveStrategy
import com.mygdx.game.CannotMoveStrategy.MoveRegardless
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.GameObjects.GameObject.FlyingState
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.Projectile
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Particles.AnimeliaEffect
import com.mygdx.game.setSize

class AerialDeath(override val attachedFightableObject: FightableObject): KeyAbility() {
    override val abilityName = AbilityName.AerialDeath
    override val ELEMENTALTYPES = ELEMENTAL_TYPE.FLYING

    override val activeFrames = 90
    override var currentFrame = 0

    var effect: ParticleEffect = ParticleEffect()
    lateinit var animeliaEffect: AnimeliaEffect
    lateinit var origSize: Vector2

    override fun onActivate() {
        attachedFightableObject.flyingState = FlyingState.FLYING
        effect.load(Gdx.files.internal("Particles/BirdCrashdown.p"), Gdx.files.internal("Particles"))
        animeliaEffect = AnimeliaEffect(effect)

    }

    override fun onDeactivate() {
      attachedFightableObject.flyingState = FlyingState.NOTFLYING
    }

    override fun frameAction() {
        val width = attachedFightableObject.sprite.width
        val height = attachedFightableObject.sprite.height
        if(currentFrame < 60){
            attachedFightableObject.sprite.setSize(width + 1, height + 1)
        }
        else if(currentFrame > 60){
            attachedFightableObject.sprite.setSize(width  - 2, height - 2)
        }

        if(currentFrame == activeFrames - 5){

            animeliaEffect.reset()
            animeliaEffect.particleEffect.emitters.forEach { it.reset() }
            animeliaEffect.start()
            animeliaEffect.particleEffect.setPosition(attachedFightableObject.currentMiddle.x, attachedFightableObject.currentMiddle.y)
            val animeliaAnimation = EffectAnimation(animeliaEffect, 30)
            AnimationManager.animationManager.add(animeliaAnimation)
        }
        if(currentFrame == activeFrames - 1){
            val rectangleAreaProjectile = RectangleAreaProjectile(gameObjectData = GameObjectData(x = attachedFightableObject.sprite.x.toInt() - 4, y
            = attachedFightableObject.sprite.y.toInt() -4), size = Vector2(40f,40f),attachedFightableObject, 5, false
            )
            rectangleAreaProjectile.add()
        }
        attachedFightableObject.forceMove(0.8f)
    }
}