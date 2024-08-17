package com.mygdx.game.GameObjects.Structures

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Animelia.Egg
import com.mygdx.game.Collisions.CannotMoveCollision
import com.mygdx.game.Collition.InputCollision
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameModes.UIMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.DoorCustomFields
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.UI.Scene2d.Screens.DialogScreen
import com.mygdx.game.UI.Scene2d.Screens.ReincarnationScreen
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class House(gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())) {
    val customFields = Json.decodeFromJsonElement<HouseCustomFields>(gameObjectData.customFields)
    val textureName = getHouseTexture(customFields.House)
    override val texture = DefaultTextureHandler.getTexture(textureName)
    override val layer = Layer.ONGROUND
    override val collision = CannotMoveCollision()

    init {
        this.polygon.scale(-0.2f)
    }
}
@Serializable
data class HouseCustomFields(val House: String){

}

fun getHouseTexture(houseAsString: String): String{
    return when(houseAsString){
        "Iglo" -> "iglo.png"
        else -> "iglo.png"
    }
}