package com.mygdx.game.Signal

import SIGNALTYPE

interface SignaledEventListener {
    val signaltype: SIGNALTYPE
    fun triggerEvent(signal:Signal)
}