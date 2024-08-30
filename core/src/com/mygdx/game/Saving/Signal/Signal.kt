package com.mygdx.game.Signal

import SIGNALTYPE
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
open class Signal(val signaltype: SIGNALTYPE, var areaIdentifer: String = "World1") {

    open fun encode():String{
        return Json.encodeToString(this)
    }
}