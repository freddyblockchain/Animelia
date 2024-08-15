package com.mygdx.game.GameObjects.MoveableObjects.EnemyAnimelia

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.EnemyAnimelia
import com.mygdx.game.Animelia.getAnimeliaData
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.GameObjectData

class IcePenguinEnemy(gameObjectData: GameObjectData) : EnemyAnimelia(gameObjectData) {
    override val animeliaEntity = ANIMELIA_ENTITY.IcePenguin
    override val texture = DefaultTextureHandler.getTexture("player.png")
    override val animeliaInfo = getAnimeliaData(animeliaEntity)

    override val maxHealth = 30f


    init {
        this.currentUnitVector = Vector2(1.5f,0f)
        currentHealth = maxHealth
    }

    override fun initObject() {
        sprite.setColor(Color.CORAL)
    }
}