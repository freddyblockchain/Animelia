package com.mygdx.game.GameObjects.MoveableObjects.EnemyAnimelia

import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.EnemyAnimelia
import com.mygdx.game.EntityRefData
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia.AnimeliaCustomFields
import com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia.IcePenguin
import com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia.getAnimeliaEntity
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

fun convertToEnemyAnimelia(gameObjectData: GameObjectData): GameObject{
    val animeliaEntityString = Json.decodeFromJsonElement<EnemyAnimeliaCustomFields>(gameObjectData.customFields).AnimeliaType
    val animeliaEntity = getAnimeliaEntity(animeliaEntityString)
    val entityRef = Json.decodeFromJsonElement<EnemyAnimeliaCustomFields>(gameObjectData.customFields).Entity_ref

    return createEnemyAnimelia(animeliaEntity, gameObjectData, entityRef)
}

fun createEnemyAnimelia(animeliaEntity: ANIMELIA_ENTITY, gameObjectData: GameObjectData, entityRefData: EntityRefData?): EnemyAnimelia{
    return when(animeliaEntity){
        ANIMELIA_ENTITY.FireArmadillo -> FireArmadilloEnemy(gameObjectData)
        ANIMELIA_ENTITY.IcePenguin -> IcePenguinEnemy(gameObjectData)
        ANIMELIA_ENTITY.Bird -> BirdEnemy(gameObjectData)
        ANIMELIA_ENTITY.Frog-> FrogEnemy(gameObjectData)
        ANIMELIA_ENTITY.IceBird -> IceBirdEnemy(gameObjectData, entityRefData)
        ANIMELIA_ENTITY.FireLion -> FireLionEnemy(gameObjectData, entityRefData)
        ANIMELIA_ENTITY.GuardFrog-> GuardFrogEnemy(gameObjectData, entityRefData)
        ANIMELIA_ENTITY.SoundBat-> SoundBatEnemy(gameObjectData, entityRefData)
        else -> FireArmadilloEnemy(gameObjectData)
    }
}

@Serializable
data class EnemyAnimeliaCustomFields(val AnimeliaType: String, val Entity_ref: EntityRefData?){

}
