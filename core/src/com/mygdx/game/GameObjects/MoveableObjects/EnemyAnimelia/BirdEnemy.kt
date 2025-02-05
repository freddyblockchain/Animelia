package com.mygdx.game.GameObjects.MoveableObjects.EnemyAnimelia

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Ability.Abilities.Fire.FireballAbility
import com.mygdx.game.Animelia.*
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.GameObjectData
import com.mygdx.game.Managers.AbilityManager
import com.mygdx.game.player

class BirdEnemy(gameObjectData: GameObjectData) : EnemyAnimelia(gameObjectData, null) {
    override val animeliaEntity = ANIMELIA_ENTITY.Bird
    override val animeliaInfo = getAnimeliaData(animeliaEntity)
    override val texture = DefaultTextureHandler.getTexture("player.png")
    override val outsideOfAggroStrategy = TurnAndFacePlayer(this)
    override val insideBattleStrategy = GoInCircles(this)

    override val maxHealth = 30f


    init {
        this.currentUnitVector = Vector2(1.5f,0f)
        currentHealth = maxHealth
    }
}