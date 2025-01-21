package com.mygdx.game.Managers

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Intersector.intersectPolygonEdges
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.FloatArray
import com.mygdx.game.CannotMoveStrategy.CannotMoveStrategy
import com.mygdx.game.CannotMoveStrategy.MoveRegardless
import com.mygdx.game.Collisions.AreaEntranceCollition
import com.mygdx.game.Collisions.DefaultAreaEntranceCollition
import com.mygdx.game.Collition.*
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.GameObject.MoveableObject
import com.mygdx.game.GameObjects.Ground
import com.mygdx.game.Managers.CollisionManager.Companion.GetCollidingObjects
import com.mygdx.game.Managers.CollisionManager.Companion.isPolygonsColliding
import com.mygdx.game.anyPointInPolygon
import com.mygdx.game.player

class CollisionManager {
    companion object {

        fun handleMoveCollisions(gameObject: GameObject, polygonToCheck: Polygon, objectsToCheck: List<GameObject>): Boolean{
            val collidingObjects = GetCollidingObjects(gameObject, polygonToCheck, objectsToCheck - gameObject)
            val collitions: List<MoveCollision> = collidingObjects.map { x -> x.collision as MoveCollision }
            collidingObjects.forEach {
                it.collision.collisionHappened(gameObject)
                gameObject.collision.collisionHappened(it)
            }
            // Handle moving object away from previous colliding object.
            checkObjectMovedOutside(gameObject,collidingObjects)
            gameObject.collidingObjects = collidingObjects

            return collitions.all { x -> x.canMoveAfterCollision }
        }

        // added additional collision check. Beware
        fun GetCollidingObjects(gameObjectToCheck: GameObject, polygonToCheck: Polygon, gameObjects: List<GameObject>): List<GameObject> {
            val collidingObjects = gameObjects.filter {gameObjectToCheck.collisionMask.canCollideWith(it) && it.collisionMask.canCollideWith(gameObjectToCheck) && gameObjectToCheck.collision.collisionCheck(polygonToCheck, it.polygon) && it.collision.collisionCheck(polygonToCheck, it.polygon)}
            return collidingObjects
        }

        fun isPolygonsColliding(polygon1: Polygon, polygon2: Polygon): Boolean {
            return intersectPolygonEdges(FloatArray(polygon1.transformedVertices), FloatArray(polygon2.transformedVertices))
                    || polygon1.anyPointInPolygon(polygon2)
        }

        fun isMiddleInPolygon(polygon1: Polygon, polygon2: Polygon): Boolean{
            val middle = getBoundingBoxCenter(polygon1)
            return polygon2.contains(middle)
        }


        //Chat gpt weird ass polygon center
        fun getBoundingBoxCenter(polygon: Polygon): Vector2 {
            val vertices = polygon.transformedVertices
            var minX = vertices[0]
            var minY = vertices[1]
            var maxX = vertices[0]
            var maxY = vertices[1]
            var i = 2
            while (i < vertices.size) {
                val x = vertices[i]
                val y = vertices[i + 1]
                if (x < minX) minX = x
                if (x > maxX) maxX = x
                if (y < minY) minY = y
                if (y > maxY) maxY = y
                i += 2
            }
            val centerX = (minX + maxX) / 2
            val centerY = (minY + maxY) / 2
            return Vector2(centerX, centerY)
        }

        fun checkObjectMovedOutside(gameObject: GameObject, collidingObjects: List<GameObject>){
            val oldCollitions = gameObject.collidingObjects.minus(collidingObjects.toSet())
            if(oldCollitions.isNotEmpty()){
                oldCollitions.forEach {
                    handleObjectMovedOutside(it.collision, gameObject)
                    handleObjectMovedOutside(gameObject.collision, it)
                }

            }
        }

        fun handleObjectMovedOutside(collition: Collision, objectLeaved: GameObject){
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

        fun handleKeyCollitions(objectsToCheck: List<GameObject>) {
            val collidingObjects = GetCollidingObjects(player, player.polygon,objectsToCheck)
            collidingObjects.forEach { x -> x.collision.collisionHappened(player); }
        }

        fun handleKeyPressable(objectsToCheck: List<GameObject>) {
            val inputObjects = objectsToCheck.filter { it.collision is InputCollision }
            val collidingObjects = GetCollidingObjects(player, player.polygon, inputObjects)
            collidingObjects.forEach { (it.collision as InputCollision).renderKeycodeToPress() }
        }
    }
}

class RaycastObject(size: Vector2, val objectBelongingTo: GameObject, val objectsToHit: List<GameObject>, rayCastListener: RaycastListener):
    MoveableObject(GameObjectData(width = size.x.toInt(), height = size.y.toInt())) {
    override val layer = Layer.ONGROUND
    override var direction: Direction
        get() = TODO("Not yet implemented")
        set(value) {}
    override var canChangeDirection: Boolean
        get() = TODO("Not yet implemented")
        set(value) {}

    override val collisionMask =  OnlyTheseSpecificObjectsCollisionMask(objectsToHit)
    override var speed = 1f
    override val cannotMoveStrategy = MoveRegardless()

    override val collision = RayCastCollision(rayCastListener)

    init {
        this.polygon.setOrigin(16f,16f)
    }

    override fun frameTask() {
        updateRayCast()
    }

    fun updateRayCast(){
        this.setPosition(objectBelongingTo.currentPosition())
        this.move(Vector2(0f,0f))
        this.polygon.rotation = objectBelongingTo.polygon.rotation - 90
    }

    override fun render(batch: SpriteBatch) {
    }

}

class RayCastCollision(val rayCastListener: RaycastListener): DefaultAreaEntranceCollition(){
    override var canMoveAfterCollision = true

    override fun collisionCheck(polygon1: Polygon, polygon2: Polygon): Boolean {
        return isPolygonsColliding(polygon1, polygon2)
    }

    override fun movedOutsideAction(objectLeaved: GameObject) {
        super.movedOutsideAction(objectLeaved)
        rayCastListener.objectLeftRay(objectLeaved)
    }

    override fun movedInsideAction(objectEntered: GameObject) {
        super.movedInsideAction(objectEntered)
        rayCastListener.objectEnteredRay(objectEntered)
    }

}

interface RaycastListener{
    fun objectEnteredRay(objectEntered: GameObject)
    fun objectLeftRay(objectLeft: GameObject)
}