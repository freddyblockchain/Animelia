package com.mygdx.game.GameObjects.MoveableObjects.Projectile

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject

fun generateProjectileFromConstructor(projectileFactory: (gameObjectData: GameObjectData, size: Vector2, unitVectorDirection: Vector2) -> Projectile,
                              gameObjectData: GameObjectData, size: Vector2, unitVectorDirection: Vector2): () -> Projectile{
    return {
        projectileFactory(gameObjectData, size, unitVectorDirection)
    }
}

fun generateMissile(gameObjectData: GameObjectData, speed: Float, unitVectorDirection: Vector2,target: GameObject): () -> Projectile{
    return {
        Missile(gameObjectData, unitVectorDirection, target, speed)
    }
}