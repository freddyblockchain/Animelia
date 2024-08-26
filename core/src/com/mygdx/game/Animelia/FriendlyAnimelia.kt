package com.mygdx.game.Animelia

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collition.InputCollision
import com.mygdx.game.EntityRefData
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameModes.TalkMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.GameObject.DefaultRotationalObject
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.GameObject.RotationalObject
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.UI.Conversation.Conversation
import com.mygdx.game.UI.Conversation.SpeechData
import com.mygdx.game.mainMode

interface AnimeliaRecruitmendCondition{
    fun isConditionFulfilled(): Boolean
}

abstract class FriendlyAnimelia(gameObjectData: GameObjectData, private val cityPosEntityRefData: EntityRefData): GameObject(gameObjectData, Vector2(32f,32f)), RotationalObject by DefaultRotationalObject() {
    abstract val animeliaEntity: ANIMELIA_ENTITY
    val animeliaData by lazy { getAnimeliaData(animeliaEntity) }
    val animeliaRecruitmentConditions = mutableListOf<AnimeliaRecruitmendCondition>()

    abstract val speeches: List<SpeechData>
    open val goingToCitySpeech = listOf<SpeechData>()
    abstract val inCitySpeeches: List<SpeechData>
    abstract fun recruitmentAction()

    var isRecruited = false


    override val collision = FriendlyAnimeliaCollision(this)

    lateinit var cityPosition: AnimeliaPosition

    override val layer = Layer.ONGROUND

    override fun initObject() {
       cityPosition = AreaManager.getObjectWithIid(cityPosEntityRefData.entityIid, cityPosEntityRefData.levelIid) as AnimeliaPosition
       this.sprite.texture = animeliaData.gameTexture

    }
    fun isConditionsFulfilled(): Boolean{
        return animeliaRecruitmentConditions.all { it.isConditionFulfilled() }
    }
}

class FriendlyAnimeliaCollision(val friendlyAnimelia: FriendlyAnimelia): InputCollision(){
    override val keyCode = Input.Keys.SPACE
    override val insideText = "TALK"

    override fun collisionHappened(collidedObject: GameObject) {
        if (friendlyAnimelia.isRecruited) {
            changeMode(TalkMode(Conversation(friendlyAnimelia.inCitySpeeches), mainMode))
        } else {
            if (friendlyAnimelia.isConditionsFulfilled()) {
                friendlyAnimelia.isRecruited = true
                if (friendlyAnimelia.goingToCitySpeech.size > 0) {
                    changeMode(TalkMode(Conversation(friendlyAnimelia.goingToCitySpeech), mainMode))
                }
                println("All conditions fulfilled")
                friendlyAnimelia.recruitmentAction()
            } else {
                changeMode(TalkMode(Conversation(friendlyAnimelia.speeches), mainMode))
                println("Not Fulfilled Yet")
            }
        }
    }

}
