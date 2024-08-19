package com.mygdx.game.GameObjects.MoveableObjects.EnemyAnimelia

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Animelia.*
import com.mygdx.game.GameObjectData
import com.mygdx.game.DefaultTextureHandler

enum class EnemyState {NORMAL, AGGROED}

class FireArmadilloEnemy(gameObjectData: GameObjectData) : EnemyAnimelia(gameObjectData) {
    override val animeliaEntity = ANIMELIA_ENTITY.FireArmadillo
    override val animeliaInfo = getAnimeliaData(animeliaEntity)
    override val texture = DefaultTextureHandler.getTexture("player.png")

    override val maxHealth = 30f


    init {
        this.currentUnitVector = Vector2(1.5f,0f)
        currentHealth = maxHealth
    }
}