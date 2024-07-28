package com.mygdx.game.Animelia

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collition.Collision
import com.mygdx.game.Collition.InputCollition
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject

interface AnimeliaRecruitmendCondition{
    fun isConditionFulfilled(): Boolean
}

abstract class FriendlyAnimelia(gameObjectData: GameObjectData): GameObject(gameObjectData, Vector2(32f,32f)) {
    abstract val animeliaEntity: ANIMELIA_ENTITY
    val animeliaData by lazy { getAnimeliaData(animeliaEntity) }
    val animeliaRecruitmentConditions = mutableListOf<AnimeliaRecruitmendCondition>()

    override val collision = FriendlyAnimeliaCollision(this)

    fun isConditionsFulfilled(): Boolean{
        return animeliaRecruitmentConditions.all { it.isConditionFulfilled() }
    }
}

class FriendlyAnimeliaCollision(val friendlyAnimelia: FriendlyAnimelia): InputCollition(){
    override val keyCode = Input.Keys.C

    override fun collisionHappened(collidedObject: GameObject) {
        if(friendlyAnimelia.isConditionsFulfilled()){
            println("All conditions fulfilled")
        } else {
            println("Not Fulfilled Yet")
        }
    }

}
