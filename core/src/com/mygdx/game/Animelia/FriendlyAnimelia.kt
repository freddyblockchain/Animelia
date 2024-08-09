package com.mygdx.game.Animelia

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collition.InputCollision
import com.mygdx.game.EntityRefData
import com.mygdx.game.GameModes.TalkMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.UI.Conversation.Conversation
import com.mygdx.game.UI.Conversation.SpeechData
import com.mygdx.game.mainMode

interface AnimeliaRecruitmendCondition{
    fun isConditionFulfilled(): Boolean
}

abstract class FriendlyAnimelia(gameObjectData: GameObjectData, private val cityPosEntityRefData: EntityRefData): GameObject(gameObjectData, Vector2(32f,32f)) {
    abstract val animeliaEntity: ANIMELIA_ENTITY
    val animeliaData by lazy { getAnimeliaData(animeliaEntity) }
    val animeliaRecruitmentConditions = mutableListOf<AnimeliaRecruitmendCondition>()
    abstract fun recruitmentAction()


    override val collision = FriendlyAnimeliaCollision(this)

    lateinit var cityPosition: AnimeliaPosition

    override fun initObject() {
       cityPosition = AreaManager.getObjectWithIid(cityPosEntityRefData.entityIid, cityPosEntityRefData.levelIid) as AnimeliaPosition
    }
    fun isConditionsFulfilled(): Boolean{
        return animeliaRecruitmentConditions.all { it.isConditionFulfilled() }
    }
}

class FriendlyAnimeliaCollision(val friendlyAnimelia: FriendlyAnimelia): InputCollision(){
    override val keyCode = Input.Keys.SPACE

    override fun collisionHappened(collidedObject: GameObject) {
        val speech1 = SpeechData("", "Do you wanna come to the city?")
        val speech2 = SpeechData("", "hmm, prove your worth by defeating 3 animelia clones")
        val speech3 = SpeechData("", "Alright, i'll do that")

        val speeches = listOf(speech1, speech2, speech3)

        changeMode(TalkMode(Conversation(speeches), mainMode))
        if(friendlyAnimelia.isConditionsFulfilled()){
            println("All conditions fulfilled")
            friendlyAnimelia.recruitmentAction()
        } else {
            println("Not Fulfilled Yet")
        }
    }

}
