package com.mygdx.game.GameObjects.MoveableObjects.EnemyAnimelia

import com.mygdx.game.EntityRefData

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Ability.Abilities.Ice.IceBreath
import com.mygdx.game.Ability.Abilities.Ice.IcicleAbility
import com.mygdx.game.Animelia.*
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.DefaultRotationalObject
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.GameObject.MoveableObject
import com.mygdx.game.GameObjects.GameObject.RotationalObject
import com.mygdx.game.Managers.AbilityManager
import com.mygdx.game.Utils.RandomManager
import com.mygdx.game.Utils.RectanglePolygon
import com.mygdx.game.Utils.drawPolygonShape
import com.mygdx.game.player

class IceBirdEnemy(gameObjectData: GameObjectData, entityRefData: EntityRefData?) : EnemyAnimelia(gameObjectData, entityRefData) {
    override val animeliaEntity = ANIMELIA_ENTITY.IceBird
    override val animeliaInfo = getAnimeliaData(animeliaEntity)
    override val outsideOfAggroStrategy = TurnAndFacePlayer(this)

    override val maxHealth = 30f
    val icicleAbility = IcicleAbility(this)
    val iceBreath = IceBreath(this)
    override val insideBattleStrategy = IceBirdBattleStrategy(this,icicleAbility)

    override var speed = 0.8f

    val cooldown = 120
    var timeSinceLastAbility: Int = 0


    init {
        this.currentUnitVector = Vector2(1.5f,0f)
        currentHealth = maxHealth
    }

    override fun initObject() {
        super.initObject()
        sprite.setColor(Color.CORAL)
    }

}

class IceBirdBattleStrategy(private val enemy: IceBirdEnemy, val icicleAbility: IcicleAbility): BattleStrategy {
    var currentPos = 0
    val positions = getBirdPositions()
    val goToPosition = GoToPosition(enemy, positions[0])
    override fun action() {
        goToPosition.action()

        if(goToPosition.isAtPosition()){
            currentPos += 1
            goToPosition.position = positions[currentPos % 4]
        }

        if(enemy.timeSinceLastAbility >= enemy.cooldown && enemy.playerInLOS ){
            if(RandomManager.roll(50)){
                AbilityManager.abilities.add(icicleAbility)
            } else{
                AbilityManager.abilities.add(IceBreath(enemy))
            }
            enemy.timeSinceLastAbility = 0
            enemy.playerInLOS = false
        }
        enemy.timeSinceLastAbility += 1
    }
    fun getBirdPositions(): List<Vector2>{
        val first = enemy.currentMiddle.cpy()
        val second = Vector2(first.x, first.y - 32f)
        val third  = Vector2(first.x - 32f, first.y - 32f)
        val fourth  = Vector2(first.x - 32f, first.y)

        return listOf(first, second, third, fourth)
    }
}