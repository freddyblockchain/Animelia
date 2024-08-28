package com.mygdx.game.GameObjects.GameObject

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.GameObjectData
import com.mygdx.game.Managers.Stats
import com.mygdx.game.UI.HealthStrategy

abstract class FightableObject(gameObjectData: GameObjectData, size: Vector2) : MoveableObject(gameObjectData, size) {
    var cannotMoveCount = 0
    var isMoving = false
    abstract val maxHealth: Float
    var currentHealth = 0f
    open val stats = Stats()
    abstract val healthStrategy: HealthStrategy

    override fun move(newUnitVector: Vector2, speed: Float): Boolean {
        if (cannotMoveCount == 0) {
            isMoving = super.move(newUnitVector, speed)
            return isMoving
        }
        return false
    }

    fun forceMove(speed: Float) {
        super.move(this.currentUnitVector, speed)
    }
}