package com.mygdx.game.Collition

import com.mygdx.game.GameObjects.GameObject.GameObject

interface Collision {
    fun collisionHappened(collidedObject: GameObject)
    val collitionType: CollisionType
}

enum class CollisionType{MOVE, INPUT}