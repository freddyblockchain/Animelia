package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import FireplaceLitSignal
import com.mygdx.game.GameObjectData
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.AnimeliaRecruitmendCondition
import com.mygdx.game.Animelia.FriendlyAnimeliaInWorld
import com.mygdx.game.EntityRefData
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.UI.Conversation.SpeechData

class IceYeti(gameObjectData: GameObjectData, cityPosEntityId: EntityRefData) : FriendlyAnimeliaInWorld(gameObjectData,
    cityPosEntityId
) {
    override val animeliaEntity = ANIMELIA_ENTITY.IceYeti
    val speech1 = SpeechData("", "Oh my, its so so cold here!")

    val gspeech1 = SpeechData("", "Yes, i will go to the city!")

    override val goingToCitySpeech: List<SpeechData> = listOf(gspeech1)

    override var speeches = listOf(speech1)

    init {
        this.animeliaRecruitmentConditions.add(IceYetiRecruitment())
    }
}
class IceYetiRecruitment(): AnimeliaRecruitmendCondition {
    override fun isConditionFulfilled(): Boolean {
        return SignalManager.pastSignals.filterIsInstance<FireplaceLitSignal>().isNotEmpty()
    }
}