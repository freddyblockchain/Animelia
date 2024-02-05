package com.mygdx.game
import kotlinx.serialization.Serializable

@Serializable
data class Root(val entities: Entities)
@Serializable
data class Entities(
    val FloorButton: List<FloorButtonData>,
    val LockedDoor: List<LockedDoorData>
)
interface GameObjectData {

}
@Serializable
data class FloorButtonData(
    val id: String,
    val iid: String,
    val x: Int,
    val y: Int
    // Include other relevant fields
) : GameObjectData
@Serializable
data class LockedDoorData(
    val id: String,
    val iid: String,
    val x: Int,
    val y: Int
    // Include other relevant fields
): GameObjectData