package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.FriendlyAnimelia
import com.mygdx.game.Animelia.getAnimeliaEntity
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

fun convertToFriendlyAnimelia(gameObjectData: GameObjectData): GameObject{
    val animeliaEntityString = Json.decodeFromJsonElement<AnimeliaCustomFields>(gameObjectData.customFields).AnimeliaType
    val animeliaEntity = getAnimeliaEntity(animeliaEntityString)
    return createFriendlyAnimelia(animeliaEntity, gameObjectData)
}

fun createFriendlyAnimelia(animeliaEntity: ANIMELIA_ENTITY, gameObjectData: GameObjectData): FriendlyAnimelia{
    return when(animeliaEntity){
        ANIMELIA_ENTITY.FIRE_ARMADILLO -> FireArmadillo(gameObjectData)
        else -> FireArmadillo(gameObjectData)
    }
}

@Serializable
data class AnimeliaCustomFields(val AnimeliaType: String){

}
