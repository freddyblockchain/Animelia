package com.mygdx.game.GameObjects.Hazards

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collisions.PlayerMoveBackCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import kotlinx.serialization.Serializable

class Thorns(gameObjectData: ThornsData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())){
    override val texture = DefaultTextureHandler.getTexture("water.png")
    override val layer = Layer.ONGROUND
    override val collision = PlayerMoveBackCollision()

    override fun render(batch: SpriteBatch) {

    }

    init {
        this.polygon.scale(-0.2f)
    }
}

@Serializable
class ThornsData(
    override val x: Int,
    override var y: Int,
    override val iid: String,
    override val width: Int,
    override val height: Int
) : GameObjectData