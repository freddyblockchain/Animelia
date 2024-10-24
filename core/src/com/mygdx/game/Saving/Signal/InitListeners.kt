package com.mygdx.game.Signal

import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.Saving.Signal.SignalListeners.AnimeliaRecruited
import com.mygdx.game.Saving.Signal.SignalListeners.CrystalActivated
import com.mygdx.game.Saving.Signal.SignalListeners.RailwayFixed
import com.mygdx.game.Saving.Signal.SignalListeners.RemoveObject
import com.mygdx.game.Signal.SignalListeners.AbilityGained

fun initSignalListeners() {
    SignalManager.addSignalListeners(
        listOf(
            RemoveObject(),
            AnimeliaRecruited(),
            RailwayFixed(),
            CrystalActivated(),
        )
    )
}