package com.mygdx.game.GameObjects.MoveableObjects

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObject.GameObject
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.butler
import kotlinx.serialization.Serializable

class ButlerActivationSensor(gameObjectData: ButlerData)
    : GameObject(gameObjectData, Vector2(32f,32f)){
    override val texture = DefaultTextureHandler.getTexture("Butler.png")
    override val layer = Layer.PERSON
    override val collision = ButlerActivationCollision(this)
    init {
        polygon.scale(1.5f)
    }
}

@Serializable
data class ButlerData(
    override val iid: String,
    override val x: Int,
    override var y: Int,
): GameObjectData

class ButlerActivationCollision(val butlerActivationSensor: ButlerActivationSensor): MoveCollision(){
    override var canMoveAfterCollision = true

    override fun collisionHappened(collidedObject: GameObject) {
        if(collidedObject is Player){
            val currentObjects = AreaManager.getActiveArea()!!.gameObjects
            currentObjects.remove(butlerActivationSensor)
            butler.active = true
            butler.setPosition(butlerActivationSensor.currentPosition())
            currentObjects.add(butler)
        }
    }

}