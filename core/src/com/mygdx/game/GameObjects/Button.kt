package com.mygdx.game.GameObjects

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collisions.CanMoveCollision
import com.mygdx.game.Collisions.DefaultAreaEntranceCollition
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.EntityRefData
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObject.GameObject
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.Managers.AreaManager
import kotlinx.serialization.Serializable

class Button(val floorButtonData: FloorButtonData): GameObject(floorButtonData, Vector2(32f,32f)) {
    override val texture = DefaultTextureHandler.getTexture("GateButton.png")
    override val layer = Layer.ONGROUND
    lateinit var lockedDoor: LockedDoor
    override var collision: MoveCollision = CanMoveCollision()

    override fun initObject() {
        lockedDoor = AreaManager.getObjectWithIid(floorButtonData.customFields.Entity_ref2.entityIid) as LockedDoor
        collision = ButtonCollision(lockedDoor)
    }
}


@Serializable
data class FloorButtonData(
    val id: String,
    override val iid: String,
    override val x: Int,
    override var y: Int,
    val customFields: FloorButtonCustomFields
    // Include other relevant fields
) : GameObjectData

@Serializable
data class FloorButtonCustomFields(val Entity_ref: List<EntityRefData>, val Entity_ref2: EntityRefData){

}

class ButtonCollision(val lockedDoor: LockedDoor) :
    DefaultAreaEntranceCollition() {
    override fun movedInsideAction(objectEntered: GameObject) {
        if(objectEntered is Player){
            lockedDoor.unlockDoor()
        }
    }

    override fun movedOutsideAction(objectLeaved: GameObject) {
    }

    override var canMoveAfterCollision = true

}