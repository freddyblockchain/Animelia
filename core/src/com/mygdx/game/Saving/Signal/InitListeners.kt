package com.mygdx.game.Signal

import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.Saving.Signal.SignalListeners.*
import com.mygdx.game.Signal.SignalListeners.AbilityGained

fun initSignalListeners() {
    SignalManager.addSignalListeners(
        listOf(
            RemoveObject(),
            AnimeliaRecruited(),
            RailwayFixed(),
            CrystalActivated(),
            AnimeliaTalkedWithInCity(),
        )
    )
}