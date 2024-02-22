package com.mygdx.game.GameObjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Utils.RectanglePolygon
import kotlinx.serialization.Serializable

class LockedDoor(val lockedDoorData: LockedDoorData): GameObject(lockedDoorData, Vector2(32f,32f)) {
    override val texture = DefaultTextureHandler.getTexture("EmptyDoor.png")
    override val layer = Layer.ONGROUND
    override val polygon = RectanglePolygon(Vector2(lockedDoorData.x + 8f, lockedDoorData.y - 8f),16f, 8f)
    override val collision = LockedDoorCollision(this)
    val areaIdentifierOfNewArea = lockedDoorData.customFields.Entrance.levelIid
    lateinit var exitEntrance: Entrance
    var unlocked = false

    fun unlockDoor(){
        unlocked = true
    }

    override fun initObject() {
        exitEntrance = AreaManager.getObjectWithIid(lockedDoorData.customFields.Entrance.entityIid) as Entrance
    }

    override fun render(batch: SpriteBatch) {
        //Don't show the lock when unlocked
        if(!unlocked){
            super.render(batch)
        }
    }
}

@Serializable
data class LockedDoorData(
    val id: String,
    override val iid: String,
    override val x: Int,
    override var y: Int,
    override val width: Int,
    override val height: Int,
    val customFields: LockedDoorCustomFields
    // Include other relevant fields
): GameObjectData

@Serializable
data class LockedDoorCustomFields(val Entrance: EntityRefData){

}

class LockedDoorCollision(val lockedDoor: LockedDoor): MoveCollision(){
    override var canMoveAfterCollision = true

    override fun collisionHappened(collidedObject: GameObject) {
        if(collidedObject is Player && lockedDoor.unlocked){
            val newPos = Vector2(lockedDoor.exitEntrance.x, lockedDoor.exitEntrance.y)
            AreaManager.changeActiveArea(lockedDoor.areaIdentifierOfNewArea)
            player.setPosition(newPos)
            player.startingPosition = newPos
            if(butler.active){
                butler.setPosition(newPos)
            }
        }
    }

}