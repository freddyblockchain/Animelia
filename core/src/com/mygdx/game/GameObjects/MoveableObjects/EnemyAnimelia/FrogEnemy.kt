package com.mygdx.game.GameObjects.MoveableObjects.EnemyAnimelia

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Ability.Abilities.Sound.SoundGunAbility
import com.mygdx.game.Animelia.*
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.GameObjectData
import com.mygdx.game.Managers.AbilityManager

class FrogEnemy(gameObjectData: GameObjectData) : EnemyAnimelia(gameObjectData, null) {
    override val animeliaEntity = ANIMELIA_ENTITY.Frog
    override val animeliaInfo = getAnimeliaData(animeliaEntity)
    override val texture = DefaultTextureHandler.getTexture("player.png")
    override val outsideOfAggroStrategy = DoNothing()
    override val insideBattleStrategy = FrogBattleStrategy(this)

    override val maxHealth = 30f

    val soundAbility = SoundGunAbility(this)


    init {
        this.currentUnitVector = Vector2(0f, 1f)
        currentHealth = maxHealth
    }
}


class FrogBattleStrategy(val enemyAnimelia: FrogEnemy): BattleStrategy{
    override fun action() {
        if(enemyAnimelia.encounterFrames % 180 == 0){
            AbilityManager.abilities.add(enemyAnimelia.soundAbility)
        }
    }
}