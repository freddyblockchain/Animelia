package com.mygdx.game.GameObjects.MoveableObjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.CannotMoveStrategy.NoAction
import com.mygdx.game.Collisions.CanMoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.MoveableObject
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.butler

class Butler(gameObjectData: GameObjectData)
    : MoveableObject(gameObjectData, Vector2(48f,32f)){
    override val texture = DefaultTextureHandler.getTexture("Butler.png")
    override var speed: Float = 2f
    override val cannotMoveStrategy = NoAction()
    override val layer = Layer.PERSON
    override var direction = Direction.RIGHT
    override var canChangeDirection = true
    override val collision = CanMoveCollision()

    var active = false

    override fun render(batch: SpriteBatch) {
        if(active){
            super.render(batch)
        }
    }
    fun setActive(pos: Vector2){
        val currentObjects = AreaManager.getActiveArea()!!.gameObjects
        if(!currentObjects.contains(butler)){
            currentObjects.add(butler)
        }
        butler.active = true
        butler.setPosition(pos)
    }
}