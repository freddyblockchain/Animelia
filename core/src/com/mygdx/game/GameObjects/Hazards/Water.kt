package com.mygdx.game.GameObjects.Hazards

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collisions.AllMoveBackCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.DefaultToggelable
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.Toggelable
import com.mygdx.game.renderRepeatedTexture

class Water(gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())), Toggelable by DefaultToggelable(){
    override val texture = DefaultTextureHandler.getTexture("water.png")
    override val layer = Layer.ONGROUND
    override val collision = AllMoveBackCollision()

    override fun render(batch: SpriteBatch) {
        renderRepeatedTexture(batch, texture, this.currentPosition(), Vector2(sprite.width, sprite.height))
    }
}