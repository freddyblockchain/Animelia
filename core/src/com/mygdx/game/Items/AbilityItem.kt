package com.mygdx.game.Items

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.Ability.convertNameToAbility
import com.mygdx.game.Ability.getIconFromType
import com.mygdx.game.GameObjectData
import com.mygdx.game.generalSaveState
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class AbilityItem(gameObjectData: GameObjectData) : WorldItem(gameObjectData) {

    val abilityString = Json.decodeFromJsonElement<AbilityCustomFields>(gameObjectData.customFields).Ability
    val abilityData = convertNameToAbility(abilityString)
    val abilityName = abilityData.abilityName
    override val texture = getIconFromType(abilityData.ELEMENTALTYPES)
    override val itemAquiredText = "You found the ${this.abilityName.name} Ability"

    init {
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    override fun itemGained() {
        generalSaveState.inventory.ownedAbilities.add(abilityData.abilityName)
        generalSaveState.updateSaveState()
        super.itemGained()
    }

}

@Serializable
data class AbilityCustomFields(val Ability: String){

}