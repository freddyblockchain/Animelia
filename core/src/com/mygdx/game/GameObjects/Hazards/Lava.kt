package com.mygdx.game.GameObjects.Hazards

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collisions.CanMoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.renderRepeatedTexture

class Lava(gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())){
    override val texture = DefaultTextureHandler.getTexture("lava.png")
    override val layer = Layer.ONGROUND
    override val collision = CanMoveCollision()

    override fun render(batch: SpriteBatch) {
        renderRepeatedTexture(batch, texture, this.currentPosition(), Vector2(sprite.width, sprite.height))
    }
}