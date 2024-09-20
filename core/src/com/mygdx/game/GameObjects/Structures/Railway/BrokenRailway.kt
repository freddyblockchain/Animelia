package com.mygdx.game.GameObjects.Structures.Railway

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.Collition.InputCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject

class BrokenRailway(gameObjectData: GameObjectData) : GameObject(gameObjectData) {
    override val layer = Layer.ONGROUND

    override val texture = DefaultTextureHandler.getTexture("BrokenRails.png")

    override val collision = FixRailsCollision()
}

class FixRailsCollision(): InputCollision(){
    override val insideText = "FIX"

    override fun collisionHappened(collidedObject: GameObject) {

    }

}