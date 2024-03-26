package com.mygdx.game.GameObjects.Hazards

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.Collition.OnlyPlayerCollitionMask
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObject.GameObject
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.player
import com.mygdx.game.renderRepeatedTexture
import kotlinx.serialization.Serializable

class Spike(gameObjectData: SpikeData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())){
    override val texture = DefaultTextureHandler.getTexture("Spike.png")
    override val layer = Layer.PERSON
    override val collitionMask = OnlyPlayerCollitionMask
    override val collision = SpikeCollision(this)

    init {
        this.polygon.scale(-0.2f)
    }

    override fun render(batch: SpriteBatch) {
        renderRepeatedTexture(batch, texture, this.currentPosition(), Vector2(sprite.width, sprite.height))
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