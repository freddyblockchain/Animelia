package com.mygdx.game.GameObjects.MoveableObjects.Projectile

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.GameObjectData

fun generateProjectileFromConstructor(projectileFactory: (gameObjectData: GameObjectData, size: Vector2, unitVectorDirection: Vector2) -> Projectile,
                              gameObjectData: GameObjectData, size: Vector2, unitVectorDirection: Vector2): () -> Projectile{
    return {
        projectileFactory(gameObjectData, size, unitVectorDirection)
    }
}