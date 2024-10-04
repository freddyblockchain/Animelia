package com.mygdx.game.Saving.Signal.SignalListeners

import RailwayFixedSignal
import com.mygdx.game.Animelia.setAnimeliaSpriteTexture
import com.mygdx.game.GameObjects.Structures.Railway.Railway
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.RailwayTransportData
import com.mygdx.game.Managers.WorldStateManager
import com.mygdx.game.Signal.Signal
import com.mygdx.game.Signal.SignaledEventListener

class RailwayFixed: SignaledEventListener {
    override val signaltype = SIGNALTYPE.RAILWAY_FIXED
    override fun triggerEvent(signal: Signal) {
        val railwayFixedSignal = signal as RailwayFixedSignal
        val areaIdentifer = signal.areaIdentifer
        val area = AreaManager.getArea(areaIdentifer)
        val railway: Railway = area.gameObjects.firstOrNull{it.gameObjectIid == railwayFixedSignal.entityIid} as Railway

        WorldStateManager.railwayTransportDataList.add(RailwayTransportData(areaIdentifer, railway.bottomright))
        railway.brokenRailway.fix()
    }
}