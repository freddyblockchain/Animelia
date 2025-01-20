package com.mygdx.game.GameObjects.MoveableObjects.EnemyAnimelia

import com.mygdx.game.EntityRefData

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Ability.Abilities.Ice.IcicleAbility
import com.mygdx.game.Animelia.*
import com.mygdx.game.GameObjectData
import com.mygdx.game.Managers.AbilityManager
import com.mygdx.game.player

class IceBirdEnemy(gameObjectData: GameObjectData, entityRefData: EntityRefData?) : EnemyAnimelia(gameObjectData, entityRefData) {
    override val animeliaEntity = ANIMELIA_ENTITY.IceBird
    override val animeliaInfo = getAnimeliaData(animeliaEntity)
    override val outsideOfAggroStrategy = TurnAndFacePlayer(this)
    override val insideBattleStrategy = IceBirdBattleStrategy(this)

    override val maxHealth = 30f
    val icicleAbility = IcicleAbility(this)

    override var speed = 0.8f


    init {
        this.currentUnitVector = Vector2(1.5f,0f)
        currentHealth = maxHealth
    }

    override fun initObject() {
        sprite.setColor(Color.CORAL)
    }

    override fun frameTask() {
        super.frameTask()
        if(aggroCircle.contains(player.currentPosition())){
            if(encounterFrames % 180 == 0){
                AbilityManager.abilities.add(icicleAbility)
            }
        }
    }
}

class IceBirdBattleStrategy(private val enemy: IceBirdEnemy): BattleStrategy {
    var currentPos = 0
    val positions = getBirdPositions()
    val goToPosition = GoToPosition(enemy, positions[0])
    override fun action() {
        goToPosition.action()

        if(goToPosition.isAtPosition()){
            currentPos += 1
            goToPosition.position = positions[currentPos % 4]
        }
    }
    fun getBirdPositions(): List<Vector2>{
        val first = enemy.currentMiddle.cpy()
        val second = Vector2(first.x, first.y - 32f)
        val third  = Vector2(first.x - 32f, first.y - 32f)
        val fourth  = Vector2(first.x - 32f, first.y)

        return listOf(first, second, third, fourth)
    }
}