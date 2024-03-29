package com.mygdx.game.GameObjects.FloorButtons

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Collisions.CanMoveCollision
import com.mygdx.game.Collisions.DefaultAreaEntranceCollition
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.LockedDoor
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.GameObjects.MoveableObjects.Butler
import com.mygdx.game.Managers.AreaManager
import kotlinx.serialization.Serializable

class Button(val floorButtonData: FloorButtonData): GameObject(floorButtonData, Vector2(32f,32f)) {
    override val texture = DefaultTextureHandler.getTexture("GateButton.png")
    override val layer = Layer.AIR
    lateinit var lockedDoor: LockedDoor
    val otherButtons: MutableList<Button> = mutableListOf()
    override var collision: MoveCollision = CanMoveCollision()
    var activated = false

    override fun render(batch: SpriteBatch) {
        sprite.color = if (activated) Color.GREEN else Color.WHITE
        super.render(batch)
    }
    override fun initObject() {
        lockedDoor = AreaManager.getObjectWithIid(floorButtonData.customFields.Entity_ref2.entityIid) as LockedDoor
        collision = ButtonCollision(lockedDoor, this)
        floorButtonData.customFields.Entity_ref.forEach {
            otherButtons.add(AreaManager.getObjectWithIid(it.entityIid) as Button)
        }
    }
}


@Serializable
data class FloorButtonData(
    val id: String,
    override val iid: String,
    override val x: Int,
    override var y: Int,
    override val width: Int,
    override val height: Int,

    val customFields: FloorButtonCustomFields
    // Include other relevant fields
) : GameObjectData

@Serializable
data class FloorButtonCustomFields(val Entity_ref: List<EntityRefData>, val Entity_ref2: EntityRefData){

}

class ButtonCollision(val lockedDoor: LockedDoor, val button: Button) :
    DefaultAreaEntranceCollition() {
    override fun movedInsideAction(objectEntered: GameObject) {
        val objectIsPlayerOrButler = objectEntered is Player || objectEntered is Butler
        val allButtonsAreActivated = (button.otherButtons.isEmpty() || button.otherButtons.all { it.activated })
        if(objectIsPlayerOrButler){
            button.activated = true
        }
        if(allButtonsAreActivated){
            lockedDoor.unlockDoor()
        }
    }

    override fun movedOutsideAction(objectLeaved: GameObject) {
        val butlerInside = insideCollition.getOrDefault(butler,false)
        val playerInside = insideCollition.getOrDefault(player,false)
        if(!playerInside && !butlerInside){
            button.activated = false
        }
    }

    override var canMoveAfterCollision = true

}