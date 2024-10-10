package com.mygdx.game
import com.mygdx.game.Items.AbilityItem
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.Door
import com.mygdx.game.GameObjects.Hazards.*
import com.mygdx.game.GameObjects.Hazards.ConveyerBelt.ConveyerBelt
import com.mygdx.game.GameObjects.MoveableObjects.EnemyAnimelia.convertToEnemyAnimelia
import com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia.convertToFriendlyAnimelia
import com.mygdx.game.GameObjects.Other.SpiritOfAnimelia
import com.mygdx.game.GameObjects.Sign
import com.mygdx.game.GameObjects.Structures.Fountain
import com.mygdx.game.GameObjects.Structures.House
import com.mygdx.game.GameObjects.Structures.Railway.Railway
import com.mygdx.game.GameObjects.Structures.TrainingStation
import com.mygdx.game.Items.BookItem
import com.mygdx.game.Items.EggItem
import com.mygdx.game.Items.MaterialItem
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
    val Lava: List<GameObjectData> = listOf(),
    val IceCone: List<GameObjectData> = listOf(),
    val Rock: List<GameObjectData> = listOf(),
    val Animelia: List<GameObjectData> = listOf(),
    val AnimeliaEnemy: List<GameObjectData> = listOf(),
    val TrainingStation: List<GameObjectData> = listOf(),
    val Ability: List<GameObjectData> = listOf(),
    val AnivolutionBook: List<GameObjectData> = listOf(),
    val Item: List<GameObjectData> = listOf(),
    val Egg: List<GameObjectData> = listOf(),
    val ConveyerBelt: List<GameObjectData> = listOf(),
    val FireLightBulb: List<GameObjectData> = listOf(),
    val Cliffside: List<GameObjectData> = listOf(),
    val Position: List<GameObjectData> = listOf(),
    val Fountain: List<GameObjectData> = listOf(),
    val House: List<GameObjectData> = listOf(),
    val Sign: List<GameObjectData> = listOf(),
    val SpiritOfAnimelia: List<GameObjectData> = listOf(),
    val Railway: List<GameObjectData> = listOf(),
    val BoxingGlove: List<GameObjectData> = listOf(),
    val IceFloor: List<GameObjectData> = listOf(),
)
fun initMappings(){
    GameObjectFactory.register("Door", ::Door)
    GameObjectFactory.register("Lava", ::Lava)
    GameObjectFactory.register("IceCone", ::IceCone)
    GameObjectFactory.register("Rock", ::Rock)
    GameObjectFactory.register("Animelia", ::convertToFriendlyAnimelia)
    GameObjectFactory.register("AnimeliaEnemy", ::convertToEnemyAnimelia)
    GameObjectFactory.register("TrainingStation", ::TrainingStation)
    GameObjectFactory.register("Ability", ::AbilityItem)
    GameObjectFactory.register("ConveyerBelt", ::ConveyerBelt)
    GameObjectFactory.register("FireLightBulb", ::LightBulb)
    GameObjectFactory.register("Cliffside", ::Cliffside)
    GameObjectFactory.register("Position", ::AnimeliaPosition)
    GameObjectFactory.register("Fountain", ::Fountain)
    GameObjectFactory.register("AnivolutionBook", ::BookItem)
    GameObjectFactory.register("Item", ::MaterialItem)
    GameObjectFactory.register("Egg", ::EggItem)
    GameObjectFactory.register("House", ::House)
    GameObjectFactory.register("Sign", ::Sign)
    GameObjectFactory.register("SpiritOfAnimelia", ::SpiritOfAnimelia)
    GameObjectFactory.register("Railway", ::Railway)
    GameObjectFactory.register("BoxingGlove", ::BoxingGlove)
    GameObjectFactory.register("IceFloor", ::IceFloor)
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