package com.mygdx.game
import com.mygdx.game.GameObjects.FloorButtonData
import com.mygdx.game.GameObjects.LockedDoorData
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
    val iid: String
}

@Serializable
data class EntityRefData(val entityIid: String, val layerIid: String, val levelIid: String, val worldIid: String)