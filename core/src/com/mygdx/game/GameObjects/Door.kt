package com.mygdx.game.GameObjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.EntityRefData
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Utils.RectanglePolygon
import com.mygdx.game.changeArea
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class Door(val lockedDoorData: GameObjectData): GameObject(lockedDoorData, Vector2(32f,32f)) {
    val customFields = Json.decodeFromJsonElement<LockedDoorCustomFields>(lockedDoorData.customFields)
    override val texture = DefaultTextureHandler.getTexture("EmptyDoor.png")
    override val layer = Layer.ONGROUND
    override val polygon = RectanglePolygon(Vector2(lockedDoorData.x + 8f, lockedDoorData.y - 8f),16f, 8f)
    override val collision = LockedDoorCollision(this)
    val areaIdentifierOfNewArea = customFields.Entrance.levelIid
    lateinit var exitEntrance: Entrance
    var unlocked = false

    fun unlockDoor(){
        unlocked = true
    }

    override fun initObject() {
        exitEntrance = AreaManager.getObjectWithIid(customFields.Entrance.entityIid) as Entrance
        if(!customFields.Locked){
            unlockDoor()
        }
    }

    override fun render(batch: SpriteBatch) {
        //Don't show the lock when unlocked
        if(!unlocked){
            super.render(batch)
        }
    }
}

@Serializable
data class LockedDoorCustomFields(val Entrance: EntityRefData,val Locked: Boolean){

}

class LockedDoorCollision(val door: Door): MoveCollision(){
    override var canMoveAfterCollision = true

    override fun collisionHappened(collidedObject: GameObject) {
        if(collidedObject is Player && door.unlocked){
            changeArea(Vector2(door.exitEntrance.x, door.exitEntrance.y), door.areaIdentifierOfNewArea)
        }
    }

}