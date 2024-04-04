package com.mygdx.game.Collisions

import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.player

class AllMoveBackCollision: MoveCollision() {
    override var canMoveAfterCollision = true

    override fun collisionHappened(collidedObject: GameObject) {
        collidedObject.setPosition(player.startingPosition)
    }
}

class PlayerMoveBackCollision: MoveCollision() {
    override var canMoveAfterCollision = true

    override fun collisionHappened(collidedObject: GameObject) {
        if(collidedObject is Player){
            collidedObject.setPosition(player.startingPosition)
        }
    }
}