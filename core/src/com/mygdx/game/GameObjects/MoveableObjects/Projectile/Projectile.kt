package com.mygdx.game.GameObjects.MoveableObjects.Projectile

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collisions.PlayerMoveBackCollision
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.GameObject.MoveableObject
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.player

abstract class Projectile(gameObjectData: GameObjectData, size: Vector2,open var unitVectorDirection: Vector2) : MoveableObject(gameObjectData, size){

    override val collision = ProjectileCollision(this)
    override fun frameTask() {
        super.frameTask()
        this.move(unitVectorDirection)
    }
}

class ProjectileCollision(val projectile: Projectile): MoveCollision() {

    override var canMoveAfterCollision = true
    override fun collisionHappened(collidedObject: GameObject) {
        if(collidedObject is Player){
            AreaManager.getActiveArea()!!.gameObjects.remove(projectile)
            PlayerMoveBackCollision().collisionHappened(player)
        }
    }
}