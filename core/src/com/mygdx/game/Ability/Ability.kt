package com.mygdx.game.Ability

import com.mygdx.game.GameObjects.GameObject.FightableObject

interface Ability {
    fun onActivate()
    fun onDeactivate()
    val activeFrames: Int
    var currentFrame: Int
    fun frameAction()
    val fightableObject: FightableObject

}
