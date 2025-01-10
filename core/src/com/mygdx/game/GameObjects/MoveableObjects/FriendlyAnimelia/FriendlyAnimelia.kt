package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.FriendlyAnimeliaInCity
import com.mygdx.game.Animelia.FriendlyAnimeliaInWorld
import com.mygdx.game.EntityRefData
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
fun convertToFriendlyAnimelia(gameObjectData: GameObjectData): GameObject{
    val animeliaFields = Json.decodeFromJsonElement<AnimeliaCustomFields>(gameObjectData.customFields)
    val animeliaEntity = getAnimeliaEntity(animeliaFields.AnimeliaType)

    val friendlyAnimelia = createFriendlyAnimelia(animeliaEntity, gameObjectData, animeliaFields.AnimeliaCityPos, animeliaFields.CustomData)
    return friendlyAnimelia
}

fun createFriendlyAnimelia(animeliaEntity: ANIMELIA_ENTITY, gameObjectData: GameObjectData, cityPosRefData: EntityRefData, CustomData: List<EntityRefData>): FriendlyAnimeliaInWorld{
    return when(animeliaEntity){
        ANIMELIA_ENTITY.FireArmadillo -> FireArmadillo(gameObjectData, cityPosRefData)
        ANIMELIA_ENTITY.IcePenguin-> IcePenguin(gameObjectData, cityPosRefData)
        ANIMELIA_ENTITY.IceYeti -> IceYeti(gameObjectData, cityPosRefData, CustomData)
        ANIMELIA_ENTITY.IceBird -> IceBird(gameObjectData, cityPosRefData)
        ANIMELIA_ENTITY.MetalBird -> MetalBird(gameObjectData, cityPosRefData, CustomData)
        ANIMELIA_ENTITY.GuardFrog -> GuardFrog(gameObjectData, cityPosRefData, CustomData)
        ANIMELIA_ENTITY.KingFrog -> KingFrog(gameObjectData, cityPosRefData, CustomData)
        ANIMELIA_ENTITY.Frog -> Frog(gameObjectData, cityPosRefData)
        ANIMELIA_ENTITY.Bird -> Bird(gameObjectData, cityPosRefData)
        else -> FireArmadillo(gameObjectData, cityPosRefData)
    }
}
fun createFriendlyAnimeliaInCity(animeliaEntity: ANIMELIA_ENTITY, position: Vector2): FriendlyAnimeliaInCity{
    val gameObjectData = GameObjectData(x = position.x.toInt(), y = position.y.toInt(), height = 32, width = 32)
    return when(animeliaEntity){
        ANIMELIA_ENTITY.FireArmadillo -> FireArmadilloInCity(gameObjectData)
        ANIMELIA_ENTITY.IcePenguin-> IcePenguinInCity(gameObjectData)
        ANIMELIA_ENTITY.IceBird -> IceBirdInCity(gameObjectData)
        ANIMELIA_ENTITY.MetalBird -> MetalBirdInCity(gameObjectData)
        ANIMELIA_ENTITY.KingFrog -> KingFrogInCity(gameObjectData)
        ANIMELIA_ENTITY.GuardFrog -> GuardFrogInCity(gameObjectData)
        ANIMELIA_ENTITY.IceYeti  -> IceYetiInCity(gameObjectData)
        ANIMELIA_ENTITY.Frog -> FrogInCity(gameObjectData)
        ANIMELIA_ENTITY.Bird -> BirdInCity(gameObjectData)
        else -> FireArmadilloInCity(gameObjectData)
    }
}
fun getAnimeliaEntity(animeliaType: String): ANIMELIA_ENTITY {
    return when(animeliaType){
        "FireArmadillo" -> ANIMELIA_ENTITY.FireArmadillo
        "FireHippo" -> ANIMELIA_ENTITY.FireHippo
        "IcePenguin" -> ANIMELIA_ENTITY.IcePenguin
        "IceYeti" -> ANIMELIA_ENTITY.IceYeti
        "Bird" -> ANIMELIA_ENTITY.Bird
        "IceBird" -> ANIMELIA_ENTITY.IceBird
        "MetalBird" -> ANIMELIA_ENTITY.MetalBird
        "GuardFrog" -> ANIMELIA_ENTITY.GuardFrog
        "KingFrog" -> ANIMELIA_ENTITY.KingFrog
        "Frog" -> ANIMELIA_ENTITY.Frog
        "Bird" -> ANIMELIA_ENTITY.Bird
        else -> ANIMELIA_ENTITY.FireArmadillo
    }
}

@Serializable
data class AnimeliaCustomFields(val AnimeliaType: String, val AnimeliaCityPos: EntityRefData, val CustomData: List<EntityRefData>){

}
