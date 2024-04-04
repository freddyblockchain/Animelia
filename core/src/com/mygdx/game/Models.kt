package com.mygdx.game
import com.mygdx.game.GameObjects.Door
import com.mygdx.game.GameObjects.Entrance
import com.mygdx.game.GameObjects.EntranceData
import com.mygdx.game.GameObjects.FloorButtons.Button
import com.mygdx.game.GameObjects.FloorButtons.FloorButtonData
import com.mygdx.game.GameObjects.FloorButtons.ToggleButton
import com.mygdx.game.GameObjects.FloorButtons.ToggleButtonData
import com.mygdx.game.GameObjects.Hazards.*
import com.mygdx.game.GameObjects.LockedDoorData
import com.mygdx.game.GameObjects.Memory.MemoryPad
import com.mygdx.game.GameObjects.Memory.MemoryPadData
import com.mygdx.game.GameObjects.Memory.MemoryStone
import com.mygdx.game.GameObjects.Memory.MemoryStoneData
import com.mygdx.game.GameObjects.MoveableObjects.ButlerActivationTrigger
import com.mygdx.game.GameObjects.MoveableObjects.ButlerData
import com.mygdx.game.GameObjects.Triggers.AbilityTrigger
import com.mygdx.game.GameObjects.Triggers.AbilityTriggerData
import com.mygdx.game.GameObjects.Triggers.SpeechActivationTrigger
import com.mygdx.game.GameObjects.Triggers.SpeechData
import kotlinx.serialization.Serializable

@Serializable
data class Root(val entities: Entities, val width: Int, val height: Int, val uniqueIdentifer: String, val identifier: String)
@Serializable
data class Entities(
    val FloorButton: List<FloorButtonData> = listOf(),
    val Door: List<LockedDoorData> = listOf(),
    val Entrance: List<EntranceData> = listOf(),
    val Butler: List<ButlerData> = listOf(),
    val Spikes: List<SpikeData> = listOf(),
    val MemoryPad: List<MemoryPadData> = listOf(),
    val MemoryStone: List<MemoryStoneData> = listOf(),
    val DialogueSensor: List<SpeechData> = listOf(),
    val Water: List<WaterData> = listOf(),
    val ToggleButton: List<ToggleButtonData> = listOf(),
    val Thorns: List<ThornsData> = listOf(),
    val Ability: List<AbilityTriggerData> = listOf()
)
fun initMappings(){
    GameObjectFactory.register(FloorButtonData::class.java) {
        Button(it as FloorButtonData)
    }
    GameObjectFactory.register(LockedDoorData::class.java) {
        Door(it as LockedDoorData)
    }
    GameObjectFactory.register(EntranceData::class.java) {
        Entrance(it as EntranceData)
    }
    GameObjectFactory.register(ButlerData::class.java) {
        ButlerActivationTrigger(it as ButlerData)
    }
    GameObjectFactory.register(SpikeData::class.java) {
        Spike(it as SpikeData)
    }
    GameObjectFactory.register(MemoryPadData::class.java) {
        MemoryPad(it as MemoryPadData)
    }
    GameObjectFactory.register(MemoryStoneData::class.java) {
        MemoryStone(it as MemoryStoneData)
    }
    GameObjectFactory.register(SpeechData::class.java) {
        SpeechActivationTrigger(it as SpeechData)
    }
    GameObjectFactory.register(WaterData::class.java) {
        Water(it as WaterData)
    }
    GameObjectFactory.register(ToggleButtonData::class.java) {
        ToggleButton(it as ToggleButtonData)
    }
    GameObjectFactory.register(ThornsData::class.java) {
        Thorns(it as ThornsData)
    }
    GameObjectFactory.register(AbilityTriggerData::class.java) {
        AbilityTrigger(it as AbilityTriggerData)
    }
}
interface GameObjectData {
    val x: Int
    var y: Int
    val iid: String
    val width: Int
    val height: Int

}
@Serializable
data class EntityRefData(val entityIid: String, val layerIid: String, val levelIid: String, val worldIid: String)