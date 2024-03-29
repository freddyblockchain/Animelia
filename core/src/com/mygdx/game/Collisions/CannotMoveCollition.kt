package com.mygdx.game.Collisions

import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.GameObjects.GameObject.GameObject

open class CannotMoveCollision: MoveCollision() {
    override var canMoveAfterCollision = false

    override fun collisionHappened(collidedObject: GameObject) {

    }
}