package com.mygdx.game.GameObjects.Hazards

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.Collition.OnlyPlayerCollitionMask
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObject.GameObject
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.player
import kotlinx.serialization.Serializable

class Spike(gameObjectData: SpikeData)
    : GameObject(gameObjectData, Vector2(32f,32f)){
    override val texture = DefaultTextureHandler.getTexture("Spike.png")
    override val layer = Layer.PERSON
    override val collitionMask = OnlyPlayerCollitionMask
    override val collision = SpikeCollision(this)

    init {
        polygon.scale(-0.5f)
    }
}
class SpikeCollision(val spike: Spike): MoveCollision() {
    override var canMoveAfterCollision = true

    override fun collisionHappened(collidedObject: GameObject) {
        if(collidedObject is Player){
            player.setPosition(player.startingPosition)
        }
    }

}

@Serializable
data class SpikeData(
    override val iid: String,
    override val x: Int,
    override var y: Int,
    override val width: Int,
    override val height: Int,
): GameObjectData