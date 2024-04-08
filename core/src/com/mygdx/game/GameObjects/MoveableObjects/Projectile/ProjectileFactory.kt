package com.mygdx.game.GameObjects.MoveableObjects.Projectile

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.GameObjectData
import com.mygdx.game.player

fun generateProjectileFromConstructor(projectileFactory: (gameObjectData: GameObjectData, size: Vector2, unitVectorDirection: Vector2) -> Projectile,
                              gameObjectData: GameObjectData, size: Vector2, unitVectorDirection: Vector2): () -> Projectile{
    return {
        projectileFactory(gameObjectData, size, unitVectorDirection)
    }
}

fun generateMissile(gameObjectData: GameObjectData, speed: Float): () -> Projectile{
    return {
        Missile(gameObjectData, player, speed, Vector2(0f,0f))
    }
}