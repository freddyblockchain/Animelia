package com.mygdx.game.GameObjects.MoveableObjects.Projectile

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.MoveableObject

abstract class Projectile(gameObjectData: GameObjectData, size: Vector2, var unitVectorDirection: Vector2) : MoveableObject(gameObjectData, size){
    override fun frameTask() {
        super.frameTask()
        this.move(unitVectorDirection)
    }
}