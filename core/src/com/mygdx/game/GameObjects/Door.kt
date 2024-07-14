package com.mygdx.game.GameObjects

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Enums.getDirectionFromString
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.Managers.AreaManager
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlin.system.exitProcess

class Door(val gameObjectData: GameObjectData): GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())) {
    val customFields = Json.decodeFromJsonElement<DoorCustomFields>(gameObjectData.customFields)
    val direction = getDirectionFromString(customFields.Direction)
    override val texture = DefaultTextureHandler.getTexture("EmptyDoor.png")
    override val layer = Layer.ONGROUND
    //override val polygon = RectanglePolygon(Vector2(gameObjectData.x + 8f, gameObjectData.y - 8f),16f, 8f)
    override val collision = DoorCollision(this)
    lateinit var exitDoor: Door

    override fun initObject() {
        exitDoor = AreaManager.getObjectWithIid(customFields.Door.entityIid) as Door
    }
}

@Serializable
data class DoorCustomFields(val Door: EntityRefData, val Direction: String){

}

class DoorCollision(val door: Door): MoveCollision(){
    override var canMoveAfterCollision = true

    override fun collisionHappened(collidedObject: GameObject) {
        if(collidedObject is Player && player.direction == door.direction){
            changeArea(Vector2(door.exitDoor.x, door.exitDoor.y), door.exitDoor.areaIdentifier)
        }
    }

}