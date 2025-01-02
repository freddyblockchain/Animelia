package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import AnimeliaRecruitedSignal
import ChangeVisibleSignal
import FireplaceLitSignal
import RemoveObjectSignal
import com.mygdx.game.GameObjectData
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.AnimeliaRecruitmendCondition
import com.mygdx.game.Animelia.FriendlyAnimeliaInCity
import com.mygdx.game.Animelia.FriendlyAnimeliaInWorld
import com.mygdx.game.EntityRefData
import com.mygdx.game.GameObjects.Structures.House
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.UI.Conversation.Conversation
import com.mygdx.game.UI.Conversation.SpeechData

class IceYeti(gameObjectData: GameObjectData, cityPosEntityId: EntityRefData,val customData: List<EntityRefData>) : FriendlyAnimeliaInWorld(gameObjectData,
    cityPosEntityId
) {
    override val animeliaEntity = ANIMELIA_ENTITY.IceYeti
    val speech1 = SpeechData("", "Oh my, its so so cold here!")

    val gspeech1 = SpeechData("", "Yes, i will go to the city!")

    override val goingToCitySpeech: List<SpeechData> = listOf(gspeech1)

    override var speeches = listOf(speech1)

    lateinit var house: House
    lateinit var house2: House

    init {
        this.animeliaRecruitmentConditions.add(IceYetiRecruitment())
    }

    override fun initObject() {
        super.initObject()


        val entityRef = customData.first()
        house = AreaManager.getObjectWithIid(
            entityRef.entityIid,
            entityRef.levelIid
        ) as House

        house.initObject()

        val entityRef2 = customData[1]
        house2 = AreaManager.getObjectWithIid(
            entityRef2.entityIid,
            entityRef2.levelIid
        ) as House

        house2.initObject()
    }


    override fun goingToCitySignals() {
        SignalManager.emitSignal(RemoveObjectSignal(gameObjectIid))
        SignalManager.emitSignal(RemoveObjectSignal(house.gameObjectIid), areaIdentifier = "World4")
        SignalManager.emitSignal(ChangeVisibleSignal(house2.gameObjectIid, house2.levelId), areaIdentifier = "World1")
        SignalManager.emitSignal(RemoveObjectSignal(house.door.gameObjectIid), areaIdentifier = "World4")
        SignalManager.emitSignal(
            AnimeliaRecruitedSignal(animeliaEntity, cityPosition.x, cityPosition.y),
            areaIdentifier = "Iglo_City"
        )
    }
}
class IceYetiRecruitment(): AnimeliaRecruitmendCondition {
    override fun isConditionFulfilled(): Boolean {
        return SignalManager.pastSignals.filterIsInstance<FireplaceLitSignal>().isNotEmpty()
    }
}

class IceYetiInCity(gameObjectData: GameObjectData): FriendlyAnimeliaInCity(gameObjectData){
    override val animeliaEntity = ANIMELIA_ENTITY.IceYeti

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