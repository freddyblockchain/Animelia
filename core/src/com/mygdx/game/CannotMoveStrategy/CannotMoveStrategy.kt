package com.mygdx.game.CannotMoveStrategy

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.GameObjects.GameObject.MoveableObject
import com.mygdx.game.plus
import com.mygdx.game.times

interface CannotMoveStrategy {
    fun CannotMoveAction(moveableObject: MoveableObject)
}

class MoveRegardless: CannotMoveStrategy {
    override fun CannotMoveAction(moveableObject: MoveableObject) {
        moveableObject.setPosition(Vector2(moveableObject.sprite.x,moveableObject.sprite.y) + moveableObject.currentUnitVector * moveableObject.getCurrentSpeed())
    }
}

class NoAction: CannotMoveStrategy {
    override fun CannotMoveAction(moveableObject: MoveableObject) {

    }
}

class RemoveObject: CannotMoveStrategy {
    override fun CannotMoveAction(moveableObject: MoveableObject) {
        moveableObject.remove()
    }
}