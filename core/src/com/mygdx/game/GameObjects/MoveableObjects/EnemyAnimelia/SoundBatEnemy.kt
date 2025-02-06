package com.mygdx.game.GameObjects.MoveableObjects.EnemyAnimelia

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Ability.Abilities.Fire.FireBreath
import com.mygdx.game.Ability.Abilities.Flying.Fly
import com.mygdx.game.Ability.Abilities.Flying.Whirlwind
import com.mygdx.game.Ability.Abilities.Ice.IcicleAbility
import com.mygdx.game.Ability.Abilities.Sound.SoundGunAbility
import com.mygdx.game.Animelia.*
import com.mygdx.game.EntityRefData
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.FlyingState
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.Hazards.Cliffside
import com.mygdx.game.Managers.AbilityManager
import com.mygdx.game.Managers.RaycastListener
import com.mygdx.game.Managers.RaycastObject
import com.mygdx.game.Utils.RandomManager
import com.mygdx.game.player

class SoundBatEnemy(gameObjectData: GameObjectData, entityRefData: EntityRefData?) : EnemyAnimelia(gameObjectData, entityRefData) {
    override val animeliaEntity = ANIMELIA_ENTITY.SoundBat
    override val animeliaInfo = getAnimeliaData(ANIMELIA_ENTITY.SoundBat)
    override val outsideOfAggroStrategy = TurnAndFacePlayer(this)
    override val aggroRange = 200f

    val soundGunAbility = SoundGunAbility(this)
    val whirlwind = Whirlwind(this)
    val flying = Fly(this)

    var shouldFly = false
    var timeSinceFly = 60

    override val insideBattleStrategy: BattleStrategy = SoundBatBattleStrategy(this, soundGunAbility, whirlwind, flying)

    override val maxHealth = 30f

    lateinit var cliffsideSensor: RaycastObject

    override fun initObject() {
        super.initObject()
        sprite.setColor(Color.YELLOW)

        cliffsideSensor = RaycastObject(Vector2(32f,32f),this, listOf(Cliffside::class.java), CliffsideListener(this))
        cliffsideSensor.add()
    }


    init {
        this.currentUnitVector = Vector2(1f,0f)
        currentHealth = maxHealth
    }
}

class SoundBatBattleStrategy(private val enemy: SoundBatEnemy, val soundGunAbility: SoundGunAbility, val whirlwind: Whirlwind, val fly: Fly): BattleStrategy {
    var currentPos = 0
    val positions = getBirdPositions()
    val goToPosition = GoToPosition(enemy, positions[0])
    override fun action() {
        if(enemy.flyingState == FlyingState.NOTFLYING){
            goToPosition.action()

            if(goToPosition.isAtPosition()){
                currentPos += 1
                goToPosition.position = positions[currentPos % positions.size]
            }
        }

        if(enemy.timeSinceLastAbility >= enemy.cooldown && enemy.playerInLOS ){
            if(RandomManager.roll(50)){
                this.enemy.tryToUseAbility(soundGunAbility)
            } else{
                this.enemy.tryToUseAbility(whirlwind)
            }
            enemy.timeSinceLastAbility = 0
            enemy.playerInLOS = false
        }
        if(enemy.shouldFly && enemy.timeSinceFly >= 90){
            enemy.timeSinceFly = 0
            enemy.shouldFly = false
            this.enemy.tryToUseAbility(fly)
        }

        enemy.timeSinceFly += 1
        enemy.timeSinceLastAbility += 1
    }
    fun getBirdPositions(): List<Vector2>{
        val first = enemy.currentMiddle.cpy()
        val second = Vector2(first.x - 64f, first.y)
        val third  = Vector2(first.x - 64f, first.y -128f)
        val fourth  = Vector2(first.x + 96f, first.y - 128f)
        val fifth  = Vector2(first.x + 96f, first.y)

        return listOf(second, third, fourth, fifth)
    }
}

class CliffsideListener(val soundBatEnemy: SoundBatEnemy): RaycastListener{
    override fun objectEnteredRay(objectEntered: GameObject) {
        soundBatEnemy.shouldFly = true
    }

    override fun objectLeftRay(objectLeft: GameObject) {
        soundBatEnemy.shouldFly = false
    }

}