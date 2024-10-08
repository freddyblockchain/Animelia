package com.mygdx.game.Inventory

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Ability.Abilities.Fighting.TailSwipe
import com.mygdx.game.Ability.AbilityName
import com.mygdx.game.Ability.KeyAbility
import com.mygdx.game.Animation.TextAnimation
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.Egg
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.player
import kotlinx.serialization.Serializable

@Serializable
class Inventory() {
    var gold: Int = 0
    val entityBooks: MutableList<ANIMELIA_ENTITY> = mutableListOf()
    val eggs: MutableList<Egg> = mutableListOf(Egg.FIRE)
    val ownedAbilities: MutableList<AbilityName> = mutableListOf()
    fun goldReceived(amount: Int,pos: Vector2){
        gold += amount
        val textAnimation = TextAnimation(Color.YELLOW, "+ $amount gold", pos)

        AnimationManager.animationManager.add(textAnimation)
    }


}