package com.mygdx.game.GameObjects.MoveableObjects

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.CannotMoveStrategy.NoAction
import com.mygdx.game.Collisions.CanMoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObject.MoveableObject

class Butler(gameObjectData: ButlerData)
    : MoveableObject(gameObjectData, Vector2(32f,32f)){
    override val texture = DefaultTextureHandler.getTexture("Butler.png")
    override var speed: Float = 2f
    override val cannotMoveStrategy = NoAction()
    override val layer = Layer.PERSON
    override var direction = Direction.RIGHT
    override var canChangeDirection = true
    override val collision = CanMoveCollision()

    var active = false
}