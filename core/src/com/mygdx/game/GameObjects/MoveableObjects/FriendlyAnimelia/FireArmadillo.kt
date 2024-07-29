package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.AnimeliaRecruitmendCondition
import com.mygdx.game.Animelia.FriendlyAnimelia
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.PlayerStatus

class FireArmadillo(gameObjectData: GameObjectData) : FriendlyAnimelia(gameObjectData) {
    override val animeliaEntity = ANIMELIA_ENTITY.FIRE_ARMADILLO

    override val texture = DefaultTextureHandler.getTexture("Animelias/firearmadillo-straight.png")
    override val layer = Layer.ONGROUND

    override fun recruitmentAction() {
        this.remove()
        val firstArea = AreaManager.getArea("World1")
        this.setPosition(Vector2(100f,-100f))
        firstArea.gameObjects.add(this)
    }

    init {
        animeliaRecruitmentConditions.add(AmountOfAnimeliasSlain(3))
    }
}

class AmountOfAnimeliasSlain(val amount: Int): AnimeliaRecruitmendCondition{
    override fun isConditionFulfilled(): Boolean {
        return PlayerStatus.animeliaClonesKilled >= amount
    }
}