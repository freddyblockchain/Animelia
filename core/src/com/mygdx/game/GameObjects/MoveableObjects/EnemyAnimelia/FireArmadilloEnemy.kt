package com.mygdx.game.GameObjects.MoveableObjects.EnemyAnimelia

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Ability.Abilities.Fire.FireballAbility
import com.mygdx.game.Animelia.*
import com.mygdx.game.GameObjectData
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Managers.AbilityManager
import com.mygdx.game.player

enum class EnemyState {NORMAL, AGGROED}

class FireArmadilloEnemy(gameObjectData: GameObjectData) : EnemyAnimelia(gameObjectData) {
    override val animeliaEntity = ANIMELIA_ENTITY.FireArmadillo
    override val animeliaInfo = getAnimeliaData(animeliaEntity)
    override val texture = DefaultTextureHandler.getTexture("player.png")

    override val maxHealth = 30f
    val fireballAbility = FireballAbility(this)

    init {
        this.currentUnitVector = Vector2(1.5f,0f)
        currentHealth = maxHealth
    }
    override fun frameTask() {
        super.frameTask()
        if(aggroCircle.contains(player.currentPosition())){
            if(aggroCounter % 180 == 0){
                AbilityManager.abilities.add(fireballAbility)
            }
        }
    }
}