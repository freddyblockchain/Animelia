package com.mygdx.game.Saving.Signal.SignalListeners

import RemoveObjectSignal
import SIGNALTYPE
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Signal.Signal
import com.mygdx.game.Signal.SignaledEventListener

class RemoveObject: SignaledEventListener {
    override val signaltype = SIGNALTYPE.REMOVE_OBJECT
    override fun triggerEvent(signal: Signal) {
        val removeItemsSignal = signal as RemoveObjectSignal
        val areaIdentifer = signal.areaIdentifer
        val area = AreaManager.getArea(areaIdentifer)
        area.gameObjects.firstOrNull{it.gameObjectIid == removeItemsSignal.entityIid}?.remove()
    }
}