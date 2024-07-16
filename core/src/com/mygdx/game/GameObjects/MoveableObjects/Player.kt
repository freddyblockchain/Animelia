package com.mygdx.game.GameObjects.MoveableEntities.Characters

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Ability.Abilities.Fighting.TailSwipe
import com.mygdx.game.Ability.KeyAbility
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.AnimeliaData
import com.mygdx.game.Animelia.getAnimeliaData
import com.mygdx.game.CannotMoveStrategy.NoAction
import com.mygdx.game.Collisions.CanMoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.Managers.PlayerStatus
import com.mygdx.game.Managers.Stats
import com.mygdx.game.SaveHandling.SaveStateEntity
import com.mygdx.game.Saving.DefaultSaveStateHandler

class Player(gameObjectData: GameObjectData, size: Vector2)
    : FightableObject(gameObjectData, size), SaveStateEntity by DefaultSaveStateHandler() {
    override val texture = DefaultTextureHandler.getTexture("player.png")
    override var speed: Float = 2f
    override val cannotMoveStrategy = NoAction()
    override val layer = Layer.PERSON
    override var direction = Direction.RIGHT
    override var canChangeDirection = true
    override val collision = CanMoveCollision()
    override val stats = PlayerStatus.playerStats
    val abilities: MutableList<KeyAbility> = mutableListOf(TailSwipe(this))
    var currentAnimelia: ANIMELIA_ENTITY = ANIMELIA_ENTITY.FIRE_ARMADILLO
    val animeliaInfo: AnimeliaData
    get() = getAnimeliaData(currentAnimelia)

    override fun render(batch: SpriteBatch) {
        sprite.texture = animeliaInfo.gameTexture
        super.render(batch)
    }
}