package com.mygdx.game
import kotlinx.serialization.Serializable

@Serializable
data class Root(val entities: Entities, val width: Int, val height: Int)
@Serializable
data class Entities(
    val FloorButton: List<FloorButtonData>,
    val LockedDoor: List<LockedDoorData>
)
interface GameObjectData {
    val x: Int
    //Need y to be var, so that we can change y, to align with the level maker y value.
    var y: Int
}
@Serializable
data class FloorButtonData(
    val id: String,
    val iid: String,
    override val x: Int,
    override var y: Int
    // Include other relevant fields
) : GameObjectData
@Serializable
data class LockedDoorData(
    val id: String,
    val iid: String,
    override val x: Int,
    override var y: Int
    // Include other relevant fields
): GameObjectData