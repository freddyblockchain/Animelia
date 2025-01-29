package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import AnimeliaRecruitedSignal
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.UI.Conversation.Conversation

import com.mygdx.game.GameObjectData
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.AnimeliaRecruitmendCondition
import com.mygdx.game.Animelia.FriendlyAnimeliaInCity
import com.mygdx.game.Animelia.FriendlyAnimeliaInWorld
import com.mygdx.game.EntityRefData
import com.mygdx.game.GameObjects.Structures.Library
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.UI.Conversation.SpeechData
import com.mygdx.game.plus

class IceBird(gameObjectData: GameObjectData, cityPosEntityId: EntityRefData) : FriendlyAnimeliaInWorld(gameObjectData,
    cityPosEntityId
) {
    override val animeliaEntity = ANIMELIA_ENTITY.IceBird
    val speech1 = SpeechData("IceBird", "I am the warden of ice")
    val speech2 = SpeechData("IceBird", "You have proven yourself worthy by reaching me")
    val speech3 = SpeechData("Me", "Do you want to come to the city?")
    val speech4 = SpeechData("IceBird", "Yes, we must fight back to reclaim our kingdom")
    val speech5 = SpeechData("IceBird", "Meet me in the city. I will make you stronger")

    override val goingToCitySpeech = listOf(speech1, speech2, speech3, speech4, speech5)
    override var speeches = listOf<SpeechData>()
    init {
        this.animeliaRecruitmentConditions.add(IceBirdRecruitment())
    }

    override fun goingToCitySignals() {
        super.goingToCitySignals()
        SignalManager.emitSignal(
            AnimeliaRecruitedSignal(animeliaEntity, cityPosition.x, cityPosition.y),
            areaIdentifier = "World4"
        )
    }
}
class IceBirdRecruitment(): AnimeliaRecruitmendCondition {
    override fun isConditionFulfilled(): Boolean {
        return true
    }
}

class IceBirdInCity(gameObjectData: GameObjectData): FriendlyAnimeliaInCity(gameObjectData){
    override val animeliaEntity = ANIMELIA_ENTITY.IceBird

    val inCitySpeechOne = SpeechData("IceBird", "Ah, it feels like ages since i was here")
    val inCitySpeechTwo = SpeechData("IceBird", "Time in the Ice Castle has went by slowly,,")
    val inCitySpeechThree = SpeechData("Me", "Did you say you could make me stronger?")
    val inCitySpeechFour = SpeechData("IceBird", "Yes.. Pleace accept the frozen heart")
    val inCitySpeechFive = SpeechData("IceBird", "This artifact will let you keep your cool at all times in hot regions")
    val inCitySpeechSix = SpeechData("IceBird", "It is the purest and cleanest form of ice")

    override val inCitySpeeches = listOf(inCitySpeechOne, inCitySpeechTwo, inCitySpeechThree, inCitySpeechFour, inCitySpeechFive, inCitySpeechSix)

    val bone = SpeechData("Me", "What can i use the frozen heart for?")
    val bbone = SpeechData("IceBird", "It will shield you from damage when going into hot regions")
    val btwo = SpeechData("IceBird", "This will let you access hot regions with all animelia")
    val bthree = SpeechData("IceBird", "Maybe there is something you can do with fire as an ice animelia?")

    val bookConversation = Conversation(listOf( bone,bbone, btwo,bthree))

    override val conversationOptions = mapOf("Frozen Heart" to bookConversation)

    override fun recruitmentAction() {
    }

}