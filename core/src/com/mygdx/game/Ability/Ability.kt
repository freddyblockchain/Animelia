package com.mygdx.game.Ability

import com.mygdx.game.Ability.Abilities.Fighting.FireballData
import com.mygdx.game.Ability.Abilities.Fighting.TailSwipe
import com.mygdx.game.Ability.Abilities.Fighting.TailSwipeData
import com.mygdx.game.GameObjects.GameObject.FightableObject

enum class AbilityType{Fireball, TailSwipe}

fun convertAbilityToType(ability:String): AbilityType{
    return when(ability){
        "Fireball" -> AbilityType.Fireball
        else -> AbilityType.TailSwipe
    }
}
fun convertAbilityTypeToData(ability: AbilityType): AbilityData{
    return when(ability){
        AbilityType.Fireball -> FireballData()
        else -> TailSwipeData()
    }
}

interface AbilityData{
    val imageIcon: String
}
interface Ability {
    fun onActivate()
    fun onDeactivate()
    val activeFrames: Int
    var currentFrame: Int
    fun frameAction()
    val attachedFightableObject: FightableObject

}
