package com.mygdx.game.GameObjects.MoveableObjects.EnemyAnimelia

import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.EnemyAnimelia
import com.mygdx.game.Animelia.getAnimeliaEntity
import com.mygdx.game.EntityRefData
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia.AnimeliaCustomFields
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

fun convertToEnemyAnimelia(gameObjectData: GameObjectData): GameObject{
    val animeliaEntityString = Json.decodeFromJsonElement<EnemyAnimeliaCustomFields>(gameObjectData.customFields).AnimeliaType
    val animeliaEntity = getAnimeliaEntity(animeliaEntityString)

    return createEnemyAnimelia(animeliaEntity, gameObjectData)
}

fun createEnemyAnimelia(animeliaEntity: ANIMELIA_ENTITY, gameObjectData: GameObjectData): EnemyAnimelia{
    return when(animeliaEntity){
        ANIMELIA_ENTITY.FireArmadillo -> FireArmadilloEnemy(gameObjectData)
        else -> FireArmadilloEnemy(gameObjectData)
    }
}

@Serializable
data class EnemyAnimeliaCustomFields(val AnimeliaType: String){

}
