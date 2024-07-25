package com.mygdx.game.GameObjects.MoveableObjects.Projectile

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.CannotMoveStrategy.CannotMoveStrategy
import com.mygdx.game.CannotMoveStrategy.MoveRegardless
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Enums.getDirectionFromUnitVector
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject

class Fireball(gameObjectData: GameObjectData, size: Vector2, unitVectorDirection: Vector2, shooter: GameObject) : Projectile(gameObjectData,size, unitVectorDirection, shooter) {

    override var speed = 3f
    override val cannotMoveStrategy = MoveRegardless()
    override val texture = DefaultTextureHandler.getTexture("fireball.png")
    override val layer = Layer.AIR
    override var direction = getDirectionFromUnitVector(unitVectorDirection)
    override var canChangeDirection = true

    init {
        setRotation(unitVectorDirection,this,0f)
    }
}