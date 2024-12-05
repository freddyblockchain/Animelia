package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import MetalBirdTalkingSignal
import RemoveObjectSignal
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.AnimeliaRecruitmendCondition
import com.mygdx.game.Animelia.FriendlyAnimeliaInCity
import com.mygdx.game.Animelia.FriendlyAnimeliaInWorld
import com.mygdx.game.EntityRefData
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.UI.Conversation.Conversation
import com.mygdx.game.UI.Conversation.SpeechData

enum class MetalBirdPos{ICE, FIRE, CANYON, SWAMP}


class MetalBird(gameObjectData: GameObjectData, cityPosEntityId: EntityRefData, val customData: List<EntityRefData>) : FriendlyAnimeliaInWorld(gameObjectData,
    cityPosEntityId
) {
    override val animeliaEntity = ANIMELIA_ENTITY.MetalBird
    val speech1 = SpeechData("MetalBird", "Uuh it sure is cold in here")
    val speech2 = SpeechData("MetalBird", "I am exploring the world  to make a map out of it!")
    val speech3 = SpeechData("Me", "Oh really? Why? ")
    val speech4 = SpeechData("MetalBird", "When the clones attacked, they seem to come out of nowhere")
    val speech5 = SpeechData("MetalBird", "With my map, maybe we can see them coming, if they ever attack again!")
    val speech6 = SpeechData("Me", "I hope you will succeed!")
    val speech7 = SpeechData("MetalBird", "Thank you. I am done with this area.")
    val speech8 = SpeechData("MetalBird", "I am going to map out a hotter area next...")

    val fireSpeech1 = SpeechData("MetalBird", "FireSpeech")
    val canyonSpeech1 = SpeechData("MetalBird", "CanyonSpeech")
    val swampSpeech1 = SpeechData("MetalBird", "SwampSpeech")

    override val goingToCitySpeech = listOf<SpeechData>(SpeechData("MetalBird", "I will join the city!"))
    override var speeches = listOf<SpeechData>(speech1, speech2, speech3, speech4, speech5,speech6, speech7, speech8)

    val positionList = mutableListOf<AnimeliaPosition>()

    var metalBirdPos = MetalBirdPos.ICE

    fun getMetalBirdAreaIdentifier(): String{
        return when(metalBirdPos){
            MetalBirdPos.ICE -> "World3"
            MetalBirdPos.FIRE -> "World2"
            MetalBirdPos.CANYON -> "Swamp0"
            MetalBirdPos.SWAMP -> "World3"
        }
    }

    override fun initObject() {
        super.initObject()

        customData.forEach {
            val goToPosition = AreaManager.getObjectWithIid(
                it.entityIid,
                it.levelIid
            ) as AnimeliaPosition
            positionList.add(goToPosition)
        }
        this.animeliaRecruitmentConditions.add(MetalBirdRecruitment(this))
    }

    override fun afterSpeechAction() {
        super.afterSpeechAction()
        SignalManager.emitSignal(RemoveObjectSignal(this.gameObjectIid))
        SignalManager.emitSignal(MetalBirdTalkingSignal(this.gameObjectIid, this.levelId, metalBirdPos), areaIdentifier = getMetalBirdAreaIdentifier())
    }
}
class MetalBirdRecruitment(val metalBird: MetalBird): AnimeliaRecruitmendCondition {
    override fun isConditionFulfilled(): Boolean {
        return metalBird.metalBirdPos == MetalBirdPos.SWAMP
    }
}

class MetalBirdInCity(gameObjectData: GameObjectData): FriendlyAnimeliaInCity(gameObjectData){
    override val animeliaEntity = ANIMELIA_ENTITY.MetalBird

    val inCitySpeechOne = SpeechData("Metal Bird", "Here is the Map!")

    override val inCitySpeeches = listOf(inCitySpeechOne)

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