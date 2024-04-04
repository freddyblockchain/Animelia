package com.mygdx.game.GameObjects.FloorButtons
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collisions.DefaultAreaEntranceCollition
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.EntityRefData
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.GameObjects.MoveableObjects.Butler
import com.mygdx.game.GameObjects.Toggelable
import com.mygdx.game.Managers.AreaManager
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class ToggleButton(toggleButtonData: GameObjectData): GameObject(toggleButtonData, Vector2(32f,32f)) {
    val customFields = Json.decodeFromJsonElement<ToggleButtonCustomFields>(toggleButtonData.customFields)
    override val texture = DefaultTextureHandler.getTexture("GateButton.png")
    override val layer = Layer.AIR
    lateinit var toggleObject: GameObject
    override var collision: MoveCollision = ToggleButtonCollision(this)
    var activated = false

    override fun render(batch: SpriteBatch) {
        sprite.color = if (activated) Color.GREEN else Color.WHITE
        super.render(batch)
    }
    override fun initObject() {
        toggleObject = AreaManager.getObjectWithIid(customFields.Entity_ref.entityIid)
    }
}
@Serializable
data class ToggleButtonCustomFields(val Entity_ref: EntityRefData){

}

class ToggleButtonCollision(val toggleButton: ToggleButton) :
    DefaultAreaEntranceCollition() {
    override fun movedInsideAction(objectEntered: GameObject) {
        val objectIsPlayerOrButler = objectEntered is Player || objectEntered is Butler
        if(objectIsPlayerOrButler){
            val toggelable = toggleButton.toggleObject as Toggelable
            if(toggelable.toggleCounter <= 0){
                AreaManager.getActiveArea()!!.gameObjects.remove(toggleButton.toggleObject)
            }
            toggelable.toggleOn()
        }
    }

    override fun movedOutsideAction(objectLeaved: GameObject) {
        val objectIsPlayerOrButler = objectLeaved is Player || objectLeaved is Butler
        if(objectIsPlayerOrButler){
            val toggelable = toggleButton.toggleObject as Toggelable
            toggelable.toggleOff()
            if(toggelable.toggleCounter <= 0){
                AreaManager.getActiveArea()!!.gameObjects.add(toggleButton.toggleObject)
            }
        }
    }

    override var canMoveAfterCollision = true

}