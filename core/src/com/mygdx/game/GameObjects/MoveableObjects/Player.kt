package com.mygdx.game.GameObjects.MoveableEntities.Characters

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.CannotMoveStrategy.NoAction
import com.mygdx.game.Collisions.CanMoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObject.MoveableObject
import com.mygdx.game.GameObjectData
import kotlinx.serialization.Serializable


class Player(gameObjectData: GameObjectData, size: Vector2)
    : MoveableObject(gameObjectData, size){
    override val texture = DefaultTextureHandler.getTexture("player.png")
    override var speed: Float = 2f
    override val cannotMoveStrategy = NoAction()
    override val layer = Layer.PERSON
    override var direction = Direction.RIGHT
    override var canChangeDirection = true
    override val collision = CanMoveCollision()
}

@Serializable
data class PlayerData(
    override val iid: String,
    override val x: Int,
    override var y: Int
): GameObjectData