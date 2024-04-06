package com.mygdx.game.Trimer

import com.mygdx.game.Timer.CooldownTimer
import com.mygdx.game.Timer.Timer

class DelayTimer(timeUntilExecute: Float, cooldownTime: Float): Timer{
    private val delayTimer = CooldownTimer(timeUntilExecute)
    private val cooldownTimer = CooldownTimer(cooldownTime)
    init {
        delayTimer.tryUseCooldown()
    }
    private var timePassed = false

    fun getTimeHasPassed():Boolean{
        if(!timePassed && delayTimer.tryUseCooldown()){
            timePassed = true
        }
        return timePassed
    }

    override fun tryUseCooldown(): Boolean {
        if(getTimeHasPassed()){
            return cooldownTimer.tryUseCooldown()
        }
        return false
    }

    override fun cooldownAvailable(): Boolean {
       return cooldownTimer.cooldownAvailable() && timePassed
    }

    override fun reset() {
        delayTimer.reset()
        cooldownTimer.reset()
        timePassed = false
    }
}