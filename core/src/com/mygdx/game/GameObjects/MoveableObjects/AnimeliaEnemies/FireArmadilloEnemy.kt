package com.mygdx.game.GameObjects.MoveableObjects.AnimeliaEnemies

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.EnemyAnimelia
import com.mygdx.game.Animelia.FriendlyAnimelia
import com.mygdx.game.CannotMoveStrategy.CannotMoveStrategy
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Direction
import com.mygdx.game.getRotatedUnitVectorClockwise
import com.mygdx.game.player

enum class EnemyState {NORMAL, AGGROED}

class FireArmadilloEnemy(gameObjectData: GameObjectData) : EnemyAnimelia(gameObjectData) {
    override val animeliaEntity = ANIMELIA_ENTITY.FIRE_ARMADILLO
    override val texture = DefaultTextureHandler.getTexture("player.png")

    init {
        this.currentUnitVector = Vector2(1.5f,0f)
    }
    override fun frameTask() {
        super.frameTask()
        this.currentUnitVector = getRotatedUnitVectorClockwise(this.currentUnitVector, 1f)
        this.move(this.currentUnitVector)
        this.setRotation(this.currentUnitVector, this, 90f)
    }
}