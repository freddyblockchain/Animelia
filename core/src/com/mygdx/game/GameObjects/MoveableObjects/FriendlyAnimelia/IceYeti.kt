package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import com.mygdx.game.GameObjectData
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.AnimeliaRecruitmendCondition
import com.mygdx.game.Animelia.FriendlyAnimelia
import com.mygdx.game.EntityRefData
import com.mygdx.game.UI.Conversation.SpeechData

class IceYeti(gameObjectData: GameObjectData, cityPosEntityId: EntityRefData) : FriendlyAnimelia(gameObjectData,
    cityPosEntityId
) {
    override val animeliaEntity = ANIMELIA_ENTITY.IceYeti
    val speech1 = SpeechData("", "Thank you for playing the demo!")

    override val speeches = listOf(speech1)
    override val inCitySpeeches: List<SpeechData>
        get() = TODO("Not yet implemented")

    init {
        this.animeliaRecruitmentConditions.add(IceYetiRecruitment())
    }
    override fun recruitmentAction() {

    }
}
class IceYetiRecruitment(): AnimeliaRecruitmendCondition {
    override fun isConditionFulfilled(): Boolean {
        return false
    }
}