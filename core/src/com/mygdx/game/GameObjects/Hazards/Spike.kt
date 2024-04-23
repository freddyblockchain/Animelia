package com.mygdx.game.GameObjects.Hazards

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collisions.DefaultAreaEntranceCollition
import com.mygdx.game.Collition.OnlyPlayerCollitionMask
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.renderRepeatedTexture

class Spike(gameObjectData: GameObjectData)
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
    }

    override fun movedOutsideAction(objectLeaved: GameObject) {

    }
}