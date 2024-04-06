package com.mygdx.game.Timer

open class CooldownTimer (private val CooldownTime: Float): Timer {
    private var lastUsedTime = 0L
    var coolDownAvailable = true
        private set


    fun UpdateTimer() {
        val currentTime = System.currentTimeMillis()
        val newTime: Float = (currentTime - lastUsedTime).toFloat() / 1000
        if (newTime >= CooldownTime) {
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