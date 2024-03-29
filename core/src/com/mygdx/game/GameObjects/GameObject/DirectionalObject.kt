package com.mygdx.game.GameObjects.GameObject

import com.mygdx.game.Enums.Direction

interface DirectionalObject {
    var direction: Direction

    var canChangeDirection: Boolean

    fun enableChangingDirection(){
        canChangeDirection = true
    }
    fun freezeChangingDirection(){
        canChangeDirection = false
    }
    fun canChangeDirection(): Boolean{
        return canChangeDirection
    }
}