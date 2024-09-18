package com.mygdx.game.Animelia

import AnimeliaCityTalkedWithSignal
import AnimeliaRecruitedSignal
import RemoveObjectSignal
import SIGNALTYPE
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.Sprite
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

interface AnimeliaRecruitmendCondition {
    fun isConditionFulfilled(): Boolean
}

abstract class FriendlyAnimelia(gameObjectData: GameObjectData, private val cityPosEntityRefData: EntityRefData) :
    GameObject(gameObjectData, Vector2(32f, 32f)), RotationalObject by DefaultRotationalObject() {
    abstract val animeliaEntity: ANIMELIA_ENTITY
    val animeliaData by lazy { getAnimeliaData(animeliaEntity) }
    val animeliaRecruitmentConditions = mutableListOf<AnimeliaRecruitmendCondition>()

    abstract val speeches: List<SpeechData>
    open val goingToCitySpeech = listOf<SpeechData>()
    abstract val inCitySpeeches: List<SpeechData>

    open val conversationOptions: Map<String, Conversation> = mapOf()

    val talkSpeechBubble = Sprite(DefaultTextureHandler.getTexture("animeliaTalk.png"))
    abstract fun recruitmentAction()

    var isRecruited = false


    override val collision = FriendlyAnimeliaCollision(this)

    lateinit var cityPosition: AnimeliaPosition

    override val layer = Layer.ONGROUND

    override fun initObject() {
        cityPosition = AreaManager.getObjectWithIid(
            cityPosEntityRefData.entityIid,
            cityPosEntityRefData.levelIid
        ) as AnimeliaPosition
        this.sprite.texture = animeliaData.gameTexture
    }

    fun isConditionsFulfilled(): Boolean {
        return animeliaRecruitmentConditions.all { it.isConditionFulfilled() }
    }

    override fun render(batch: SpriteBatch) {
        super.render(batch)
        if (this.isConditionsFulfilled() && !isRecruited) {
            val pos = this.currentMiddle - Vector2(8f, -8f)
            talkSpeechBubble.setPosition(pos.x, pos.y)
            talkSpeechBubble.draw(batch)
        }
    }
}

class FriendlyAnimeliaCollision(val friendlyAnimelia: FriendlyAnimelia) : InputCollision() {
    override val keyCode = Input.Keys.ENTER
    override val insideText = "TALK"

    override fun collisionHappened(collidedObject: GameObject) {


        if (friendlyAnimelia.isRecruited) {
            val talkedWithAnimelia =
                SignalManager.pastSignals.filter { it.signaltype == SIGNALTYPE.ANIMELIA_CITY_TALKED_WITH }
                    .map { it as AnimeliaCityTalkedWithSignal }
                    .firstOrNull { it.animeliaEntity == this.friendlyAnimelia.animeliaEntity }

            if(talkedWithAnimelia != null){
                changeMode(UIMode(PickConversationScreen(mainMode, this.friendlyAnimelia.conversationOptions)))
            }else {
                changeMode(TalkMode(Conversation(friendlyAnimelia.inCitySpeeches), mainMode))
                SignalManager.emitSignal(AnimeliaCityTalkedWithSignal(this.friendlyAnimelia.animeliaEntity))
            }
        } else {
            if (friendlyAnimelia.isConditionsFulfilled()) {
                if (friendlyAnimelia.goingToCitySpeech.size > 0) {
                    changeMode(TalkMode(Conversation(friendlyAnimelia.goingToCitySpeech), mainMode))
                }
                SignalManager.emitSignal(RemoveObjectSignal(this.friendlyAnimelia.gameObjectIid))
                SignalManager.emitSignal(
                    AnimeliaRecruitedSignal(this.friendlyAnimelia.animeliaEntity),
                    areaIdentifier = "World1"
                )
            } else {
                changeMode(TalkMode(Conversation(friendlyAnimelia.speeches), mainMode))
                println("Not Fulfilled Yet")
            }
        }
    }


}
