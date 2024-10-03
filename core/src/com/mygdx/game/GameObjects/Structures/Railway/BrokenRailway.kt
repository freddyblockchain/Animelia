package com.mygdx.game.GameObjects.Structures.Railway

import RailwayFixedSignal
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.Collisions.CanMoveCollision
import com.mygdx.game.Collition.Collision
import com.mygdx.game.Collition.InputCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Managers.SignalManager
import kotlin.concurrent.fixedRateTimer

class BrokenRailway(val railway: Railway, gameObjectData: GameObjectData) : GameObject(gameObjectData) {
    override val layer = Layer.ONGROUND

    override val texture = DefaultTextureHandler.getTexture("BrokenRails.png")

    val fixedTexture = DefaultTextureHandler.getTexture("HealthyRails.png")

    override var collision: Collision = FixRailsCollision(this)

    fun fix(){
        sprite.texture = fixedTexture
        collision = CanMoveCollision()
    }
}

class FixRailsCollision(val brokenRailway: BrokenRailway): InputCollision(){
    override val insideText = "FIX"

    override fun collisionHappened(collidedObject: GameObject) {
        SignalManager.emitSignal(RailwayFixedSignal(brokenRailway.railway.gameObjectIid))
    }

}