package com.mygdx.game.Timer

open class CooldownTimer (private val cooldownTimeInSeconds: Float): Timer {
    private var lastUsedTime = 0L
    private var coolDownAvailable = true


    fun UpdateTimer() {
        val currentTime = System.currentTimeMillis()
        val newTime: Float = (currentTime - lastUsedTime).toFloat() / 1000
        if (newTime >= cooldownTimeInSeconds) {
            coolDownAvailable = true
        }
    }
    override fun tryUseCooldown():Boolean{
        UpdateTimer()
        if(coolDownAvailable){
            reset()
            return true
        }
        return false
    }

    override fun cooldownAvailable(): Boolean {
        UpdateTimer()
        return coolDownAvailable
    }

    override fun reset(){
        lastUsedTime = System.currentTimeMillis()
        coolDownAvailable = false
    }
}