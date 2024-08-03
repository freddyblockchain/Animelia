package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.AnimeliaRecruitmendCondition
import com.mygdx.game.Animelia.FriendlyAnimelia
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.Structures.TrainingStation
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.PlayerStatus
import com.mygdx.game.plus

class FireArmadillo(gameObjectData: GameObjectData, cityPositionEntityId: String) : FriendlyAnimelia(gameObjectData, cityPositionEntityId) {
    override val animeliaEntity = ANIMELIA_ENTITY.FIRE_ARMADILLO

    override val texture = DefaultTextureHandler.getTexture("Animelias/firearmadillo-straight.png")
    override val layer = Layer.ONGROUND

    override fun recruitmentAction() {
        this.remove()
        val trainingStation = TrainingStation(GameObjectData(x = cityPosition.x.toInt() + this.width.toInt(), y=cityPosition.y.toInt(), width = 32, height = 64))
        val firstArea = AreaManager.getArea("World1")
        this.setPosition(cityPosition.currentPosition())
        trainingStation.setPosition(cityPosition.currentPosition() + Vector2(this.width, 0f))
        firstArea.gameObjects.add(this)
        firstArea.gameObjects.add(trainingStation)
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