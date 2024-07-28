package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.AnimeliaRecruitmendCondition
import com.mygdx.game.Animelia.FriendlyAnimelia
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Managers.PlayerStatus

class FireArmadillo(gameObjectData: GameObjectData) : FriendlyAnimelia(gameObjectData) {
    override val animeliaEntity = ANIMELIA_ENTITY.FIRE_ARMADILLO
    override val texture = DefaultTextureHandler.getTexture("Animelias/firearmadillo-straight.png")
    override val layer = Layer.ONGROUND

    init {
        animeliaRecruitmentConditions.add(AmountOfAnimeliasSlain(3))
    }
}

class AmountOfAnimeliasSlain(val amount: Int): AnimeliaRecruitmendCondition{
    override fun isConditionFulfilled(): Boolean {
        return PlayerStatus.animeliaClonesKilled >= amount
    }
}