package com.mygdx.game
import com.mygdx.game.GameObjects.Door
import com.mygdx.game.GameObjects.Entrance
import com.mygdx.game.GameObjects.FloorButtons.Button
import com.mygdx.game.GameObjects.FloorButtons.ToggleButton
import com.mygdx.game.GameObjects.Hazards.Spike
import com.mygdx.game.GameObjects.Hazards.Thorns
import com.mygdx.game.GameObjects.Hazards.Water
import com.mygdx.game.GameObjects.Memory.MemoryPad
import com.mygdx.game.GameObjects.Memory.MemoryStone
import com.mygdx.game.GameObjects.MoveableObjects.ButlerActivationTrigger
import com.mygdx.game.GameObjects.Triggers.AbilityTrigger
import com.mygdx.game.GameObjects.Triggers.SpeechActivationTrigger
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

@Serializable
data class Root(val entities: Entities, val width: Int, val height: Int, val uniqueIdentifer: String, val identifier: String)
@Serializable
data class Entities(
    val FloorButton: List<GameObjectData> = listOf(),
    val Door: List<GameObjectData> = listOf(),
    val Entrance: List<GameObjectData> = listOf(),
    val Butler: List<GameObjectData> = listOf(),
    val Spikes: List<GameObjectData> = listOf(),
    val MemoryPad: List<GameObjectData> = listOf(),
    val MemoryStone: List<GameObjectData> = listOf(),
    val DialogueSensor: List<GameObjectData> = listOf(),
    val Water: List<GameObjectData> = listOf(),
    val ToggleButton: List<GameObjectData> = listOf(),
    val Thorns: List<GameObjectData> = listOf(),
    val Ability: List<GameObjectData> = listOf()
)
fun initMappings(){
    GameObjectFactory.register("FloorButton", ::Button)
    GameObjectFactory.register("Door", ::Door)
    GameObjectFactory.register("Entrance", ::Entrance)
    GameObjectFactory.register("Butler", ::ButlerActivationTrigger)
    GameObjectFactory.register("Spikes", ::Spike)
    GameObjectFactory.register("MemoryPad", ::MemoryPad)
    GameObjectFactory.register("MemoryStone", ::MemoryStone)
    GameObjectFactory.register("DialogueSensor", ::SpeechActivationTrigger)
    GameObjectFactory.register("Water", ::Water)
    GameObjectFactory.register("ToggleButton", ::ToggleButton)
    GameObjectFactory.register("Thorns", ::Thorns)
    GameObjectFactory.register("Ability", ::AbilityTrigger)
}

interface CustomFields {

}
@Serializable
open class GameObjectData( val x: Int = 0,
                           var y: Int = 0,
                           val iid: String = "",
                           val id: String = "",
                           val width: Int = 0,
                           val height: Int = 0,
                           val customFields: JsonElement = JsonObject(emptyMap()))

@Serializable
data class EntityRefData(val entityIid: String, val layerIid: String, val levelIid: String, val worldIid: String)