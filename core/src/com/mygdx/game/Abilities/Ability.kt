package com.mygdx.game.Abilities

interface Ability {
    fun onActivate()
    fun onDeactivate()
}

fun getAbilityBasedOnEnum(abilityString: String): Ability{
    return when(abilityString){
        "ButlerSwitch" -> ButlerSwitch()
        else -> ButlerSwitch()
    }
}