package com.mygdx.game.GameObjects.MoveableEntities.Characters

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Abilities.Ability
import com.mygdx.game.CannotMoveStrategy.NoAction
import com.mygdx.game.Collisions.CanMoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Enums.PlayerState
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.MoveableObject
import com.mygdx.game.SaveHandling.SaveStateEntity
import com.mygdx.game.Saving.DefaultSaveStateHandler
import com.mygdx.game.player
import kotlinx.serialization.Serializable
class Player(gameObjectData: GameObjectData, size: Vector2)
    : MoveableObject(gameObjectData, size), SaveStateEntity by DefaultSaveStateHandler() {
    override val texture = DefaultTextureHandler.getTexture("player.png")
    val butlerTexture = DefaultTextureHandler.getTexture("Butler.png")
    val butlerSprite = Sprite(butlerTexture)
    override var speed: Float = 2f
    override val cannotMoveStrategy = NoAction()
    override val layer = Layer.PERSON
    override var direction = Direction.RIGHT
    override var canChangeDirection = true
    override val collision = CanMoveCollision()
    val abilities: MutableList<Ability> = mutableListOf()
    var state: PlayerState = PlayerState.NORMAL

    init {
        butlerSprite.setSize(32f,48f)
    }

    override fun render(batch: SpriteBatch) {
        when(state){
            PlayerState.NORMAL -> super.render(batch)
            PlayerState.BUTLERRIDING -> {
                butlerSprite.setPosition(player.sprite.x, player.sprite.y - 16f)
                butlerSprite.draw(batch)
                super.render(batch)
            }
        }
    }
}

@Serializable
data class PlayerData(
    override val iid: String,
    override val x: Int,
    override var y: Int,
    override val width: Int,
    override val height: Int,
): GameObjectData