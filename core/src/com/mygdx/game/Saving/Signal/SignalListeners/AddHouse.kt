package com.mygdx.game.Saving.Signal.SignalListeners

import ChangeVisibleSignal
import com.mygdx.game.GameObjects.Structures.House
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Signal.Signal
import com.mygdx.game.Signal.SignaledEventListener

class AddHouse: SignaledEventListener {
    override val signaltype = SIGNALTYPE.CHANGE_VISIBLE
    override fun triggerEvent(signal: Signal) {
        val visibleSignal = signal as ChangeVisibleSignal
        val house = AreaManager.getObjectWithIid(entityId = visibleSignal.entityIid, levelId = visibleSignal.levelId) as House
        if(!house.visible){
            house.initObject()
            house.door.initObject()
            house.door.add()
            house.add()
            house.visible = true
        }
    }
}