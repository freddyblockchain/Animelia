package com.mygdx.game.Inventory

import com.mygdx.game.Ability.AbilityName
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import kotlinx.serialization.Serializable

@Serializable
data class AbilityPreference(val numkey: Int, val abilityName: AbilityName)