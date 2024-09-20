package com.mygdx.game.GameObjects.Structures.Railway

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collisions.CanMoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Enums.getDirectionFromString
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.Hazards.ConveyerBelt.ConveyerBeltCustomFields
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class Railway(val gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())) {

    val directionString = Json.decodeFromJsonElement<ConveyerBeltCustomFields>(gameObjectData.customFields).Direction
    var direction = getDirectionFromString(directionString)
    override val texture = DefaultTextureHandler.getTexture("HealthyRails.png")
    val brokenRails  = DefaultTextureHandler.getTexture("BrokenRails.png")
    override val layer = Layer.ONGROUND
    override val collision = CanMoveCollision()

    lateinit var brokenRailway: BrokenRailway


    override fun initObject() {
        sprite.setSize(gameObjectData.width.toFloat() / 2, gameObjectData.height.toFloat())
        val brokenRailwayStart = Vector2(sprite.x + sprite.width, sprite.y)
        brokenRailway = BrokenRailway(GameObjectData(x = brokenRailwayStart.x.toInt(), y = brokenRailwayStart.y.toInt(), width = sprite.width.toInt(), height = sprite.height.toInt()))
        brokenRailway.add()
        super.initObject()
    }
}

@Serializable
data class RailwayCustomFields(val Direction: String){

}
