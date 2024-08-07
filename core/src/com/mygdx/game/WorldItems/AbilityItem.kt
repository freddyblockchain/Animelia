package com.mygdx.game.WorldItems

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Ability.AbilityType
import com.mygdx.game.Ability.convertAbilityToType
import com.mygdx.game.Ability.convertAbilityTypeToData
import com.mygdx.game.Animation.TextAnimation
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.plus
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class AbilityItem(gameObjectData: GameObjectData) : WorldItem(gameObjectData) {

    val abilityString = Json.decodeFromJsonElement<AbilityCustomFields>(gameObjectData.customFields).Ability
    val abilityType = convertAbilityToType(abilityString)
    val abilityData = convertAbilityTypeToData(abilityType)
    override val texture = DefaultTextureHandler.getTexture(abilityData.imageIcon)
    override val itemAquiredText = "You found the ${this.abilityType.name} Ability"

    init {
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

}

@Serializable
data class AbilityCustomFields(val Ability: String){

}