package com.mygdx.game.Timer
interface Timer {
    fun tryUseCooldown():Boolean
    fun cooldownAvailable(): Boolean

    fun reset()
}
