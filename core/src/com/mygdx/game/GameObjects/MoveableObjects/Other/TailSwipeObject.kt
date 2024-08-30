package com.mygdx.game.GameObjects.MoveableObjects.Other

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.CannotMoveStrategy.NoAction
import com.mygdx.game.Collition.AllOtherObjectsCollisionMask
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.GameObject.MoveableObject
import com.mygdx.game.GameObjects.Hazards.Rock

class TailSwipeObject(gameObjectData: GameObjectData, size: Vector2, val objectAttached: FightableObject, val rotationAmount: Float) : MoveableObject(gameObjectData, size) {

    override var speed = 1f
    override val cannotMoveStrategy = NoAction()
    override val texture = DefaultTextureHandler.getTexture("FireGate.png")
    override val layer = Layer.ONGROUND
    override var direction = Direction.DOWN
    override var canChangeDirection = true
    override val collision = TailSwipeCollision(this,objectAttached)
    override val collitionMask = AllOtherObjectsCollisionMask(objectAttached)
    var objectAttachedPos = Vector2(0f,0f)

    val entitesHit = mutableMapOf<GameObject, Boolean>()

    init {
        setSize(Vector2(objectAttached.width / 5f, 6f))
        currentUnitVector = objectAttached.currentUnitVector
        val amountToIncrease = Vector2(- (objectAttached.width / 1.5f * currentUnitVector.x), -(objectAttached.height / 1.5f * currentUnitVector.y))
        this.polygon.setOrigin(- amountToIncrease.x, - amountToIncrease.y)
        this.setPosition(objectAttached.currentMiddle + amountToIncrease - Vector2((objectAttached.width / 5f) / 2, 6f / 2))
    }

    override fun render(batch: SpriteBatch) {
    }

    override fun frameTask() {
        /*if(this.objectAttached.direction == Direction.DOWN || this.objectAttached.direction == Direction.UP){
            this.setPosition(Vector2(this.currentPosition().x, objectAttached.currentPosition().y))
        }else {
            this.setPosition(Vector2(objectAttached.currentPosition().x + objectAttached.width / 2, this.currentPosition().y))
        }*/
        currentUnitVector = objectAttached.currentUnitVector
        if(objectAttachedPos != objectAttached.currentPosition()){
            //Corresponding object has moved
            this.move(Vector2(currentUnitVector), speed)
        } else{
            //Dont move further
            this.move(Vector2(currentUnitVector), 0f)
        }
        //this.setPosition(this.currentPosition() + currentUnitVector)
        this.rotateByAmount(rotationAmount)
        //cheating abit to trigger collisions
        objectAttachedPos = objectAttached.currentPosition()
    }
}

class TailSwipeCollision(val tailSwipeObject: TailSwipeObject, val objectAttached: FightableObject): MoveCollision() {
    override var canMoveAfterCollision = true
    var hasHit = false

    override fun collisionHappened(collidedObject: GameObject) {
        if(collidedObject is Rock && !gameObjectAlreadyHit(collidedObject) ){
            collidedObject.handleRockDestroyed(objectAttached.stats)
        }
        if(collidedObject is FightableObject && !gameObjectAlreadyHit(collidedObject)){
            collidedObject.currentHealth -= 10f
        }
        tailSwipeObject.entitesHit[collidedObject] = true
    }

    fun gameObjectAlreadyHit(gameObject: GameObject): Boolean{
        return tailSwipeObject.entitesHit.getOrDefault(gameObject, false)
    }

}