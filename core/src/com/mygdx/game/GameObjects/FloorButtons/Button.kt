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
import com.mygdx.game.GameObjects.Door
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.GameObjects.MoveableObjects.Butler
import com.mygdx.game.GameObjects.Triggers.AbilityCustomFields
import com.mygdx.game.Managers.AreaManager
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class Button(floorButtonData: GameObjectData): GameObject(floorButtonData, Vector2(32f,32f)) {
    val customFields = Json.decodeFromJsonElement<FloorButtonCustomFields>(floorButtonData.customFields)
    override val texture = DefaultTextureHandler.getTexture("GateButton.png")
    override val layer = Layer.AIR
    lateinit var door: Door
    val otherButtons: MutableList<Button> = mutableListOf()
    override var collision: MoveCollision = CanMoveCollision()
    var activated = false

    override fun render(batch: SpriteBatch) {
        sprite.color = if (activated) Color.GREEN else Color.WHITE
        super.render(batch)
    }
    override fun initObject() {
        door = AreaManager.getObjectWithIid(customFields.Entity_ref2.entityIid) as Door
        collision = ButtonCollision(door, this)
        customFields.Entity_ref.forEach {
            otherButtons.add(AreaManager.getObjectWithIid(it.entityIid) as Button)
        }
    }
}

@Serializable
data class FloorButtonCustomFields(val Entity_ref: List<EntityRefData>, val Entity_ref2: EntityRefData){

}

class ButtonCollision(val door: Door, val button: Button) :
    DefaultAreaEntranceCollition() {
    override fun movedInsideAction(objectEntered: GameObject) {
        val objectIsPlayerOrButler = objectEntered is Player || objectEntered is Butler
        val allButtonsAreActivated = (button.otherButtons.isEmpty() || button.otherButtons.all { it.activated })
        if(objectIsPlayerOrButler){
            button.activated = true
        }
        if(allButtonsAreActivated){
            door.unlockDoor()
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