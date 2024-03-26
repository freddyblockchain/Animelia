package com.mygdx.game.GameObjects.Sensors

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Animation.Conversation
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObject.GameObject
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Managers.AreaManager
import kotlinx.serialization.Serializable

class SpeechActivationSensor(val gameObjectData: SpeechData)
    : GameObject(gameObjectData, Vector2( gameObjectData.width.toFloat(),gameObjectData.height.toFloat())){
    override val texture = DefaultTextureHandler.getTexture("inputbox.png")
    override val layer = Layer.PERSON
    override val collision = SpeechActivationCollision(this)
}

@Serializable
data class SpeechData(
    override val iid: String,
    override val x: Int,
    override var y: Int,
    override val width: Int,
    override val height: Int,
    val customFields: SpeechActivationCustomFields,
): GameObjectData

@Serializable
data class SpeechActivationCustomFields(val dialogueName: String){

}
class SpeechActivationCollision(val speechActivationSensor: SpeechActivationSensor): MoveCollision(){
    override var canMoveAfterCollision = true

    override fun collisionHappened(collidedObject: GameObject) {
        if(collidedObject is Player){
            val currentObjects = AreaManager.getActiveArea()!!.gameObjects
            currentObjects.remove(speechActivationSensor)
            AnimationManager.animationManager.add(Conversation(speechActivationSensor.gameObjectData.customFields.dialogueName))
        }
    }

}