package com.mygdx.game.Collition

import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.Projectile

interface CollisionMask {
    val canCollideWith: (GameObject) -> Boolean
}

class DefaultCollisionMask(override val canCollideWith: (GameObject) -> Boolean = { _: GameObject -> true }):
    CollisionMask {
}

object OnlyPlayerCollitionMask: CollisionMask{
    override val canCollideWith: (GameObject) -> Boolean = { other: GameObject -> other is Player }
}

object OnlyProjectileCollisionMask: CollisionMask{
    override val canCollideWith: (GameObject) -> Boolean = { other: GameObject -> other is Projectile}
}

class AllOtherObjectsCollisionMask(val objectToExclude: GameObject): CollisionMask{
    override val canCollideWith: (GameObject) -> Boolean = { other: GameObject ->  other != objectToExclude }
}