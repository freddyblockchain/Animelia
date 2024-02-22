package com.mygdx.game.GameObjects

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObject.GameObject
import com.mygdx.game.GameObjectData
import kotlinx.serialization.Serializable

class Ground(gameObjectData: GameObjectData, size: Vector2, textureName: String) : GameObject(gameObjectData, size) {
    override val texture = DefaultTextureHandler.getTexture(textureName)
    override val layer = Layer.GROUND
}

@Serializable
data class GroundData(
    override val iid: String,
    override val x: Int,
    override var y: Int,
    override val width: Int,
    override val height: Int,
): GameObjectData