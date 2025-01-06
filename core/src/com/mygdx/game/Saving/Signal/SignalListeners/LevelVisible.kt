package com.mygdx.game.Saving.Signal.SignalListeners

import ChangeLevelVisibleSignal
import com.mygdx.game.GameObjects.Structures.House
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Signal.Signal
import com.mygdx.game.Signal.SignaledEventListener

class LevelVisible: SignaledEventListener {
    override val signaltype = SIGNALTYPE.CHANGE_LEVEL_VISIBLE
    override fun triggerEvent(signal: Signal) {
        val visibleSignal = signal as ChangeLevelVisibleSignal
        AreaManager.levelVisibleMap[visibleSignal.levelId] = true
    }
}