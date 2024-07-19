package com.mygdx.game.GameObjects.GameObject

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.GameObjectData
import com.mygdx.game.Managers.Stats

abstract class FightableObject(gameObjectData: GameObjectData, size: Vector2) : MoveableObject(gameObjectData, size) {
    var usingAbility = false
    var isMoving = false
    open val stats = Stats()

    override fun move(newUnitVector: Vector2, speed: Float): Boolean {
        if (!usingAbility) {
            isMoving = super.move(newUnitVector, speed)
            return isMoving
        }
        return false
    }

    fun forceMove(speed: Float) {
        super.move(this.currentUnitVector, speed)
    }
}