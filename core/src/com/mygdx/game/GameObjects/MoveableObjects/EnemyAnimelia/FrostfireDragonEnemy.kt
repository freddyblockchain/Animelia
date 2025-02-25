package com.mygdx.game.GameObjects.MoveableObjects.EnemyAnimelia

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Ability.Abilities.Fire.FireBreath
import com.mygdx.game.Ability.Abilities.Flying.AerialDeath
import com.mygdx.game.Ability.Abilities.Ice.IceBreath
import com.mygdx.game.Ability.Abilities.Ice.IcicleAbility
import com.mygdx.game.Ability.KeyAbility
import com.mygdx.game.Animelia.*
import com.mygdx.game.EntityRefData
import com.mygdx.game.GameObjectData
import com.mygdx.game.Managers.AbilityManager
import com.mygdx.game.player
import kotlin.random.Random

class FrostfireDragonEnemy(gameObjectData: GameObjectData, entityRefData: EntityRefData?) : EnemyAnimelia(gameObjectData, entityRefData) {
    override val animeliaEntity = ANIMELIA_ENTITY.FrostFireDragon
    override val animeliaInfo = getAnimeliaData(animeliaEntity)
    override val outsideOfAggroStrategy = TurnAndFacePlayer(this)

    override val aggroRange = 250f

    override val maxHealth = 80f
    val arealDeath = AerialDeath(this)
    val fireBreath = FireBreath(this)
    val frostBreath = IceBreath(this)
    override val insideBattleStrategy = FrostFireDragonBattleStrategy(this,arealDeath, fireBreath, frostBreath)

    override var speed = 1.5f


    init {
        this.currentUnitVector = Vector2(1.0f,0f)
        currentHealth = maxHealth
    }

    override fun initObject() {
        super.initObject()
        sprite.setColor(Color.CORAL)
    }

}

class FrostFireDragonBattleStrategy(private val enemy: FrostfireDragonEnemy, val arealDeath: AerialDeath, val  fireBreath: FireBreath, val frostBreath: IceBreath):
    BattleStrategy {
    var currentPos = 0
    var playerPos = Vector2(0f,0f)
    val positions = getBirdPositions()
    val goToPosition = GoToPosition(enemy, positions[0])
    override fun action() {
        goToPosition.action()

        if(goToPosition.isAtPosition()){
            playerPos.x = player.currentMiddle.x
            playerPos.y = player.currentMiddle.y
            currentPos += 1
            goToPosition.position = positions[currentPos % positions.size]
        }

        if(enemy.timeSinceLastAbility >= enemy.cooldown && enemy.playerInLOS ){
            val randomNum = Random.nextInt(1, 101)
            var ability: KeyAbility = arealDeath
            if(randomNum > 66){
                ability = fireBreath
            } else if(randomNum > 32){
                ability = frostBreath
            }
            AbilityManager.abilities.add(ability)

            enemy.timeSinceLastAbility = 0
            enemy.playerInLOS = false
        }
        enemy.timeSinceLastAbility += 1
    }
    fun getBirdPositions(): List<Vector2>{
        val first = enemy.currentMiddle.cpy()
        val second = Vector2(first.x - 96f, first.y)
        val third  = Vector2(first.x + 128f, first.y)

        return listOf(second, playerPos, third, playerPos)
    }
}