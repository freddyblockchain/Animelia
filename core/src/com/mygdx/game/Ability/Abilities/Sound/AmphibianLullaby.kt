package com.mygdx.game.Ability.Abilities.Sound

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Ability.AbilityName
import com.mygdx.game.Ability.ELEMENTAL_TYPE
import com.mygdx.game.Ability.KeyAbility
import com.mygdx.game.CannotMoveStrategy.CannotMoveStrategy
import com.mygdx.game.CannotMoveStrategy.MoveRegardless
import com.mygdx.game.Collition.AllOtherObjectsCollisionMask
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Enums.getDirectionFromUnitVector
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.GameObjects.GameObject.FlyingState
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.Projectile
import com.mygdx.game.getUnitVectorTowardsPoint

class AmphibianLullaby(override val attachedFightableObject: FightableObject) : KeyAbility() {
    override val abilityName = AbilityName.AmphibianLullaby
    override val ELEMENTALTYPES = ELEMENTAL_TYPE.SOUND
    val circle = Circle()

    val startRadius = 100f

    override val activeFrames = 120
    override var currentFrame = 0

    var angleIncrement = 1

    override fun onActivate() {
        circle.x = attachedFightableObject.currentMiddle.x - 8f
        circle.y = attachedFightableObject.currentMiddle.y - 8f
        circle.radius = startRadius

        for(i in 1..6){
            val lullabyProjectile = LullabyProjectile(
                gameObjectData = GameObjectData(x = circle.x.toInt(), y = circle.y.toInt()),
                size = Vector2(16f, 32f),
                unitVectorDirection = Vector2(1f, 0f),
                shooter = attachedFightableObject,
                i * 60f,
                circle
            )
            lullabyProjectile.add()
        }

    }

    override fun onDeactivate() {

    }

    override fun frameAction() {
        /*lullabyProjectile.angle = (lullabyProjectile.angle + angleIncrement) % 360f
        val x = circle.radius * Math.cos(lullabyProjectile.angle.toDouble())
        val y = circle.radius * Math.sin(lullabyProjectile.angle.toDouble())

        val newPoint = Vector2(circle.x + x.toFloat(), circle.y + y.toFloat())
        val unitVector = getUnitVectorTowardsPoint(Vector2(circle.x, circle.y), newPoint)*/
        //lullabyProjectile.move(unitVector)
        //lullabyProjectile.move(Vector2(0f,1f))
    }
}

class LullabyProjectile(
    gameObjectData: GameObjectData, size: Vector2, unitVectorDirection: Vector2,
    shooter: GameObject, override var angle: Float, val circle: Circle
) : Projectile(
    gameObjectData, size,
    unitVectorDirection, shooter
) {
    override var direction = getDirectionFromUnitVector(unitVectorDirection)
    override var canChangeDirection = true
    override var speed = 2f
    override val cannotMoveStrategy = MoveRegardless()
    override val layer = Layer.AIR
    override val texture: Texture = DefaultTextureHandler.getTexture("MusicNode.png")

    val angleIncrement = 3f
    override val projectileLifespan = 120
    var counter = 0

    override fun frameTask() {
        if(counter < 60){
            angle = (angle + angleIncrement) % 360f
        } else if (counter == 60){
            angle += 180
        }
        else{
            angle = (angle - angleIncrement) % 360
            if(angle <= 0){
                angle += 360
            }
        }
        val x =  Math.cos(angle * Math.PI / 180)
        val y =  Math.sin(angle * Math.PI / 180)

        val newPoint = Vector2(circle.x + x.toFloat(), circle.y + y.toFloat())
        unitVectorDirection = getUnitVectorTowardsPoint(Vector2(circle.x, circle.y), newPoint)
        counter += 1
        super.frameTask()

    }
}