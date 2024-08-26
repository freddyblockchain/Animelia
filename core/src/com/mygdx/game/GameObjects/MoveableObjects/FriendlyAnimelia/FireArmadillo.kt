package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.AnimeliaRecruitmendCondition
import com.mygdx.game.Animelia.FriendlyAnimelia
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.EntityRefData
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.Structures.TrainingStation
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.PlayerStatus
import com.mygdx.game.UI.Conversation.SpeechData
import com.mygdx.game.plus

class FireArmadillo(gameObjectData: GameObjectData, cityPositionEntityId: EntityRefData) : FriendlyAnimelia(gameObjectData, cityPositionEntityId) {
    override val animeliaEntity = ANIMELIA_ENTITY.FireArmadillo

    val speech1 = SpeechData("Me", "Do you wanna come to the city?")
    val speech2 = SpeechData("Fire Armadillo", "hmm, prove your worth by defeating 3 animelia clones")
    val speech3 = SpeechData("Me", "Alright, i'll do that")

    override val speeches = listOf(speech1, speech2, speech3)

    val citySpeech1 = SpeechData("Fire Armadillo", "So, you actually defeated 3 clones")
    val citySpeech2 = SpeechData("Fire Armadillo", "I have to admit. I thought you were a faker")
    val citySpeech3 = SpeechData("Fire Armadillo", "But you have proven yourself. Alright, i'll come back to the city.")

    override val goingToCitySpeech = listOf(citySpeech1,citySpeech2,citySpeech3)

    val inCitySpeechOne = SpeechData("Fire Armadillo", "I love it here!")
    val inCitySpeechTwo = SpeechData("Fire Armadillo", "I opened up a training center here")
    val inCitySpeechThree = SpeechData("Me", "That's awesome! What can i use it for?")
    val inCitySpeechFour = SpeechData("Fire Armadillo", "You can train stats!")
    val inCitySpeechFive = SpeechData("Fire Armadillo", "Stats matter in battle! They also have a use in the world...")

    override val inCitySpeeches = listOf(inCitySpeechOne, inCitySpeechTwo, inCitySpeechThree, inCitySpeechFour, inCitySpeechFive)


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