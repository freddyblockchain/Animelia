package com.mygdx.game.Signal

import AbilityGainedSignal
import RemoveObjectSignal
import SIGNALTYPE
import kotlinx.serialization.json.Json

fun signalConvert(signalString: String): Signal{
    val processedString = signalString.split(",")[0] + '}'
    val newSignal:Signal = Json.decodeFromString(processedString)


    return when(newSignal.signaltype){
        SIGNALTYPE.ABILITY_GAINED-> Json.decodeFromString<AbilityGainedSignal>(signalString)
        SIGNALTYPE.REMOVE_OBJECT -> Json.decodeFromString<RemoveObjectSignal>(signalString)
    }
}