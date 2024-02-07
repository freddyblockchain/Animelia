package com.mygdx.game.Collition

abstract class MoveCollision: Collision {
    override val collitionType = CollisionType.MOVE

    abstract var canMoveAfterCollision: Boolean
}
abstract class InputCollition: Collision {
    override val collitionType = CollisionType.INPUT
}