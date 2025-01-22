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

    override fun goingToCitySignals() {
        super.goingToCitySignals()
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

    val bone = SpeechData("Me", "Can you explain about books?")
    val bbone = SpeechData("Ice Penguin", "Certainly! I oversaw the library in this city before the clones invaded")
    val btwo = SpeechData("Ice Penguin", "When they did, all the books were scattered across the kingdom")
    val bthree = SpeechData("Ice Penguin", "Each book contains the encyclopedic knowledge of an animelia")
    val bfour = SpeechData("Ice Penguin", "I want you to find these books in the world, so my library can be restored")

    val bookConversation = Conversation(listOf( bone,bbone, btwo,bthree, bfour))

    val cone = SpeechData("Me", "Can you explain about anivolution conditions!")
    val ctwo = SpeechData("Ice Penguin", "Certainly! You can view the conditions to anivolve in the library")
    val cthree = SpeechData("Ice Penguin", "The condition will only be visible if you have the corresponding book")
    val cfour = SpeechData("Me", "Can i anivolve to any animelia, that fulfills its condition?")
    val cfive = SpeechData("Ice Penguin", "No, there must also be a direct line between the two animelias")
    val csix = SpeechData("Ice Penguin", "Check the library for anivolution information about each animelia")

    val conditionConversation = Conversation(listOf(cone, ctwo, cthree, cfour, cfive, csix))

    override val conversationOptions = mapOf("Books" to bookConversation, "Conditions" to conditionConversation)

    override fun recruitmentAction() {
    }

}