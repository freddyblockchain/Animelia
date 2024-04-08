package com.mygdx.game.GameObjects.Hazards

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collisions.CannotMoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject

class InvisibleWall(gameObjectData: GameObjectData): GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(), gameObjectData.height.toFloat())) {
    override val texture = DefaultTextureHandler.getTexture("water.png")
    override val layer = Layer.BEFOREGROUND

    override fun render(batch: SpriteBatch) {

    }

    override val collision = CannotMoveCollision()
}