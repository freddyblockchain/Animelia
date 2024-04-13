package com.mygdx.game.GameObjects.Hazards.Generator

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Collisions.CannotMoveCollision
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Enums.getDirectionUnitVector
import com.mygdx.game.GameObjects.GameObject.DefaultRotationalObject
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.GameObject.RotationalObject
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.Projectile
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.SaveHandling.SaveStateEntity
import com.mygdx.game.Saving.DefaultSaveStateHandler
import com.mygdx.game.Trimer.DelayTimer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

open class Generator(
    gameObjectData: GameObjectData,
    timeUntilFire: Float = 0f,
    shootCoolDown: Float = 3f,
    val projectileFactory: () -> Projectile
) :
    GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(), gameObjectData.height.toFloat())),
    RotationalObject by DefaultRotationalObject(),
    SaveStateEntity by DefaultSaveStateHandler() {

    val customFields = Json.decodeFromJsonElement<GeneratorCustomFields>(gameObjectData.customFields)

    val unitVectorDirection = getDirectionUnitVector(customFields.Direction)

    override val texture = DefaultTextureHandler.getTexture("BoulderGenerator.png")
    override val layer = Layer.ONGROUND
    override val collision = CannotMoveCollision()
    val delayTimer = DelayTimer(timeUntilFire, shootCoolDown)
    var shouldFire = true


    init {
        polygon.setOrigin(sprite.x + sprite.originX, sprite.y + sprite.originY)
        setRotation(unitVectorDirection, this, 0f)
    }

    fun generateProjectile(): Projectile{
        val projectile = projectileFactory()
        val Position = Vector2(this.sprite.x,this.sprite.y) + getDistanceFromGenerator(this.unitVectorDirection, projectile.size)
        projectile.setPosition(Position)
        projectile.unitVectorDirection = this.unitVectorDirection
        return projectile
    }

    fun getDistanceFromGenerator(unitVectorDirection: Vector2, projectileSize: Vector2): Vector2 {
        return Vector2(unitVectorDirection.x * projectileSize.x, unitVectorDirection.y * projectileSize.y)
    }

    override fun frameTask() {
        super.frameTask()
        val playerInRange = InsideCircle(this, 150f, player)
        if (playerInRange && delayTimer.tryUseCooldown()) {
                val projectile = generateProjectile()
                AreaManager.getActiveArea()!!.gameObjects.add(projectile)
            }
        }
    }

@Serializable
data class GeneratorCustomFields(val Direction: String){

}
