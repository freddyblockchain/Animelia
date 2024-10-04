package com.mygdx.game.Animelia

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.CannotMoveStrategy.NoAction
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.Inventory.Inventory
import com.mygdx.game.Items.Material
import com.mygdx.game.Items.MaterialItem
import com.mygdx.game.Managers.PlayerStatus
import com.mygdx.game.UI.EnemyHealthStrategy
import com.mygdx.game.Utils.RandomManager

enum class ANIMELIA_ENTITY {FireArmadillo, IcePenguin, FireDragon, IceDinasaur, IceYeti, FireHippo, Bird}

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
    var aggroCircle = Circle(0f, 0f, 100f)

    override val texture = DefaultTextureHandler.getTexture("player.png")
    var aggroCounter = 0

    override fun initObject() {
        sprite.setColor(Color.CHARTREUSE)
    }
    override fun render(batch: SpriteBatch) {
        setAnimeliaSpriteTexture(this, animeliaInfo)
        super.render(batch)
    }

    override fun frameTask() {
        val currentMiddle = this.currentMiddle
        aggroCircle = Circle(currentMiddle.x, currentMiddle.y, 150f)
        if(aggroCircle.contains(player.currentPosition())){
            val unitVectorToDirection = getUnitVectorTowardsPoint(currentMiddle, player.currentPosition())
            this.currentUnitVector = unitVectorToDirection
            this.setRotation(unitVectorToDirection, this, 90f)
            aggroCounter += 1
        } else{
            this.currentUnitVector = getRotatedUnitVectorClockwise(this.currentUnitVector, 1f)
            this.move(this.currentUnitVector)
            this.setRotation(this.currentUnitVector, this, 90f)
            aggroCounter = 0
        }

        if(this.currentHealth <= 0){
            this.remove()
            generalSaveState.inventory.goldReceived(1, this.currentMiddle)
            PlayerStatus.animeliaClonesKilled += 1

            if(RandomManager.roll(50)){
                val materialItem = MaterialItem(GameObjectData(x = this.x.toInt(), y = this.y.toInt(), width = 32, height = 32), Material.ANIMELIABONE)
                materialItem.add()
            }
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
        "FireArmadillo" -> ANIMELIA_ENTITY.FireArmadillo
        "FireHippo" -> ANIMELIA_ENTITY.FireHippo
        "IcePenguin" -> ANIMELIA_ENTITY.IcePenguin
        "IceYeti" -> ANIMELIA_ENTITY.IceYeti
        "Bird" -> ANIMELIA_ENTITY.Bird
        else -> ANIMELIA_ENTITY.FireArmadillo
    }
}