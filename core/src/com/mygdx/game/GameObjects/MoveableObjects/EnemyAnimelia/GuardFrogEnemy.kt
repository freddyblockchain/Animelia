package com.mygdx.game.GameObjects.MoveableObjects.EnemyAnimelia

import com.mygdx.game.EntityRefData

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Ability.Abilities.Fighting.RockThrowAbility
import com.mygdx.game.Ability.Abilities.Fire.FireballAbility
import com.mygdx.game.Ability.Abilities.Fire.FlameBreath
import com.mygdx.game.Ability.Abilities.Ice.IcicleAbility
import com.mygdx.game.Ability.Abilities.Sound.AmphibianLullaby
import com.mygdx.game.Ability.Abilities.Sound.SoundGunAbiltiy
import com.mygdx.game.Animelia.*
import com.mygdx.game.GameObjectData
import com.mygdx.game.Managers.AbilityManager
import com.mygdx.game.Utils.RandomManager
import com.mygdx.game.player

class GuardFrogEnemy(gameObjectData: GameObjectData, entityRefData: EntityRefData?) : EnemyAnimelia(gameObjectData, entityRefData) {
    override val animeliaEntity = ANIMELIA_ENTITY.GuardFrog
    override val animeliaInfo = getAnimeliaData(animeliaEntity)

    val rockThrowAbility = RockThrowAbility(this)
    val amphibianLullaby = AmphibianLullaby(this)

    override val outsideOfAggroStrategy = TurnAndFacePlayer(this)
    override val insideBattleStrategy = GuardFrogBattleStrategy(this, rockThrowAbility, amphibianLullaby)

    override val maxHealth = 30f

    override val aggroRange = 200f


    init {
        this.currentUnitVector = Vector2(1.5f,0f)
        currentHealth = maxHealth
    }
}

class GuardFrogBattleStrategy(private val enemy: GuardFrogEnemy, val rockThrow: RockThrowAbility, val amphibianLullaby: AmphibianLullaby): BattleStrategy {
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
                this.enemy.tryToUseAbility(rockThrow)
            } else{
                this.enemy.tryToUseAbility(amphibianLullaby)
            }
            enemy.timeSinceLastAbility = 0
            enemy.playerInLOS = false
        }
        enemy.timeSinceLastAbility += 1
    }
    fun getBirdPositions(): List<Vector2>{
        val first = enemy.currentPosition().cpy()
        val second = Vector2(first.x + 80f, first.y + 16f)
        val third  = Vector2(first.x + 80f, first.y + 48f)
        val fourth  = Vector2(first.x - 16f, first.y + 48f)
        val fifth  = Vector2(first.x - 16f, first.y + 16f)

        return listOf(second, third, fourth, fifth)
    }
}