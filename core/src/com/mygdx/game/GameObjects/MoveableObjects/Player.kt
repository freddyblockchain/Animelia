package com.mygdx.game.GameObjects.MoveableEntities.Characters

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Ability.Abilities.Fighting.FireballAbility
import com.mygdx.game.Ability.Abilities.Fighting.TailSwipe
import com.mygdx.game.Ability.KeyAbility
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.AnimeliaData
import com.mygdx.game.Animelia.getAnimeliaData
import com.mygdx.game.Animelia.setAnimeliaSpriteTexture
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
import com.mygdx.game.UI.HealthStrategy
import com.mygdx.game.UI.PlayerHealthStrategy

class Player(gameObjectData: GameObjectData, size: Vector2)
    : FightableObject(gameObjectData, size), SaveStateEntity by DefaultSaveStateHandler() {
    override val texture = DefaultTextureHandler.getTexture("player.png")
    override var speed: Float = 5f
    override val cannotMoveStrategy = NoAction()
    override val layer = Layer.PERSON
    override var direction = Direction.RIGHT
    override var canChangeDirection = true
    override val collision = CanMoveCollision()
    override val maxHealth = 30f
    override var stats = Stats()
    val abilities: MutableList<KeyAbility> = mutableListOf(TailSwipe(this), FireballAbility(this))
    var currentAnimelia: ANIMELIA_ENTITY = ANIMELIA_ENTITY.FireArmadillo
    var animeliaInfo = getAnimeliaData(currentAnimelia)

    override val healthStrategy = PlayerHealthStrategy()

    init {
        currentHealth = maxHealth
    }

    override fun render(batch: SpriteBatch) {
        setAnimeliaSpriteTexture(this, animeliaInfo)
        super.render(batch)
    }
}