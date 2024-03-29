package com.mygdx.game.Collition

import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player

interface CollisionMask {
    val canCollideWith: (GameObject) -> Boolean
}

class DefaultCollisionMask(override val canCollideWith: (GameObject) -> Boolean = { _: GameObject -> true }):
    CollisionMask {
}

object OnlyPlayerCollitionMask: CollisionMask{
    override val canCollideWith: (GameObject) -> Boolean = { other: GameObject -> other is Player }
}