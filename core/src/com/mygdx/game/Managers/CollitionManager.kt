package com.mygdx.game.Managers

import com.badlogic.gdx.math.Intersector.intersectPolygonEdges
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.FloatArray
import com.mygdx.game.Collisions.AreaEntranceCollition
import com.mygdx.game.Collition.Collision
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.Ground
import com.mygdx.game.anyPointInPolygon

class CollitionManager {
    companion object {

        fun handleMoveCollisions(gameObject: GameObject, polygonToCheck: Polygon, objectsToCheck: List<GameObject>): Boolean{
            val collidingObjects = GetCollidingObjects(gameObject, polygonToCheck, objectsToCheck - gameObject)
            val collitions: List<MoveCollision> = collidingObjects.map { x -> x.collision as MoveCollision }
            collidingObjects.forEach {
                it.collision.collisionHappened(gameObject)
                gameObject.collision.collisionHappened(it)
            }
            // Handle moving object away from previous colliding object.
            handleAreaExitCollisions(gameObject,collidingObjects)
            gameObject.collidingObjects = collidingObjects

            return collitions.all { x -> x.canMoveAfterCollision }
        }

        fun GetCollidingObjects(gameObjectToCheck: GameObject, polygonToCheck: Polygon, gameObjects: List<GameObject>): List<GameObject> {
            val collidingObjects = gameObjects.filter {gameObjectToCheck.collitionMask.canCollideWith(it) && it.collitionMask.canCollideWith(gameObjectToCheck) && isPolygonsColliding(polygonToCheck, it.polygon) }
            return collidingObjects
        }

        fun isPolygonsColliding(polygon1: Polygon, polygon2: Polygon): Boolean {
            return intersectPolygonEdges(FloatArray(polygon1.transformedVertices), FloatArray(polygon2.transformedVertices))
                    || polygon1.anyPointInPolygon(polygon2)
        }

        fun handleAreaExitCollisions(gameObject: GameObject, collidingObjects: List<GameObject>){
            val oldCollitions = gameObject.collidingObjects.minus(collidingObjects.toSet())
            if(oldCollitions.isNotEmpty()){
                oldCollitions.forEach {
                    val collition = it.collision
                    handleAreaExitCheckAndAction(it.collision, gameObject)
                    handleAreaExitCheckAndAction(gameObject.collision, it)
                }

            }
        }

        fun handleAreaExitCheckAndAction(collition: Collision, objectLeaved: GameObject){
            if(collition is AreaEntranceCollition){
                if(collition.insideCollition.getOrDefault(objectLeaved, true)){
                    collition.movedOutside(objectLeaved)
                }
            }
        }

        fun entityWithinLocations(polygonToCheck: Polygon): Boolean {
            var inLocation1 = false
            for (point in getPolygonPoints(polygonToCheck)) {
                inLocation1 = false
                //Ground is the area, that we can actually walk on.
                val grounds = AreaManager.getActiveArea()!!.gameObjects.filter { it is Ground }
                for (rectangle in grounds.map { x -> x.sprite.boundingRectangle }) {
                    if (rectangle.contains(point)) {
                        inLocation1 = true
                        break
                    }
                }
                if (!inLocation1) {
                    break
                }
            }
            return inLocation1
        }
        fun getPolygonPoints(polygon: Polygon): List<Vector2> {
            val floatArray = polygon.transformedVertices
            val xValues = floatArray.filterIndexed { index, _ -> index.toFloat() % 2f == 0f }
            val yValues = floatArray.filterIndexed { index, _ -> index % 2f == 1f }
            val listOfVectors = xValues.zip(yValues).map { (xvalue, yvalue) -> Vector2(xvalue, yvalue) }
            return listOfVectors
        }


    }
}