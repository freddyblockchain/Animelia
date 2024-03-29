package com.mygdx.game.GameObjects

interface Toggelable {
    fun toggleOn()
    fun toggleOff()
    var toggleCounter: Int

}

open class DefaultToggelable(): Toggelable{
    override var toggleCounter = 0

    override fun toggleOff() {
        toggleCounter -= 1
    }

    override fun toggleOn() {
        toggleCounter += 1
    }
}