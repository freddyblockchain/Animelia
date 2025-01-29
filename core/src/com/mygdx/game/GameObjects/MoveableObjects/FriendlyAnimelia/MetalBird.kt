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

    val firespeech1 =  SpeechData("MetalBird", "This area is so hot! I miss the cold now")
    val firespeech2 =  SpeechData("MetalBird", "I finished everything for this area")
    val firespeech3 =  SpeechData("MetalBird", "I'll head to my home area of the canyon now")
    val firespeech4 =  SpeechData("MetalBird", "I hear you'll need a flying ability to go over there")
    val firespeech5 =  SpeechData("MetalBird", "I'm told there should be one nearby")
    val firespeech6 =  SpeechData("MetalBird", "Anyways, i'm off. See you in the canyon!")

    val canyonSpeech1 = SpeechData("MetalBird", "You came! Things are going well with the map")
    val canyonSpeech2 = SpeechData("MetalBird", "I'm only missing the northeast area with the abandoned building in this area")
    val canyonSpeech3 = SpeechData("MetalBird", "Afterwards i'm heading to the last area: The forest")

    val swampSpeech1 = SpeechData("MetalBird", "Oh, here you are again!")
    val swampSpeech2 = SpeechData("MetalBird", "Great timing! I've finished my map")
    val swampSpeech3 = SpeechData("MetalBird", "I wonder what to do now.. ")
    val swampSpeech4 = SpeechData("Me", "You should join me in the city!")
    val swampSpeech5 = SpeechData("Me", "Okay, don't mind if i do!")

    override val goingToCitySpeech = listOf<SpeechData>(swampSpeech1, swampSpeech2, swampSpeech3, swampSpeech4, swampSpeech5)
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

    val inCitySpeechOne = SpeechData("Metal Bird", "It sure is lively here!")
    val inCitySpeechTwo = SpeechData("Metal Bird", "Say do you want a copy of my map?")
    val inCitySpeechThree = SpeechData("Metal Bird", "Its only to be the only one having this masterpiece")
    val inCitySpeechFour = SpeechData("Me", "Okay, i'll put it to good use")
    val inCitySpeechFive = SpeechData("Me", "Perfect! You open and close it by pressing M")

    override val inCitySpeeches = listOf(inCitySpeechOne, inCitySpeechTwo, inCitySpeechThree, inCitySpeechFour, inCitySpeechFive)

    val mone = SpeechData("Me", "How do i use the map?")
    val mtow = SpeechData("Metal Bird", "Perfect! You open and close it by pressing M")
    val mthree = SpeechData("Me", "What does the map show?")
    val mfour = SpeechData("Metal Bird", "It shows the areas, that you can go to aswell as yourself")

    val mapConversation = Conversation(listOf(mone, mtow, mthree, mfour))

    override val conversationOptions = mapOf("Map" to mapConversation)

    override fun recruitmentAction() {

    }

}