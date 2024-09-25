
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Signal.Signal
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

enum class SIGNALTYPE { ABILITY_GAINED, REMOVE_OBJECT, ANIMELIA_RECRUITED, ANIMELIA_CITY_TALKED_WITH, RAILWAY_FIXED}

@Serializable
class RemoveObjectSignal(val entityIid: String) : Signal(SIGNALTYPE.REMOVE_OBJECT) {

}

@Serializable
class RailwayFixedSignal(val entityIid: String): Signal(SIGNALTYPE.RAILWAY_FIXED)


@Serializable
class AbilityGainedSignal() : Signal(SIGNALTYPE.ABILITY_GAINED) {

}

@Serializable
class AnimeliaRecruitedSignal(val animeliaEntity: ANIMELIA_ENTITY) : Signal(SIGNALTYPE.ANIMELIA_RECRUITED) {

}

@Serializable
class AnimeliaCityTalkedWithSignal(val animeliaEntity: ANIMELIA_ENTITY) : Signal(SIGNALTYPE.ANIMELIA_CITY_TALKED_WITH) {

}


fun signalConvert(signalString: String): Signal{
    val processedString = signalString.split(",")[0] + '}'
    val newSignal:Signal = Json.decodeFromString(processedString)


    return when(newSignal.signaltype){
        SIGNALTYPE.ABILITY_GAINED -> Json.decodeFromString<AbilityGainedSignal>(signalString)
        SIGNALTYPE.REMOVE_OBJECT -> Json.decodeFromString<RemoveObjectSignal>(signalString)
        SIGNALTYPE.ANIMELIA_RECRUITED -> Json.decodeFromString<AnimeliaRecruitedSignal>(signalString)
        SIGNALTYPE.ANIMELIA_CITY_TALKED_WITH -> Json.decodeFromString<AnimeliaCityTalkedWithSignal>(signalString)
        SIGNALTYPE.RAILWAY_FIXED -> Json.decodeFromString<RailwayFixedSignal>(signalString)
    }
}