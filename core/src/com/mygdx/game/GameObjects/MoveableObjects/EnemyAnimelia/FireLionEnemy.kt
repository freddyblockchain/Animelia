package com.mygdx.game.GameObjects.MoveableObjects.EnemyAnimelia

import com.mygdx.game.EntityRefData

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Ability.Abilities.Fire.FireballAbility
import com.mygdx.game.Ability.Abilities.Ice.IcicleAbility
import com.mygdx.game.Animelia.*
import com.mygdx.game.GameObjectData
import com.mygdx.game.Managers.AbilityManager
import com.mygdx.game.player

class FireLionEnemy(gameObjectData: GameObjectData, entityRefData: EntityRefData?) : EnemyAnimelia(gameObjectData, entityRefData) {
    override val animeliaEntity = ANIMELIA_ENTITY.FireLion
    override val animeliaInfo = getAnimeliaData(animeliaEntity)
    override val outsideOfAggroStrategy = GoInCircles(this)
    override val insideBattleStrategy = TurnAndFacePlayer(this)

    override val maxHealth = 30f
    val icicleAbility = FireballAbility(this)


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