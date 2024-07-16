package com.mygdx.game.Ability.Abilities.Fighting

import com.badlogic.gdx.Input
import com.mygdx.game.Ability.KeyAbility
import com.mygdx.game.GameObjects.GameObject.FightableObject

class TailSwipe(override val fightableObject: FightableObject): KeyAbility {
    override val triggerKey = Input.Keys.NUM_1
    override val activeFrames = 40
    override var currentFrame = 0
    val rotationIncrement = (360 / activeFrames).toFloat()

    override fun onActivate() {
        frameAction()
    }

    override fun frameAction(){
        fightableObject.rotateByAmount(rotationIncrement, fightableObject)
        fightableObject.forceMove(1f)
    }

    override fun onDeactivate() {

    }
}