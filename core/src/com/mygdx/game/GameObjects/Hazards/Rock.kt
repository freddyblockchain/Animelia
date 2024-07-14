package com.mygdx.game.GameObjects.Hazards

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collisions.CanMoveCollision
import com.mygdx.game.Collisions.CannotMoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.EntityRefData
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.DoorCustomFields
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.renderRepeatedTexture
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class Rock(gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())) {
    override val texture = DefaultTextureHandler.getTexture("MediumRock.png")
    override val layer = Layer.ONGROUND
    override val collision = CannotMoveCollision()
    val customFields = Json.decodeFromJsonElement<RockCustomFields>(gameObjectData.customFields)
}

@Serializable
data class RockCustomFields(val StrengthToBreak: Int){

}