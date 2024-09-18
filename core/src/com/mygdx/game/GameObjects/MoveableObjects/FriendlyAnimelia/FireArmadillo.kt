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
import com.mygdx.game.UI.Conversation.Conversation
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

    val inCitySpeechOne = SpeechData("Fire Armadillo", "Its good to be back!")
    val inCitySpeechTwo = SpeechData("Fire Armadillo", "I build a training statue")
    val inCitySpeechThree = SpeechData("Fire Armadillo", "Speak to me for training information")

    override val inCitySpeeches = listOf(inCitySpeechOne, inCitySpeechTwo, inCitySpeechThree)


    val sone = SpeechData("Me", "Can you tell me about Stats?")
    val stwo = SpeechData("Fire Armadillo", "Yes! There are four different stats")
    val sthree= SpeechData("Fire Armadillo", "Offence affects how much damage you do")
    val sfour = SpeechData("Fire Armadillo", "Defence affects how much damage you take")
    val sfive= SpeechData("Fire Armadillo", "Speed affects how fast you move")
    val ssix = SpeechData("Fire Armadillo", "Intelligence affects how many abilities you can use")
    val statsConversation = Conversation(listOf(sone, stwo,sthree, sfour, sfive, ssix))

    val tone = SpeechData("Me", "Can you tell me about Training Points?")
    val ttwo = SpeechData("Fire Armadillo", "Yes! Training points determine how much you can increase stats!")
    val tthree = SpeechData("Fire Armadillo", "You can use your training points to increase stats.")
    val tfour = SpeechData("Fire Armadillo", "When you reincarnate, the training points are reset!")
    val tfive = SpeechData("Fire Armadillo", "When you anivolve, you gain five additional training points!")
    val tsix = SpeechData("Me", "Are there other ways to increase training points?")
    val tseven = SpeechData("Fire Armadillo", "Not to my knowledge. But maybe you will find some")
    val trainingPointsConversation = Conversation(listOf(tone, ttwo, tthree, tfour, tfive, tsix, tseven))

    override val conversationOptions = mapOf("Stats" to statsConversation, "Training Points" to trainingPointsConversation)


    override fun recruitmentAction() {
        val trainingStation = TrainingStation(GameObjectData(x = cityPosition.x.toInt() + this.width.toInt(), y=cityPosition.y.toInt(), width = 32, height = 64), true)
        val firstArea = AreaManager.getArea("World1")
        this.setPosition(cityPosition.currentPosition())
        trainingStation.setPosition(cityPosition.currentPosition() + Vector2(this.width * 1.5f, 0f))
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