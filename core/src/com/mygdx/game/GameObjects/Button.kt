package com.mygdx.game.GameObjects

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collisions.CannotMoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.FloorButtonData
import com.mygdx.game.GameObject.GameObject

class Button(val floorButtonData: FloorButtonData): GameObject(Vector2(floorButtonData.x.toFloat(), floorButtonData.y.toFloat()), Vector2(32f,32f)) {
    override val texture = DefaultTextureHandler.getTexture("GateButton.png")
    override val layer = Layer.ONGROUND
    override val collision = CannotMoveCollision()
}