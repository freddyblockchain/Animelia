package com.mygdx.game.Animelia

import RemoveObjectSignal
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.CannotMoveStrategy.NoAction
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.Items.Material
import com.mygdx.game.Items.MaterialItem
import com.mygdx.game.Managers.*
import com.mygdx.game.Particles.AnimeliaEffect
import com.mygdx.game.UI.EnemyHealthStrategy
import com.mygdx.game.Utils.RandomManager
import com.mygdx.game.Utils.Triggerable

enum class ANIMELIA_ENTITY {FireArmadillo, IcePenguin, IceYeti, FireHippo, Bird, IceBird, MetalBird, Frog, GuardFrog, KingFrog, FireLion, SoundBat, FrostFireDragon, SpiritOfAnimelia }

enum class ANIMELIA_STAGE{JUNIOR, MASTER, GRANDMASTER}

interface BattleStrategy{
    fun action(): Unit
}


class GoInCircles(val enemyAnimelia: EnemyAnimelia): BattleStrategy{
    override fun action() {
        enemyAnimelia.currentUnitVector = getRotatedUnitVectorClockwise(enemyAnimelia.currentUnitVector, 1f)
        enemyAnimelia.move(enemyAnimelia.currentUnitVector)
        enemyAnimelia.setRotation(enemyAnimelia.currentUnitVector, enemyAnimelia, 90f)
    }

}
class DoNothing(): BattleStrategy{
    override fun action() {
    }

}

class TurnAndFacePlayer(val enemyAnimelia: EnemyAnimelia): BattleStrategy{
    override fun action() {
        val unitVectorToDirection = getUnitVectorTowardsPoint(enemyAnimelia.currentMiddle, player.currentPosition())
        enemyAnimelia.currentUnitVector = unitVectorToDirection
        enemyAnimelia.setRotation(unitVectorToDirection, enemyAnimelia, 90f)
    }

}

class GoToPosition(val enemyAnimelia: EnemyAnimelia, var position: Vector2): BattleStrategy{
    override fun action() {
        if(enemyAnimelia.cannotMoveCount == 0){
            val unitVectorToDirection = getUnitVectorTowardsPoint(enemyAnimelia.currentMiddle, position)
            enemyAnimelia.setRotation(unitVectorToDirection, enemyAnimelia, 90f)
            enemyAnimelia.move(unitVectorToDirection)
        }
    }

    fun relaxedIsAtPosition(): Boolean{
        val distance = distance(enemyAnimelia.currentMiddle, position)
        val distanceWithoutSpeed = distance / enemyAnimelia.speed

        return distanceWithoutSpeed <= 32f
    }

    fun isAtPosition(): Boolean{

        val distance = distance(enemyAnimelia.currentMiddle, position)
        val distanceWithoutSpeed = distance / enemyAnimelia.speed

        return distanceWithoutSpeed <= 1f
    }
}

abstract class EnemyAnimelia(gameObjectData: GameObjectData, val entityRefData: EntityRefData?): FightableObject(gameObjectData, Vector2(32f,32f)), RaycastListener {
    abstract val animeliaEntity: ANIMELIA_ENTITY
    abstract val animeliaInfo: AnimeliaData

    override var speed = 1f
    override val cannotMoveStrategy = NoAction()
    override val layer = Layer.PERSON

    var playerInLOS = false

    override val healthStrategy = EnemyHealthStrategy()
    override var direction = Direction.DOWN
    override var canChangeDirection = true

    open val aggroRange = 150f
    var aggroCircle = Circle(0f, 0f, aggroRange)

    abstract val outsideOfAggroStrategy: BattleStrategy

    abstract val insideBattleStrategy: BattleStrategy

    override val texture = DefaultTextureHandler.getTexture("player.png")
    var encounterFrames = 0

    val fogEffect = ParticleEffect()

    var animeliaEffect: AnimeliaEffect


    val cooldown = 120
    var timeSinceLastAbility: Int = 0

    lateinit var raycastObject: RaycastObject

    init {
        fogEffect.load(Gdx.files.internal("Particles/fog.p"), Gdx.files.internal("Particles"))
        animeliaEffect = AnimeliaEffect(fogEffect)
        animeliaEffect.particleEffect.emitters.forEach { it.reset() }
        animeliaEffect.start()
    }

    override fun initObject() {
        sprite.setColor(Color.CHARTREUSE)
        raycastObject = RaycastObject(Vector2(64f,32f),this, listOf(Player::class.java), this)
        raycastObject.add()


        polygon.scale(-0.3f)

    }
    override fun render(batch: SpriteBatch) {
        setAnimeliaSpriteTexture(this, animeliaInfo)
        super.render(batch)

        animeliaEffect.render(batch)
    }

    override fun remove() {
        super.remove()
        raycastObject.remove()

    }

    override fun frameTask() {
        val currentMiddle = this.currentMiddle
        aggroCircle = Circle(currentMiddle.x, currentMiddle.y, aggroRange)
        if(aggroCircle.contains(player.currentPosition())){
            insideBattleStrategy.action()
            encounterFrames += 1
        } else{
            outsideOfAggroStrategy.action()
            encounterFrames = 0
        }

        // death
        if(this.currentHealth <= 0){
            if(this.animeliaEntity == ANIMELIA_ENTITY.IceBird || this.animeliaEntity == ANIMELIA_ENTITY.GuardFrog || this.animeliaEntity == ANIMELIA_ENTITY.FireLion || this.animeliaEntity == ANIMELIA_ENTITY.SoundBat){
                SignalManager.emitSignal(RemoveObjectSignal(this.gameObjectIid))
            } else{
                this.remove()
            }
           // generalSaveState.inventory.goldReceived(1, this.currentMiddle)
            PlayerStatus.animeliaClonesKilled += 1

            if(RandomManager.roll(50)){
                val materialItem = MaterialItem(GameObjectData(x = this.x.toInt(), y = this.y.toInt(), width = 32, height = 32), Material.ANIMELIABONE)
                materialItem.add()
            }

            if(entityRefData != null){
                val gameObjectToTrigger = AreaManager.getObjectWithIid(entityRefData.entityIid, entityRefData.levelIid) as Triggerable
                gameObjectToTrigger.onTrigger()
            }
        }

        animeliaEffect.particleEffect.setPosition(this.currentMiddle.x, this.currentMiddle.y + this.height / 2 )

        super.frameTask()
    }

    override fun objectEnteredRay(objectEntered: GameObject) {
        playerInLOS = true
    }

    override fun objectLeftRay(objectLeft: GameObject) {
        playerInLOS = false
    }

}

fun setAnimeliaSpriteTexture(animelia: FightableObject, animeliaInfo: AnimeliaData){
    if(animelia.isMoving){
        animeliaInfo.animeliaAnimation.setSpriteTextureBasedOnAnimation(animelia.sprite)
        animelia.isMoving = false
    } else {
        animelia.sprite.texture = DefaultTextureHandler.getTexture(animeliaInfo.textureName)
        animeliaInfo.animeliaAnimation.reset()
    }
}