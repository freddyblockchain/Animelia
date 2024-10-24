package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.FriendlyAnimelia
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.EntityRefData
import com.mygdx.game.UI.Conversation.SpeechData

class FireHippo(gameObjectData: GameObjectData, cityPosEntityId: EntityRefData) : FriendlyAnimelia(gameObjectData,
    cityPosEntityId
) {
    override val animeliaEntity = ANIMELIA_ENTITY.FireHippo

    override val speeches: List<SpeechData> = listOf()
    override val inCitySpeeches: List<SpeechData>
        get() = TODO("Not yet implemented")

    override fun recruitmentAction() {
        TODO("Not yet implemented")
    }

    override val texture = DefaultTextureHandler.getTexture("Sensor.png")
    override val layer: Layer
        get() = TODO("Not yet implemented")
}