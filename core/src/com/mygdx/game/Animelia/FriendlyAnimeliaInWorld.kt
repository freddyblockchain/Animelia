package com.mygdx.game.Animelia

import AnimeliaRecruitedSignal
import RemoveObjectSignal
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Collition.InputCollision
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameModes.TalkMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.GameObject.DefaultRotationalObject
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.GameObject.RotationalObject
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.UI.Conversation.Conversation
import com.mygdx.game.UI.Conversation.SpeechData

interface AnimeliaRecruitmendCondition {
    fun isConditionFulfilled(): Boolean
}

abstract class FriendlyAnimeliaInWorld(
    gameObjectData: GameObjectData,
    private val cityPosEntityRefData: EntityRefData
) :
    GameObject(gameObjectData, Vector2(32f, 32f)), RotationalObject by DefaultRotationalObject() {
    abstract val animeliaEntity: ANIMELIA_ENTITY
    val animeliaData by lazy { getAnimeliaData(animeliaEntity) }
    val animeliaRecruitmentConditions = mutableListOf<AnimeliaRecruitmendCondition>()

    abstract var speeches: List<SpeechData>
    open val goingToCitySpeech = listOf<SpeechData>()

    val talkSpeechBubble = Sprite(DefaultTextureHandler.getTexture("animeliaTalk.png"))


    override val collision = FriendlyAnimeliaInWorldCollision(this)

    lateinit var cityPosition: AnimeliaPosition

    override val layer = Layer.ONGROUND

    open fun afterSpeechAction(){

    }

    override fun initObject() {
        cityPosition = AreaManager.getObjectWithIid(
            cityPosEntityRefData.entityIid,
            cityPosEntityRefData.levelIid
        ) as AnimeliaPosition
        this.sprite.texture = DefaultTextureHandler.getTexture(animeliaData.textureName)
    }

    fun isConditionsFulfilled(): Boolean {
        return animeliaRecruitmentConditions.all { it.isConditionFulfilled() }
    }

    open fun goingToCityAction(){
        SignalManager.emitSignal(RemoveObjectSignal(gameObjectIid))
        SignalManager.emitSignal(
            AnimeliaRecruitedSignal(animeliaEntity, cityPosition.x, cityPosition.y),
            areaIdentifier = "World1"
        )
    }

    override fun render(batch: SpriteBatch) {
        super.render(batch)
        if (this.isConditionsFulfilled()) {
            val pos = this.currentMiddle - Vector2(8f, -8f)
            talkSpeechBubble.setPosition(pos.x, pos.y)
            talkSpeechBubble.draw(batch)
        }
    }
}


open class FriendlyAnimeliaInWorldCollision(val friendlyAnimeliaInWorld: FriendlyAnimeliaInWorld) : InputCollision() {
    override val keyCode = Input.Keys.ENTER
    override val insideText = "TALK"

    override fun collisionHappened(collidedObject: GameObject) {

        if (friendlyAnimeliaInWorld.isConditionsFulfilled()) {
            if (friendlyAnimeliaInWorld.goingToCitySpeech.size > 0) {
                changeMode(TalkMode(Conversation(friendlyAnimeliaInWorld.goingToCitySpeech), mainMode) {friendlyAnimeliaInWorld.goingToCityAction()})
            }
        } else {
            changeMode(TalkMode(Conversation(friendlyAnimeliaInWorld.speeches), mainMode))
            friendlyAnimeliaInWorld.afterSpeechAction()
            println("Not Fulfilled Yet")
        }
    }
}
