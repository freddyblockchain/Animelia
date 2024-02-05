package com.mygdx.game.Collisions

import com.mygdx.game.Interfaces.MoveCollition
import com.mygdx.game.BaseClasses.GameObject

object CanMoveCollision: MoveCollition{
    override fun collitionHappened(collidedObject: GameObject) {

    }
    override var canMoveAfterCollition = true
}