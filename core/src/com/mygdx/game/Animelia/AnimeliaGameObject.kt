package com.mygdx.game.Animelia

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.CannotMoveStrategy.NoAction
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Managers.InventoryManager
import com.mygdx.game.Managers.PlayerStatus
import com.mygdx.game.UI.EnemyHealthStrategy
import com.mygdx.game.UI.HealthStrategy
import com.mygdx.game.getRotatedUnitVectorClockwise
import com.mygdx.game.getUnitVectorTowardsPoint
import com.mygdx.game.player

enum class ANIMELIA_ENTITY {FIRE_ARMADILLO, ICE_PENGUIN, FIRE_DRAGON, ICE_DINASAUR, ICE_YETI, FIRE_HIPPO}

enum class ELEMENTAL_TYPE{ICE, FIRE}

enum class ANIMELIA_STAGE{JUNIOR, MASTER, GRANDMASTER}

abstract class EnemyAnimelia(gameObjectData: GameObjectData): FightableObject(gameObjectData, Vector2(32f,32f)) {
    abstract val animeliaEntity: ANIMELIA_ENTITY
    abstract val animeliaInfo: AnimeliaData

    override var speed = 1f
    override val cannotMoveStrategy = NoAction()
    override val layer = Layer.ONGROUND

    override val healthStrategy = EnemyHealthStrategy()
    override var direction = Direction.DOWN
    override var canChangeDirection = true

    override fun initObject() {
        sprite.setColor(Color.CHARTREUSE)
    }
    override fun render(batch: SpriteBatch) {
        setAnimeliaSpriteTexture(this, animeliaInfo)
        super.render(batch)
    }

    override fun frameTask() {
        val currentMiddle = this.currentMiddle
        val circle = Circle(currentMiddle.x, currentMiddle.y, 100f)
        if(circle.contains(player.currentPosition())){
            val unitVectorToDirection = getUnitVectorTowardsPoint(currentMiddle, player.currentPosition())
            this.currentUnitVector = unitVectorToDirection
            this.setRotation(unitVectorToDirection, this, 90f)
        } else{
            this.currentUnitVector = getRotatedUnitVectorClockwise(this.currentUnitVector, 1f)
            this.move(this.currentUnitVector)
            this.setRotation(this.currentUnitVector, this, 90f)
        }

        if(this.currentHealth <= 0){
            this.remove()
            InventoryManager.goldReceived(1, this.currentMiddle)
            PlayerStatus.animeliaClonesKilled += 1
        }

        super.frameTask()
    }

}

fun setAnimeliaSpriteTexture(animelia: FightableObject, animeliaInfo: AnimeliaData){
    if(animelia.isMoving){
        animeliaInfo.animeliaAnimation.setSpriteTextureBasedOnAnimation(animelia.sprite)
        animelia.isMoving = false
    } else {
        animelia.sprite.texture = animeliaInfo.gameTexture
        animeliaInfo.animeliaAnimation.reset()
    }
}

fun getAnimeliaEntity(animeliaType: String): ANIMELIA_ENTITY {
    return when(animeliaType){
        "Fire_Armadillo" -> ANIMELIA_ENTITY.FIRE_ARMADILLO
        else -> ANIMELIA_ENTITY.FIRE_DRAGON
    }
}