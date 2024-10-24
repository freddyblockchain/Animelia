package com.mygdx.game.Ability.Abilities.Fighting

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Ability.AbilityName
import com.mygdx.game.Ability.ELEMENTAL_TYPE
import com.mygdx.game.Ability.KeyAbility
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.GameObjects.MoveableObjects.Other.TailSwipeObject
import com.mygdx.game.Managers.AreaManager

class TailSwipe(override val attachedFightableObject: FightableObject): KeyAbility {
    override val abilityName = AbilityName.TailSwipe
    override val ELEMENTALTYPES = ELEMENTAL_TYPE.FIGHTING
    override val activeFrames = 60
    override var currentFrame = 0
    val rotationIncrement = (360 / activeFrames).toFloat()
    var tailSwipeObject: TailSwipeObject? = null

    override fun onActivate() {
        tailSwipeObject = TailSwipeObject(GameObjectData(), Vector2(0f,0f), attachedFightableObject, rotationIncrement)
        AreaManager.getActiveArea()!!.gameObjects.add(tailSwipeObject!!)
        frameAction()
    }

    override fun frameAction(){
        attachedFightableObject.sprite.rotate(rotationIncrement)
        attachedFightableObject.forceMove(1f)
    }

    override fun onDeactivate() {
        if(tailSwipeObject != null){
            AreaManager.getActiveArea()!!.gameObjects.remove(tailSwipeObject!!)
        }
    }
}