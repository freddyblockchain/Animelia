package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import AnimeliaRecruitedSignal
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.AnimeliaRecruitmendCondition
import com.mygdx.game.Animelia.FriendlyAnimeliaInCity
import com.mygdx.game.Animelia.FriendlyAnimeliaInWorld
import com.mygdx.game.EntityRefData
import com.mygdx.game.GameObjectData
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.UI.Conversation.Conversation
import com.mygdx.game.UI.Conversation.SpeechData

class FireLion(gameObjectData: GameObjectData, cityPosEntityId: EntityRefData) : FriendlyAnimeliaInWorld(gameObjectData,
    cityPosEntityId
) {
    override val animeliaEntity = ANIMELIA_ENTITY.FireLion
    val speech1 = SpeechData("FireLion", "I am the warden of fire")
    val speech2 = SpeechData("FireLion", "You have proven yourself worthy by reaching me")
    val speech3 = SpeechData("Me", "Do you want to come to the city?")
    val speech4 = SpeechData("FireLion", "Yes, we must fight back to reclaim our kingdom")
    val speech5 = SpeechData("FireLion", "Meet me in the city. I will make you stronger")

    override val goingToCitySpeech = listOf(speech1, speech2, speech3, speech4, speech5)
    override var speeches = listOf<SpeechData>()
    init {
        this.animeliaRecruitmentConditions.add(IceBirdRecruitment())
    }

    override fun goingToCityAction() {
        super.goingToCityAction()
        SignalManager.emitSignal(
            AnimeliaRecruitedSignal(animeliaEntity, cityPosition.x, cityPosition.y),
            areaIdentifier = "World3"
        )
    }
}
class FireLionRecruitment(): AnimeliaRecruitmendCondition {
    override fun isConditionFulfilled(): Boolean {
        return true
    }
}

class FireLionInCity(gameObjectData: GameObjectData): FriendlyAnimeliaInCity(gameObjectData){
    override val animeliaEntity = ANIMELIA_ENTITY.FireLion

    val inCitySpeechOne = SpeechData("FireLion", "Ah, it feels like ages since i was here")
    val inCitySpeechTwo = SpeechData("FireLion", "Time in the Ice Castle has went by slowly,,")
    val inCitySpeechThree = SpeechData("Me", "Did you say you could make me stronger?")
    val inCitySpeechFour = SpeechData("FireLion", "Yes.. Pleace accept the fire heart")
    val inCitySpeechFive = SpeechData("FireLion", "This artifact will let you keep your warmth even in cold regions")
    val inCitySpeechSix = SpeechData("FireLion", "It is raw heat instilled into you")

    override val inCitySpeeches = listOf(inCitySpeechOne, inCitySpeechTwo, inCitySpeechThree, inCitySpeechFour, inCitySpeechFive, inCitySpeechSix)

    val bone = SpeechData("Me", "How do I go to the canyon?")
    val btwo = SpeechData("Fire Lion", "I hear the entrance is on the east side of the firelands")
    val bbthree = SpeechData("Fire Lion", "I also heard you need flying for that!")
    val bbfour = SpeechData("Fire Lion", "Lastly I heard that a flying egg is hidden somewhere in the ice lands")
    val bbfive = SpeechData("Fire Lion", "With your fire heart, any animelia will be able to access the ice lands")

    val bookConversation = Conversation(listOf(bone, btwo, bbthree, bbfour, bbfive))

    override val conversationOptions = mapOf("Canyon" to bookConversation)

    override fun recruitmentAction() {
    }

}