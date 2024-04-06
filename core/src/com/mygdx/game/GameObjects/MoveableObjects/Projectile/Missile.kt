package com.mygdx.game.GameObjects.MoveableObjects.Projectile

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.CannotMoveStrategy.NoAction
import com.mygdx.game.Collisions.CanMoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Enums.getDirectionFromUnitVector
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.InsideCircle
import com.mygdx.game.getUnitVectorTowardsPoint


class Missile(
    gameObjectData: GameObjectData,
    unitVectorDirection: Vector2,
    val target: GameObject,
    override var speed: Float = 8f
) : Projectile(gameObjectData, Vector2(gameObjectData.width.toFloat(), gameObjectData.height.toFloat()), unitVectorDirection) {
    override val cannotMoveStrategy = NoAction()
    override val texture = DefaultTextureHandler.getTexture("Rocket.png")
    override val layer = Layer.AIR
    override val collision = CanMoveCollision()
    override var direction = getDirectionFromUnitVector(unitVectorDirection)
    override var canChangeDirection = true
    val missileCheckRange = 500f

    init {
        setRotation(unitVectorDirection, this, 0f)
    }

    override fun frameTask() {
        super.frameTask()

        val playerInRange = InsideCircle(this, missileCheckRange, target)
        var objectToFollow: GameObject? = null

        if(playerInRange){
            objectToFollow = target
            unitVectorDirection = getUnitVectorTowardsPoint(this.currentPosition(), objectToFollow.currentPosition())
            setRotation(unitVectorDirection, this, 0f)
        }

    }
}