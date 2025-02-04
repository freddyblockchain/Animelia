package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import RailwayFixedSignal
import com.mygdx.game.GameObjectData
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.FriendlyAnimeliaInWorld
import com.mygdx.game.EntityRefData
import com.mygdx.game.UI.Conversation.SpeechData

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Animelia.AnimeliaRecruitmendCondition
import com.mygdx.game.Animelia.FriendlyAnimeliaInCity
import com.mygdx.game.GameObjects.Structures.Railway.Railway
import com.mygdx.game.Inventory.RailwayTransportData
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.UI.Conversation.Conversation
import com.mygdx.game.generalSaveState
import com.mygdx.game.plus

class FireHippo(gameObjectData: GameObjectData, cityPositionEntityId: EntityRefData) : FriendlyAnimeliaInWorld(gameObjectData, cityPositionEntityId) {
    override val animeliaEntity = ANIMELIA_ENTITY.FireHippo

    val speech1 = SpeechData("Me", "Do you wanna come to the city?")
    val speech2 = SpeechData("FireHippo", "I'm a railway constructor")
    val speech3 = SpeechData("FireHippo", "There used to be railways acros the entire kingdom")
    val speech4 = SpeechData("FireHippo", "When the clones attacked, the connections were destroyed")
    val speech5 = SpeechData("Me", "Thanks for the history lesson. Can you answer my question?")
    val speech6 = SpeechData("FireHippo", "Jeez...If you prove yourself by fixing 2 railways, I will come")
    val speech7 = SpeechData("Me", "Alright, i'll do that")

    override var speeches = listOf(speech1, speech2, speech3,speech4,speech5,speech6,speech7)

    val citySpeech1 = SpeechData("Fire Hippo", "You did it! You fixed 2 railways")
    val citySpeech2 = SpeechData("Fire Hippo", "I will come to the city!")

    override val goingToCitySpeech = listOf(citySpeech1,citySpeech2)
    init {
        animeliaRecruitmentConditions.add(RailwaysFixedRecruitment())
    }

    override fun goingToCityAction() {
        super.goingToCityAction()
        generalSaveState.inventory.railwayConnections.add(RailwayTransportData("World1",this.cityPosition.x + 128, this.cityPosition.y - 64))
        generalSaveState.updateSaveState()
    }
}

class FireHippoInCity(gameObjectData: GameObjectData): FriendlyAnimeliaInCity(gameObjectData){
    override val animeliaEntity = ANIMELIA_ENTITY.FireHippo
    val inCitySpeechOne = SpeechData("Fire Armadillo", "Its good to be back!")
    val inCitySpeechTwo = SpeechData("Fire Armadillo", "I build a training statue")
    val inCitySpeechThree = SpeechData("Fire Armadillo", "Speak to me for training information")

    override val inCitySpeeches = listOf(inCitySpeechOne, inCitySpeechTwo, inCitySpeechThree)


    val sone = SpeechData("Me", "Can you tell me about Stats?")
    val stwo = SpeechData("Fire Armadillo", "Yes! There are four different stats")
    val sthree= SpeechData("Fire Armadillo", "Offence affects how much damage you do")
    val sfour = SpeechData("Fire Armadillo", "Defence affects how much damage you take")
    val ssix = SpeechData("Fire Armadillo", "Intelligence affects how many abilities you can use")
    val statsConversation = Conversation(listOf(sone, stwo,sthree, sfour, ssix))

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
        val railWay = Railway(GameObjectData(x = this.x.toInt() + this.width.toInt(), y=this.y.toInt(), width =128, height = 32))
        val firstArea = AreaManager.getArea("World1")
        railWay.setPosition(this.currentPosition() + Vector2(0f,-64f))
        railWay.initObject()
        railWay.brokenRailway.fix()
        firstArea.gameObjects.add(railWay)
    }

}

class RailwaysFixedRecruitment(): AnimeliaRecruitmendCondition{
    override fun isConditionFulfilled(): Boolean {
        return SignalManager.pastSignals.filter { it is RailwayFixedSignal }.size >= 2
    }
}