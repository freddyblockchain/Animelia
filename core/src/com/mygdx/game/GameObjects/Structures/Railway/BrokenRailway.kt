package com.mygdx.game.GameObjects.Structures.Railway

import RailwayFixedSignal
import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.Collition.InputCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Managers.SignalManager
import kotlin.concurrent.fixedRateTimer

class BrokenRailway(val railway: Railway , gameObjectData: GameObjectData) : GameObject(gameObjectData) {
    override val layer = Layer.ONGROUND

    override val texture = DefaultTextureHandler.getTexture("BrokenRails.png")

    override val collision = FixRailsCollision(this)
}

class FixRailsCollision(val brokenRailway: BrokenRailway): InputCollision(){
    override val insideText = "FIX"

    override fun collisionHappened(collidedObject: GameObject) {
        SignalManager.emitSignal(RailwayFixedSignal(brokenRailway.railway.gameObjectIid))
    }

}