package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import ChangeLevelVisibleSignal
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.AnimeliaRecruitmendCondition
import com.mygdx.game.Animelia.FriendlyAnimeliaInCity
import com.mygdx.game.Animelia.FriendlyAnimeliaInWorld
import com.mygdx.game.EntityRefData
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.Structures.TrainingStation
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.PlayerStatus
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.UI.Conversation.Conversation
import com.mygdx.game.UI.Conversation.SpeechData
import com.mygdx.game.plus

class Frog(gameObjectData: GameObjectData, cityPositionEntityId: EntityRefData) : FriendlyAnimeliaInWorld(gameObjectData, cityPositionEntityId) {
    override val animeliaEntity = ANIMELIA_ENTITY.Frog

    val speech1 = SpeechData("Frog", "Why hello there!")
    val speech2 = SpeechData("Frog", "Welcome to my little oasis")
    val speech3 = SpeechData("Frog", "I'm facinated by fruits. The rare tropical fruit grows here")
    val speech4 = SpeechData("Me", "Do you want to come to the city?")
    val speech5 = SpeechData("Frog", "I will come to the city, if you bring me one of every fruit!")
    val speech6 = SpeechData("Frog", "So 1 fire fruit, 1 canyon fruit, 1 ice fruit, 1 forest fruit, and 1 tropical fruit")
    val speech7 = SpeechData("Me", "Alright, i'll do that")

    override var speeches = listOf(speech1, speech2, speech3, speech4, speech5, speech6, speech7)

    val citySpeech1 = SpeechData("Frog", "Oh wow! You actually did it.")
    val citySpeech2 = SpeechData("Frog", "I guess i'll have to leave my little paradise here...")
    val citySpeech3 = SpeechData("Me", "Yes, you said you would come. No backsies.")

    override val goingToCitySpeech = listOf(citySpeech1,citySpeech2,citySpeech3)

    override fun goingToCitySignals() {
        super.goingToCitySignals()
        SignalManager.emitSignal(ChangeLevelVisibleSignal(cityPosition.levelId))
    }
    init {
        animeliaRecruitmentConditions.add(FrogRecruitment())
    }
}

class FrogInCity(gameObjectData: GameObjectData): FriendlyAnimeliaInCity(gameObjectData){
    override val animeliaEntity = ANIMELIA_ENTITY.Frog

    val citySpeech1 = SpeechData("Frog", "I ended up making my own little oasis!")
    val citySpeech2 = SpeechData("Frog", "All sorts of fruits grow here.")
    val citySpeech3 = SpeechData("Frog", "So stuck up if you are running dry")
    val citySpeech4 = SpeechData("Me", "Thanks, appreciate it")

    override val inCitySpeeches = listOf<SpeechData>(citySpeech1, citySpeech2, citySpeech3, citySpeech4)

    override val conversationOptions = mapOf<String,Conversation>()

    override fun recruitmentAction() {

    }

}

class FrogRecruitment(): AnimeliaRecruitmendCondition {
    override fun isConditionFulfilled(): Boolean {
        return true
    }
}