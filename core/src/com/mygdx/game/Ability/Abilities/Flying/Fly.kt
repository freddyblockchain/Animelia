package com.mygdx.game.Ability.Abilities.Flying

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Ability.AbilityName
import com.mygdx.game.Ability.ELEMENTAL_TYPE
import com.mygdx.game.Ability.KeyAbility
import com.mygdx.game.Animation.EffectAnimation
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.GameObjects.GameObject.FlyingState
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.Fireball
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Particles.AnimeliaEffect

class Fly(override val attachedFightableObject: FightableObject): KeyAbility {
    override val triggerKey = Input.Keys.NUM_2
    override val abilityName = AbilityName.Fly
    override val ELEMENTALTYPES = ELEMENTAL_TYPE.FLYING

    override val activeFrames = 60
    override var currentFrame = 0

    override fun onActivate() {
        attachedFightableObject.flyingState = FlyingState.FLYING
    }

    override fun onDeactivate() {
        attachedFightableObject.flyingState = FlyingState.NOTFLYING
    }

    override fun frameAction() {
        val width = attachedFightableObject.sprite.width
        val height = attachedFightableObject.sprite.height
        if(currentFrame <= 20){
            attachedFightableObject.sprite.setSize(width + 1, height + 1)
            println("width is : " + width)
        }
        else if(currentFrame >= 41){
            attachedFightableObject.sprite.setSize(width  - 1, height - 1)
        }
        attachedFightableObject.forceMove(2f)
    }
}