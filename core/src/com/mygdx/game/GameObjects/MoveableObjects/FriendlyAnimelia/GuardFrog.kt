package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import FireplaceLitSignal
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.GameObjectData
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.AnimeliaRecruitmendCondition
import com.mygdx.game.Animelia.FriendlyAnimeliaInWorld
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.EntityRefData
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.UI.Conversation.SpeechData
import com.mygdx.game.minus
import java.util.Vector

class GuardFrog(gameObjectData: GameObjectData, cityPosEntityId: EntityRefData) : FriendlyAnimeliaInWorld(gameObjectData,
    cityPosEntityId
) {
    override val animeliaEntity = ANIMELIA_ENTITY.GuardFrog
    val speech1 = SpeechData("", "Oh my, its so so cold here!")

    val gspeech1 = SpeechData("", "Yes, i will go to the city!")

    val spear = Sprite(DefaultTextureHandler.getTexture("spear.png"))

    override val goingToCitySpeech: List<SpeechData> = listOf(gspeech1)

    override var speeches = listOf(speech1)

    override fun initObject() {
        spear.setSize(8f,32f)
        spear.rotate(90f)
        val newPos = this.currentPosition() - Vector2(14f,-6f)
        spear.setPosition(newPos.x, newPos.y)

        super.initObject()
    }

    init {
        this.animeliaRecruitmentConditions.add(GuardFrogRecruitment())
    }

    override fun render(batch: SpriteBatch) {
        super.render(batch)
        spear.draw(batch)

    }
}
class GuardFrogRecruitment(): AnimeliaRecruitmendCondition {
    override fun isConditionFulfilled(): Boolean {
        return false
    }
}