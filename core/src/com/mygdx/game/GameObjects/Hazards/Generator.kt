package com.mygdx.game.GameObjects.Hazards

import com.mygdx.game.Collisions.CannotMoveCollision
import com.mygdx.game.GameObjects.GameObject.DefaultRotationalObject
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.GameObject.RotationalObject
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.Projectile
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.SaveHandling.SaveStateEntity
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Saving.DefaultSaveStateHandler
import com.mygdx.game.Trimer.DelayTimer

open class Generator(
    gameObjectData: GameObjectData,
    val unitVectorDirection: Vector2,
    timeUntilFire: Float = 0f,
    shootCoolDown: Float = 3f,
    val projectileFactory: () -> Projectile
) :
    GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(), gameObjectData.height.toFloat())),
    RotationalObject by DefaultRotationalObject(),
    SaveStateEntity by DefaultSaveStateHandler() {

    override val texture = DefaultTextureHandler.getTexture("BoulderGenerator.png")
    override val layer = Layer.ONGROUND
    override val collision = CannotMoveCollision()
    val delayTimer = DelayTimer(timeUntilFire, shootCoolDown)
    var shouldFire = true

    fun generateProjectile(val projectileFactory: () -> Projectile): Projectile{
        val projectile = projectileFactory()
        val Position = Vector2(this.sprite.x,this.sprite.y) + getDistanceFromGenerator(unitVectorDirection, projectile.size)
        projectile.setPosition(Position)
        projectile.unitVectorDirection = this.unitVectorDirection
        return projectile
    }

    init {
        polygon.setOrigin(sprite.x + sprite.originX, sprite.y + sprite.originY)
        setRotation(unitVectorDirection, this, 0f)
    }

    fun getDistanceFromGenerator(unitVectorDirection: Vector2, projectileSize: Vector2): Vector2 {
        return Vector2(unitVectorDirection.x * projectileSize, unitVectorDirection.y * projectileSize.y)
    }

    override fun frameTask() {
        super.frameTask()
        if (delayTimer.tryUseCooldown() && shouldFire) {
                val projectile = generateProjectile(projectileFactory)
                AreaManager.addObject(projectile)
            }
        }
    }

}
