package com.mygdx.game.Interfaces

import com.mygdx.game.BaseClasses.GameObject

interface CollisionMask {
    val canCollideWith: (GameObject) -> Boolean
}

class DefaultCollisionMask(override val canCollideWith: (GameObject) -> Boolean = { _:GameObject -> true }): CollisionMask {

}
