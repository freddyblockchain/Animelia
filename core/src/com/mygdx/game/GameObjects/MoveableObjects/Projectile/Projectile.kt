package com.mygdx.game.GameObjects.MoveableObjects.Projectile

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collisions.PlayerMoveBackCollision
import com.mygdx.game.Collition.AllOtherObjectsCollisionMask
import com.mygdx.game.Collition.CollisionMask
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.*
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.player
import com.mygdx.game.unaryMinus

abstract class Projectile(gameObjectData: GameObjectData, size: Vector2,open var unitVectorDirection: Vector2, var shooter: GameObject) : MoveableObject(gameObjectData, size){

    override val collision = ProjectileCollision(this)
    open val projectileLifespan = 90
    var currentFrame = 0
    override val collisionMask = AllOtherObjectsCollisionMask(shooter)
    init {
        currentUnitVector = unitVectorDirection
    }
    override fun frameTask() {
        super.frameTask()
        this.move(currentUnitVector)
        currentFrame += 1

        if(currentFrame >= projectileLifespan){
            this.remove()
        }
    }

    open fun handleReflection(collidedObject: GameObject){
        this.shooter = collidedObject
        this.currentFrame = 0
        this.currentUnitVector = -this.currentUnitVector
        this.collisionMask.objectToExclude = collidedObject
        this.setRotation(this.currentUnitVector, this, 0f)
    }
}

open class ProjectileCollision(val projectile: Projectile): MoveCollision() {

    override var canMoveAfterCollision = true

    open fun handleProjectileHitting(fightableObject: FightableObject){
        projectile.remove()
        fightableObject.currentHealth -= 10
    }
    override fun collisionHappened(collidedObject: GameObject) {
        if(collidedObject is FightableObject){
            if(collidedObject.reflectState == ReflectingState.REFLECTING){
                projectile.handleReflection(collidedObject)
            }
            else if (collidedObject.state == State.SHIELDED){
                projectile.remove()
            }
            else{
                handleProjectileHitting(collidedObject)
            }
        }
    }
}