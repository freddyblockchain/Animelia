package com.mygdx.game.Abilities

interface Ability {
    fun onActivate()
    fun onDeactivate()

    val abilityId: AbilityId
}

fun getAbility (abilityId: AbilityId): Ability{
    return when(abilityId){
        AbilityId.BUTLERRIDING -> ButlerRiding()
        AbilityId.BUTLERSWITCH -> ButlerSwitch()
    }
}

enum class AbilityId {BUTLERRIDING, BUTLERSWITCH}

fun getAbilityBasedOnEnum(abilityString: String): Ability{
    return when(abilityString){
        "ButlerSwitch" -> ButlerSwitch()
        else -> ButlerSwitch()
    }
}