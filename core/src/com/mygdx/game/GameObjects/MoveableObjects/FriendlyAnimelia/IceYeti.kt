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
    val speech1 = SpeechData("IceYeti", "Oh my, its so so cold here!")
    val speech2 = SpeechData("Me", "Do you want to come to the city then?")
    val speech3 = SpeechData("IceYeti", "I can't think straight when its this cold!")
    val speech4 = SpeechData("IceYeti", "Can you find a way to heat things up in here?")
    val speech5 = SpeechData("Me", "Okay, i'll try")

    val gspeech1 = SpeechData("IceYeti", "Aah, much better. You were saying?")
    val gspeech2 = SpeechData("Me", "Do you wanna come to the city?")
    val gspeech3 = SpeechData("IceYeti", "Sure thing! See you there")

    override val goingToCitySpeech: List<SpeechData> = listOf(gspeech1,gspeech2,gspeech3)

    override var speeches = listOf(speech1,speech2,speech3,speech4,speech5)

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

    val inCitySpeechOne = SpeechData("IceYeti", "Its good to be back!")
    val inCitySpeechTwo = SpeechData("IceYeti", "I can give you info on Animelia that you haven't recruited yet!")
    val inCitySpeechThree = SpeechData("IceYeti", "Talk to me again for this information")

    override val inCitySpeeches = listOf(inCitySpeechOne, inCitySpeechTwo, inCitySpeechThree)

    val armadillo1 = SpeechData("IceYeti", "I hear fire armadillo is somewhere in the fire lands")
    val armadillo2 = SpeechData("IceYeti", "If you defeat enough clones, he will join the city")
    val armadillo = Conversation(listOf(armadillo1,armadillo2))

    val firehippo1 = SpeechData("IceYeti", "I hear fire hippo is deep in the fire lands")
    val firehippo2 = SpeechData("IceYeti", "I hear he is facinated by railways, so try unlocking as many of those as you can")
    val firehippo  = Conversation(listOf(firehippo1,firehippo2))

    val penguin1 = SpeechData("IceYeti", "I hear ice penguin is somewhere close to this city!")
    val penguin2 = SpeechData("IceYeti", "ice penguin is all about books, so try to collect as many of those as you can")
    val penguin = Conversation(listOf(penguin1,penguin2))

    val bird1 = SpeechData("IceYeti", "I hear bird is somewhere close to this city!")
    val bird2 = SpeechData("IceYeti", "Bird is bad at directions, so try to help him, when you find him!")
    val bird = Conversation(listOf(bird1,bird2))

    val icebird1 = SpeechData("IceYeti", "As the warden of ice, ice bird resides over the ice sanctuary in the ice lands")
    val icebird2 = SpeechData("IceYeti", "Survive the challenges of the sanctuary, and you should be able to impress ice bird")
    val icebird = Conversation(listOf(icebird1,icebird2))


    val metalbird1 = SpeechData("IceYeti", "Metal bird is drawing a map of the world!")
    val metalbird2 = SpeechData("IceYeti", "Follow it around the world to track its progress!")
    val metalbird3 = SpeechData("IceYeti", "I hear Metal bird starts their journey somewhere in the ice lands...")
    val metalbird = Conversation(listOf(metalbird1,metalbird2,metalbird3))

    val guardfrog1 = SpeechData("IceYeti", "Guard frog is somewhere in the forest")
    val guardfrog2= SpeechData("IceYeti", "I hear it is guarding the kings throne room day in and day out with no rest and sleep")
    val guardfrog3 = SpeechData("IceYeti", "Maybe there is a sound attack you can use, to make guard frog tired?")
    val guardfrog = Conversation(listOf(guardfrog1,guardfrog2,guardfrog3))

    val kingfrog1 = SpeechData("IceYeti", "King frog is somewhere in the forest")
    val kingfrog2= SpeechData("IceYeti", "I hear it is not calling itself king frog anymore, but something else")
    val kingfrog3 = SpeechData("IceYeti", "Regardless if you confront king frog with its crown, you will be on the right path")
    val kingfrog = Conversation(listOf(kingfrog1,kingfrog2,kingfrog3))

    val frog1 = SpeechData("IceYeti", "Frog is somewhere in the canyon")
    val frog2= SpeechData("IceYeti", "I hear frog prefers lush and green areas")
    val frog3 = SpeechData("IceYeti", "Frog is fascinated by fruits, so collect 1 of every fruit, before talking to him")
    val frog = Conversation(listOf(frog1,frog2,frog3))

    val soundbat1 = SpeechData("IceYeti", "As the warden of air, soundbat resides over in the abondoned house in the canyon")
    val soundbat2= SpeechData("IceYeti", "Survive the challenges of the abondoned house, and you should be able to impress sound bat")
    val soundbat = Conversation(listOf(soundbat1, soundbat2))

    val firelion1 = SpeechData("IceYeti", "As the warden of fire, firelion resides over in the vulcano in the firelands")
    val firelion2= SpeechData("IceYeti", "Survive the challenges of the vulcano, and you should be able to impress fire lion")
    val firelion = Conversation(listOf(firelion1, firelion2))

    override val conversationOptions = getConversations()

    private fun getConversations(): Map<String, Conversation>{
        val conversationMap = mutableMapOf<String, Conversation>()

        val recruitSignals = SignalManager.pastSignals.filterIsInstance<AnimeliaRecruitedSignal>()
        if(recruitSignals.none { it.animeliaEntity == ANIMELIA_ENTITY.FireArmadillo }){
            conversationMap["Fire Armadillo"] = armadillo
        }

        if(recruitSignals.none { it.animeliaEntity == ANIMELIA_ENTITY.IcePenguin }){
            conversationMap["Ice Penguin"] = penguin
        }
        if(recruitSignals.none { it.animeliaEntity == ANIMELIA_ENTITY.Bird }){
            conversationMap["Bird"] = bird
        }
        if(recruitSignals.none { it.animeliaEntity == ANIMELIA_ENTITY.Frog }){
            conversationMap["Frog"] = frog
        }

        if(recruitSignals.none { it.animeliaEntity == ANIMELIA_ENTITY.IceBird }){
            conversationMap["Ice Bird"] = icebird
        }
        if(recruitSignals.none { it.animeliaEntity == ANIMELIA_ENTITY.FireLion}){
            conversationMap["Fire Lion"] = firelion
        }
        if(recruitSignals.none { it.animeliaEntity == ANIMELIA_ENTITY.SoundBat }){
            conversationMap["Sound Bat"] = soundbat
        }
        if(recruitSignals.none { it.animeliaEntity == ANIMELIA_ENTITY.FireHippo }){
            conversationMap["Fire Hippo"] = firehippo
        }
        if(recruitSignals.none { it.animeliaEntity == ANIMELIA_ENTITY.MetalBird }){
            conversationMap["Metal Bird"] = metalbird
        }
        if(recruitSignals.none { it.animeliaEntity == ANIMELIA_ENTITY.GuardFrog }){
            conversationMap["Guard Frog"] = guardfrog
        }
        if(recruitSignals.none { it.animeliaEntity == ANIMELIA_ENTITY.KingFrog }){
            conversationMap["King Frog"] = kingfrog
        }

        return conversationMap

    }

    override fun recruitmentAction() {

    }

}