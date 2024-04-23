package com.mygdx.game
import com.mygdx.game.GameObjects.Door
import com.mygdx.game.GameObjects.Entrance
import com.mygdx.game.GameObjects.Hazards.*
import com.mygdx.game.GameObjects.Triggers.AbilityTrigger
import com.mygdx.game.GameObjects.Triggers.SpeechActivationTrigger
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

@Serializable
data class Root(val x: Int, val y: Int, val entities: Entities, val width: Int, val height: Int, val uniqueIdentifer: String, val identifier: String, val customFields: RootCustomFields)

@Serializable
data class RootCustomFields(val World: String)
@Serializable
data class Entities(
    val Door: List<GameObjectData> = listOf(),
    val Entrance: List<GameObjectData> = listOf(),
    val Spikes: List<GameObjectData> = listOf(),
    val DialogueSensor: List<GameObjectData> = listOf(),
    val Water: List<GameObjectData> = listOf(),
    val Thorns: List<GameObjectData> = listOf(),
    val Ability: List<GameObjectData> = listOf(),
    val BreakableObject: List<GameObjectData> = listOf(),
    val InvisibleWall: List<GameObjectData> = listOf(),
    val Lava: List<GameObjectData> = listOf(),
)
fun initMappings(){
    GameObjectFactory.register("Door", ::Door)
    GameObjectFactory.register("Entrance", ::Entrance)
    GameObjectFactory.register("Spikes", ::Spike)
    GameObjectFactory.register("DialogueSensor", ::SpeechActivationTrigger)
    GameObjectFactory.register("Water", ::Water)
    GameObjectFactory.register("Thorns", ::Thorns)
    GameObjectFactory.register("Ability", ::AbilityTrigger)
    GameObjectFactory.register("BreakableObject", ::BreakableObject)
    GameObjectFactory.register("InvisibleWall", ::InvisibleWall)
    GameObjectFactory.register("Lava", ::Lava)
}
@Serializable
open class GameObjectData( var x: Int = 0,
                           var y: Int = 0,
                           val iid: String = "",
                           val id: String = "",
                           val width: Int = 0,
                           val height: Int = 0,
                           val customFields: JsonElement = JsonObject(emptyMap()))

@Serializable
data class EntityRefData(val entityIid: String, val layerIid: String, val levelIid: String, val worldIid: String)