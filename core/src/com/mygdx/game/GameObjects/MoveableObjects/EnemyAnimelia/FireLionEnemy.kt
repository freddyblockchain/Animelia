package com.mygdx.game.GameObjects.MoveableObjects.EnemyAnimelia

import com.mygdx.game.EntityRefData

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Ability.Abilities.Fire.FireballAbility
import com.mygdx.game.Ability.Abilities.Fire.FlameBreath
import com.mygdx.game.Ability.Abilities.Ice.IceBreath
import com.mygdx.game.Ability.Abilities.Ice.IcicleAbility
import com.mygdx.game.Animelia.*
import com.mygdx.game.GameObjectData
import com.mygdx.game.Managers.AbilityManager
import com.mygdx.game.Utils.RandomManager
import com.mygdx.game.player

class FireLionEnemy(gameObjectData: GameObjectData, entityRefData: EntityRefData?) : EnemyAnimelia(gameObjectData, entityRefData) {
    override val animeliaEntity = ANIMELIA_ENTITY.FireLion
    override val animeliaInfo = getAnimeliaData(animeliaEntity)

    override val maxHealth = 30f
    val fireball = FireballAbility(this)
    val fireBreath = FlameBreath(this)

    override val outsideOfAggroStrategy = TurnAndFacePlayer(this)
    override val insideBattleStrategy = FireLionBattleStrategy(this, fireball, fireBreath)

    override val aggroRange = 200f

    override fun initObject() {
        super.initObject()
        sprite.setColor(Color.ORANGE)
    }


    init {
        this.currentUnitVector = Vector2(1.5f,0f)
        currentHealth = maxHealth
    }
}

class FireLionBattleStrategy(private val enemy: FireLionEnemy, val fireball: FireballAbility, val flameBreath: FlameBreath): BattleStrategy {
    var currentPos = 0
    val positions = getBirdPositions()
    val goToPosition = GoToPosition(enemy, positions[0])
    override fun action() {
        goToPosition.action()

        if(goToPosition.isAtPosition()){
            currentPos += 1
            goToPosition.position = positions[currentPos % positions.size]
        }

        if(enemy.timeSinceLastAbility >= enemy.cooldown && enemy.playerInLOS ){
            if(RandomManager.roll(50)){
                this.enemy.tryToUseAbility(fireball)
            } else{
                this.enemy.tryToUseAbility(flameBreath)
            }
            enemy.timeSinceLastAbility = 0
            enemy.playerInLOS = false
        }
        enemy.timeSinceLastAbility += 1
    }
    fun getBirdPositions(): List<Vector2>{
        val first = enemy.currentMiddle.cpy()
        val second = Vector2(first.x - 64f, first.y)
        val third  = Vector2(first.x, first.y + 64f)
        val fourth  = Vector2(first.x + 64f, first.y)
        val fifth  = Vector2(first.x, first.y - 64f)

        return listOf(first, second, first, third, first, fourth,first, fifth)
    }
}