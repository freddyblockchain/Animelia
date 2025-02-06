package com.mygdx.game.Collition

import com.badlogic.gdx.math.Polygon
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Managers.CollisionManager
import com.mygdx.game.Managers.CollisionManager.Companion.isPolygonsColliding

interface Collision {
    fun collisionHappened(collidedObject: GameObject)
    val collitionType: CollisionType

    fun collisionCheck(polygonToCheck: Polygon, polygon2: Polygon,gameObject: GameObject): Boolean{
        return isPolygonsColliding(polygonToCheck, polygon2)
    }
}

enum class CollisionType{MOVE, INPUT}