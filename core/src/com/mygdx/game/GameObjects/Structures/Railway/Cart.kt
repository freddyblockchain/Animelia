package com.mygdx.game.GameObjects.Structures.Railway

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject

class Cart(gameObjectData: GameObjectData) : GameObject(gameObjectData) {
    override var layer = Layer.ONGROUND
    override val texture = DefaultTextureHandler.getTexture("Minecart.png")
}