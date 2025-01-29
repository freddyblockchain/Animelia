package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.AnimeliaRecruitmendCondition
import com.mygdx.game.Animelia.FriendlyAnimeliaInCity
import com.mygdx.game.Animelia.FriendlyAnimeliaInWorld
import com.mygdx.game.EntityRefData
import com.mygdx.game.GameObjectData
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.UI.Conversation.Conversation
import com.mygdx.game.UI.Conversation.SpeechData

class Bird(gameObjectData: GameObjectData, cityPositionEntityId: EntityRefData) : FriendlyAnimeliaInWorld(gameObjectData, cityPositionEntityId) {
    override val animeliaEntity = ANIMELIA_ENTITY.Bird

    override var speeches = listOf<SpeechData>()

    val citySpeech1 = SpeechData("Bird", "Hello! I seem to have become lost")
    val citySpeech2 = SpeechData("Me", "I see. Do you want to come to the city then?")
    val citySpeech3 = SpeechData("Bird", "Sure! If its safe now. How do I get there?")
    val citySpeech4 = SpeechData("Me", "Well from here, you go up and then to the left")
    val citySpeech5 = SpeechData("Bird", "Uuhm okay, so first up, and then what?")
    val citySpeech6 = SpeechData("Me", "Then you go left")
    val citySpeech7 = SpeechData("Bird", "Okay so I go left and then what?")
    val citySpeech8 = SpeechData("Me", "NO, YOU GO UP AND THEN LEFT")
    val citySpeech9 = SpeechData("Bird", "Right")



    override val goingToCitySpeech = listOf(citySpeech1,citySpeech2,citySpeech3, citySpeech4,citySpeech5,citySpeech6,citySpeech7,citySpeech8, citySpeech9)
}

class BirdInCity(gameObjectData: GameObjectData): FriendlyAnimeliaInCity(gameObjectData){
    override val animeliaEntity = ANIMELIA_ENTITY.Bird

    val inCitySpeechOne = SpeechData("Bird", "I finally found the city!")
    val inCitySpeechTwo  = SpeechData("Bird", "Your directions were bad, but I found it in the end!")
    val inCitySpeechThree = SpeechData("Me", "Oh, is that so?")
    val inCitySpeechFour = SpeechData("Bird", "Yep. I'll show you how its done")
    val inCitySpeechFive = SpeechData("Bird", "Talk to me again for directions")

    override val inCitySpeeches = listOf(inCitySpeechOne, inCitySpeechTwo, inCitySpeechThree, inCitySpeechFour, inCitySpeechFive)

    val bone = SpeechData("Me", "How do I go to the canyon?")
    val bTwo = SpeechData("Bird", "Well, its to the east")

    val canyonConversation = Conversation(listOf(bone, bTwo))


    val fone = SpeechData("Me", "How do I go to the fire lands?")
    val fTwo = SpeechData("Bird", "Well, its to the north")

    val fireConversation = Conversation(listOf(fone, fTwo))


    val ione = SpeechData("Me", "How do I go to the ice lands?")
    val iTwo = SpeechData("Bird", "Well, its to the west")

    val icelandsConversation = Conversation(listOf(ione, iTwo))


    val fione = SpeechData("Me", "How do I go to the forest?")
    val fiTwo = SpeechData("Bird", "Well, its to the south")

    val forestConversation = Conversation(listOf(fione, fiTwo))



    val mone = SpeechData("Me", "How do I go to the metal factory?")
    val mTwo = SpeechData("Bird", "Well, its to the west, and then to the north")

    val metalConversation = Conversation(listOf(mone, mTwo))


    override val conversationOptions = mapOf("Canyon" to canyonConversation, "Fire Lands" to fireConversation, "Ice Lands" to icelandsConversation, "Forest" to forestConversation, "Metal Factory" to metalConversation)


    override fun recruitmentAction() {

    }

}