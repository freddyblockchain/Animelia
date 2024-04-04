package com.mygdx.game.GameObjects

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject

class Ground(gameObjectData: GameObjectData, size: Vector2, textureName: String) : GameObject(gameObjectData, size) {
    override val texture = DefaultTextureHandler.getTexture(textureName)
    override val layer = Layer.GROUND
}