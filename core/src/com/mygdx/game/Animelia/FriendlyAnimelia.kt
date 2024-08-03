package com.mygdx.game.Animelia

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collition.Collision
import com.mygdx.game.Collition.InputCollition
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Utils.Triggerable

interface AnimeliaRecruitmendCondition{
    fun isConditionFulfilled(): Boolean
}

abstract class FriendlyAnimelia(gameObjectData: GameObjectData, private val cityPosEntityId: String): GameObject(gameObjectData, Vector2(32f,32f)) {
    abstract val animeliaEntity: ANIMELIA_ENTITY
    val animeliaData by lazy { getAnimeliaData(animeliaEntity) }
    val animeliaRecruitmentConditions = mutableListOf<AnimeliaRecruitmendCondition>()
    abstract fun recruitmentAction()


    override val collision = FriendlyAnimeliaCollision(this)

    lateinit var cityPosition: AnimeliaPosition

    override fun initObject() {
       cityPosition = AreaManager.getObjectWithIid(cityPosEntityId) as AnimeliaPosition
    }
    fun isConditionsFulfilled(): Boolean{
        return animeliaRecruitmentConditions.all { it.isConditionFulfilled() }
    }
}

class FriendlyAnimeliaCollision(val friendlyAnimelia: FriendlyAnimelia): InputCollition(){
    override val keyCode = Input.Keys.C

    override fun collisionHappened(collidedObject: GameObject) {
        if(friendlyAnimelia.isConditionsFulfilled()){
            println("All conditions fulfilled")
            friendlyAnimelia.recruitmentAction()
        } else {
            println("Not Fulfilled Yet")
        }
    }

}
