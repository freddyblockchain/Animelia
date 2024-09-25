package com.mygdx.game.GameObjects.Structures.Railway

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
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
    override val layer = Layer.ONGROUND
    override val collision = CanMoveCollision()

    lateinit var brokenRailway: BrokenRailway

    lateinit var railsRegion: TextureRegion

    fun fixed(){
        sprite.setRegion(0f,0f,sprite.width, sprite.height)
    }


    override fun initObject() {
        sprite.setSize(gameObjectData.width.toFloat(), gameObjectData.height.toFloat())
        /*val brokenRailwayStart = Vector2(sprite.x, sprite.y)
        brokenRailway = BrokenRailway(this, GameObjectData(x = brokenRailwayStart.x.toInt(), y = brokenRailwayStart.y.toInt(), width = sprite.width.toInt(), height = sprite.height.toInt()))
        brokenRailway.add()*/
        val cart = Cart(GameObjectData(x = sprite.x.toInt(), y= sprite.y.toInt() + 4, width = 48, height = 32))
        cart.add()
        super.initObject()

       sprite.setRegion(0,0,gameObjectData.width, gameObjectData.height)
    }
}

@Serializable
data class RailwayCustomFields(val Direction: String){

}
