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
    override val animeliaEntity = ANIMELIA_ENTITY.FireArmadillo

    val speech1 = SpeechData("Me", "Do you wanna come to the city?")
    val speech2 = SpeechData("Fire Armadillo", "hmm, prove your worth by defeating 3 animelia clones")
    val speech3 = SpeechData("Me", "Alright, i'll do that")

    override var speeches = listOf(speech1, speech2, speech3)

    val citySpeech1 = SpeechData("Fire Armadillo", "So, you actually defeated 3 clones")
    val citySpeech2 = SpeechData("Fire Armadillo", "I have to admit. I thought you were a faker")
    val citySpeech3 = SpeechData("Fire Armadillo", "But you have proven yourself. Alright, i'll come back to the city.")

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

    override val inCitySpeeches = listOf<SpeechData>()

    override val conversationOptions = mapOf<String,Conversation>()

    override fun recruitmentAction() {

    }

}

class FrogRecruitment(): AnimeliaRecruitmendCondition {
    override fun isConditionFulfilled(): Boolean {
        return true
    }
}