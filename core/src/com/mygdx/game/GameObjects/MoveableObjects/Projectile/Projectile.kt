package com.mygdx.game.GameObjects.MoveableObjects.Projectile

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collisions.PlayerMoveBackCollision
import com.mygdx.game.Collition.AllOtherObjectsCollisionMask
import com.mygdx.game.Collition.CollisionMask
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.GameObject.MoveableObject
import com.mygdx.game.GameObjects.GameObject.ReflectingState
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.player
import com.mygdx.game.unaryMinus

abstract class Projectile(gameObjectData: GameObjectData, size: Vector2,open var unitVectorDirection: Vector2, var shooter: GameObject) : MoveableObject(gameObjectData, size){

    override val collision = ProjectileCollision(this)
    open val projectileLifespan = 90
    var currentFrame = 0
    override val collisionMask = AllOtherObjectsCollisionMask(shooter)
    override fun frameTask() {
        super.frameTask()
        this.move(unitVectorDirection)
        currentFrame += 1

        if(currentFrame >= projectileLifespan){
            this.remove()
        }
    }
}

open class ProjectileCollision(val projectile: Projectile): MoveCollision() {

    override var canMoveAfterCollision = true
    override fun collisionHappened(collidedObject: GameObject) {
        if(collidedObject is FightableObject){
            if(collidedObject.reflectState == ReflectingState.REFLECTING){
                projectile.shooter = collidedObject
                projectile.currentFrame = 0
                projectile.unitVectorDirection = -projectile.unitVectorDirection
                projectile.collisionMask.objectToExclude = collidedObject
            }else{
                projectile.remove()
                collidedObject.currentHealth -= 10
            }
        }
    }
}