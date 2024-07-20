package com.mygdx.game.GameObjects.MoveableObjects.AnimeliaEnemies

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Animelia.*
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.CannotMoveStrategy.CannotMoveStrategy
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Direction
import com.mygdx.game.getRotatedUnitVectorClockwise
import com.mygdx.game.player
import kotlin.math.max

enum class EnemyState {NORMAL, AGGROED}

class FireArmadilloEnemy(gameObjectData: GameObjectData) : EnemyAnimelia(gameObjectData) {
    override val animeliaEntity = ANIMELIA_ENTITY.FIRE_ARMADILLO
    override val texture = DefaultTextureHandler.getTexture("player.png")
    override val animeliaInfo = getAnimeliaData(animeliaEntity)

    override val maxHealth = 30f


    init {
        this.currentUnitVector = Vector2(1.5f,0f)
        currentHealth = maxHealth
    }
}