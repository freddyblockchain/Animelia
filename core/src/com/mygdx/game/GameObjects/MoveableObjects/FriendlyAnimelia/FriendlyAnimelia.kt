package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.FriendlyAnimelia
import com.mygdx.game.Animelia.getAnimeliaEntity
import com.mygdx.game.EntityRefData
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

fun convertToFriendlyAnimelia(gameObjectData: GameObjectData): GameObject{
    val animeliaFields = Json.decodeFromJsonElement<AnimeliaCustomFields>(gameObjectData.customFields)
    val animeliaEntity = getAnimeliaEntity(animeliaFields.AnimeliaType)

    return createFriendlyAnimelia(animeliaEntity, gameObjectData, animeliaFields.AnimeliaCityPos)
}

fun createFriendlyAnimelia(animeliaEntity: ANIMELIA_ENTITY, gameObjectData: GameObjectData, cityPosRefData: EntityRefData): FriendlyAnimelia{
    return when(animeliaEntity){
        ANIMELIA_ENTITY.FireArmadillo -> FireArmadillo(gameObjectData, cityPosRefData)
        else -> FireArmadillo(gameObjectData, cityPosRefData)
    }
}

@Serializable
data class AnimeliaCustomFields(val AnimeliaType: String, val AnimeliaCityPos: EntityRefData){

}
