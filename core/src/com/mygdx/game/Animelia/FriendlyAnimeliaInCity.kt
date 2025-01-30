package com.mygdx.game.Animelia

import AnimeliaCityTalkedWithSignal
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Collition.InputCollision
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameModes.TalkMode
import com.mygdx.game.GameModes.UIMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.GameObject.DefaultRotationalObject
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.GameObject.RotationalObject
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.UI.Conversation.Conversation
import com.mygdx.game.UI.Conversation.SpeechData
import com.mygdx.game.UI.Scene2d.Screens.PickConversationScreen

abstract class FriendlyAnimeliaInCity(gameObjectData: GameObjectData) :
    GameObject(gameObjectData, Vector2(32f, 32f)), RotationalObject by DefaultRotationalObject() {
    abstract val animeliaEntity: ANIMELIA_ENTITY
    val animeliaData by lazy { getAnimeliaData(animeliaEntity) }
    abstract val inCitySpeeches: List<SpeechData>

    open val conversationOptions: Map<String, Conversation> = mapOf()
    abstract fun recruitmentAction()

    override val collision = FriendlyAnimeliaInCityCollision(this)
    override val layer = Layer.ONGROUND

    override fun initObject() {
        this.sprite.texture = DefaultTextureHandler.getTexture(animeliaData.textureName)
    }
}

class FriendlyAnimeliaInCityCollision(val friendlyAnimeliaInCity: FriendlyAnimeliaInCity) : InputCollision() {
    override val keyCode = Input.Keys.ENTER
    override val insideText = "TALK"

    override fun collisionHappened(collidedObject: GameObject) {
        val talkedWithAnimelia =
            SignalManager.pastSignals.filter { it.signaltype == SIGNALTYPE.ANIMELIA_CITY_TALKED_WITH }
                .map { it as AnimeliaCityTalkedWithSignal }
                .firstOrNull { it.animeliaEntity == this.friendlyAnimeliaInCity.animeliaEntity }

        if (talkedWithAnimelia != null) {
            changeMode(UIMode(PickConversationScreen(mainMode, this.friendlyAnimeliaInCity.conversationOptions)))
        } else {
            changeMode(TalkMode(Conversation(friendlyAnimeliaInCity.inCitySpeeches), mainMode){SignalManager.emitSignal(AnimeliaCityTalkedWithSignal(this.friendlyAnimeliaInCity.animeliaEntity))})
        }
    }


}