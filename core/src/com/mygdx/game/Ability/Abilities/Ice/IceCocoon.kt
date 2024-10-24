package com.mygdx.game.Ability.Abilities.Ice

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Ability.AbilityName
import com.mygdx.game.Ability.ELEMENTAL_TYPE
import com.mygdx.game.Ability.KeyAbility
import com.mygdx.game.Animation.EffectAnimation
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.GameObjects.GameObject.State
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.Icicle
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Particles.AnimeliaEffect

class IceCocoon(override val attachedFightableObject: FightableObject): KeyAbility {
    override val abilityName = AbilityName.IceCocoon
    override val ELEMENTALTYPES = ELEMENTAL_TYPE.ICE
    val originalTexture = attachedFightableObject.sprite.texture

    override val activeFrames = 60
    override var currentFrame = 0
    override fun frameAction() {
    }

    override fun onActivate() {
        player.state = State.SHIELDED
        attachedFightableObject.sprite.texture = DefaultTextureHandler.getTexture("IceCocoon.png")
    }

    override fun onDeactivate() {
        attachedFightableObject.sprite.texture = originalTexture
        player.state = State.NORMAL
    }
}