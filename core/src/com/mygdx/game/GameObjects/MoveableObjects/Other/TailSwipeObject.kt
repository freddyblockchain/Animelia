package com.mygdx.game.GameObjects.MoveableObjects.Other

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.CannotMoveStrategy.MoveRegardless
import com.mygdx.game.Collition.Collision
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.GameObject.MoveableObject
import com.mygdx.game.GameObjects.Hazards.Rock
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.plus
import com.mygdx.game.setSize
import java.awt.geom.Area

class TailSwipeObject(gameObjectData: GameObjectData, size: Vector2, val objectAttached: FightableObject, val rotationAmount: Float) : MoveableObject(gameObjectData, size) {

    override var speed = 0f
    override val cannotMoveStrategy = MoveRegardless()
    override val texture = DefaultTextureHandler.getTexture("FireGate.png")
    override val layer = Layer.ONGROUND
    override var direction = Direction.DOWN
    override var canChangeDirection = true
    override val collision = TailSwipeCollision(objectAttached)

    init {
        setSize(Vector2(objectAttached.width / 5f, 6f))
        this.polygon.setOrigin(0f, objectAttached.sprite.originY)
        this.setPosition(objectAttached.currentPosition() + Vector2(objectAttached.width / 2,0f))
    }

    override fun render(batch: SpriteBatch) {
    }

    override fun frameTask() {
        if(this.objectAttached.direction == Direction.DOWN || this.objectAttached.direction == Direction.UP){
            this.setPosition(Vector2(this.currentPosition().x, objectAttached.currentPosition().y))
        }else {
            this.setPosition(Vector2(objectAttached.currentPosition().x + objectAttached.width / 2, this.currentPosition().y))
        }
        this.rotateByAmount(rotationAmount, this)
        //cheating abit to trigger collisions
        this.move(Vector2(0f, 0f), speed)
    }
}

class TailSwipeCollision(val objectAttached: FightableObject): MoveCollision() {
    override var canMoveAfterCollision = true

    override fun collisionHappened(collidedObject: GameObject) {
        if(collidedObject is Rock && collidedObject.checkDestroyed(objectAttached.stats)){
            AreaManager.getActiveArea()!!.gameObjects.remove(collidedObject)
        }
    }

}