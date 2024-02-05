package com.mygdx.game.GameObjects

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.BaseClasses.GameObject
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer

class Ground(initPosition: Vector2, size: Vector2, textureName: String) : GameObject(initPosition, size) {
    override val texture = DefaultTextureHandler.getTexture(textureName)
    override val layer = Layer.GROUND
}