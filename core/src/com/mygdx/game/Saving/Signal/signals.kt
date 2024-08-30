
import com.mygdx.game.Signal.Signal
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

enum class SIGNALTYPE { ABILITY_GAINED, REMOVE_OBJECT }

@Serializable
class RemoveObjectSignal(val entityIid: String) : Signal(SIGNALTYPE.REMOVE_OBJECT) {

}


@Serializable
class AbilityGainedSignal() : Signal(SIGNALTYPE.ABILITY_GAINED) {

}

fun signalConvert(signalString: String): Signal{
    val processedString = signalString.split(",")[0] + '}'
    val newSignal:Signal = Json.decodeFromString(processedString)


    return when(newSignal.signaltype){
        SIGNALTYPE.ABILITY_GAINED -> Json.decodeFromString<AbilityGainedSignal>(signalString)
        SIGNALTYPE.REMOVE_OBJECT -> Json.decodeFromString<RemoveObjectSignal>(signalString)
    }
}