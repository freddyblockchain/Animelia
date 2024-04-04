package com.mygdx.game.GameObjects.Triggers

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Animation.Conversation
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Managers.AreaManager
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class SpeechActivationTrigger(val gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2( gameObjectData.width.toFloat(),gameObjectData.height.toFloat())){
    val customFields = Json.decodeFromJsonElement<SpeechActivationCustomFields>(gameObjectData.customFields)
    override val texture = DefaultTextureHandler.getTexture("inputbox.png")
    override val layer = Layer.PERSON
    override val collision = SpeechActivationCollision(this)
}
@Serializable
data class SpeechActivationCustomFields(val dialogueName: String){

}
class SpeechActivationCollision(val speechActivationTrigger: SpeechActivationTrigger): MoveCollision(){
    override var canMoveAfterCollision = true

    override fun collisionHappened(collidedObject: GameObject) {
        if(collidedObject is Player){
            val currentObjects = AreaManager.getActiveArea()!!.gameObjects
            currentObjects.remove(speechActivationTrigger)
            AnimationManager.animationManager.add(Conversation(speechActivationTrigger.customFields.dialogueName))
        }
    }

}