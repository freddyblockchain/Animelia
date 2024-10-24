package com.mygdx.game.Saving.Signal.SignalListeners

import CrystalActivatedSignal
import RailwayFixedSignal
import com.mygdx.game.GameObjects.Other.Crystals.Statue
import com.mygdx.game.GameObjects.Structures.Railway.Railway
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Signal.Signal
import com.mygdx.game.Signal.SignaledEventListener

class CrystalActivated: SignaledEventListener {
    override val signaltype = SIGNALTYPE.CRYSTAL_ACTIVATED
    override fun triggerEvent(signal: Signal) {
        val crystalActivatedSignal = signal as CrystalActivatedSignal
        val areaIdentifer = signal.areaIdentifer
        val area = AreaManager.getArea(areaIdentifer)
        val statue: Statue = area.gameObjects.firstOrNull{it.gameObjectIid == crystalActivatedSignal.statueEntityIid} as Statue
        statue.activateCrystal()
    }
}