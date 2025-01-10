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

    override val inCitySpeeches = listOf<SpeechData>()

    override val conversationOptions = mapOf<String, Conversation>()

    override fun recruitmentAction() {

    }

}