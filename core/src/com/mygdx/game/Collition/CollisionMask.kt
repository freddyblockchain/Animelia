package com.mygdx.game.Collition

import com.mygdx.game.GameObject.GameObject

interface CollisionMask {
    val canCollideWith: (GameObject) -> Boolean
}

class DefaultCollisionMask(override val canCollideWith: (GameObject) -> Boolean = { _: GameObject -> true }):
    CollisionMask {

}
