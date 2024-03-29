package com.mygdx.game.GameObjects.Hazards

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Abilities.ButlerRiding
import com.mygdx.game.Collisions.DefaultAreaEntranceCollition
import com.mygdx.game.Collition.OnlyPlayerCollitionMask
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
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
class SpikeCollision(val spike: Spike): DefaultAreaEntranceCollition() {
    override var canMoveAfterCollision = true

    override fun movedInsideAction(objectEntered: GameObject) {
        val butlerRidingAbility = player.abilities.firstOrNull() { it is ButlerRiding }
        if(butlerRidingAbility != null){
            val butlerRiding = butlerRidingAbility as ButlerRiding
            butlerRiding.onActivate()
        } else{
            player.setPosition(player.startingPosition)
        }
    }

    override fun movedOutsideAction(objectLeaved: GameObject) {
        val butlerRidingAbility = player.abilities.firstOrNull() { it is ButlerRiding }
        if(butlerRidingAbility != null) {
            val butlerRiding = butlerRidingAbility as ButlerRiding
            butlerRiding.onDeactivate()
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