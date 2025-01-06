
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.EntityRefData
import com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia.MetalBirdPos
import com.mygdx.game.Signal.Signal
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

enum class SIGNALTYPE { ABILITY_GAINED, REMOVE_OBJECT, ANIMELIA_RECRUITED, ANIMELIA_CITY_TALKED_WITH, RAILWAY_FIXED, CRYSTAL_ACTIVATED, METAL_BIRD_TALKING, FIREPLACE_LIT, CHANGE_VISIBLE}

@Serializable
class RemoveObjectSignal(val entityIid: String) : Signal(SIGNALTYPE.REMOVE_OBJECT) {

}

@Serializable
class ChangeVisibleSignal(val entityIid: String, val levelId: String) : Signal(SIGNALTYPE.CHANGE_VISIBLE) {

}

@Serializable
class MetalBirdTalkingSignal(val entityIid: String,val levelId: String, val metalBirdPos: MetalBirdPos) : Signal(SIGNALTYPE.METAL_BIRD_TALKING) {

}

@Serializable
class FireplaceLitSignal() : Signal(SIGNALTYPE.FIREPLACE_LIT) {

}

@Serializable
class RailwayFixedSignal(val entityIid: String): Signal(SIGNALTYPE.RAILWAY_FIXED)


@Serializable
class AbilityGainedSignal() : Signal(SIGNALTYPE.ABILITY_GAINED) {

}
@Serializable
class CrystalActivatedSignal(val crystalEntityIId: String, val statueEntityIid: String) : Signal(SIGNALTYPE.CRYSTAL_ACTIVATED) {

}

@Serializable
class AnimeliaRecruitedSignal(val animeliaEntity: ANIMELIA_ENTITY,val posX: Float, val posY: Float) : Signal(SIGNALTYPE.ANIMELIA_RECRUITED) {

}

@Serializable
class AnimeliaCityTalkedWithSignal(val animeliaEntity: ANIMELIA_ENTITY) : Signal(SIGNALTYPE.ANIMELIA_CITY_TALKED_WITH) {

}


fun signalConvert(signalString: String): Signal {
    val processedString = signalString.split(",")[0] + '}'
    val newSignal: Signal = Json.decodeFromString(processedString)


    return when (newSignal.signaltype) {
        SIGNALTYPE.ABILITY_GAINED -> Json.decodeFromString<AbilityGainedSignal>(signalString)
        SIGNALTYPE.REMOVE_OBJECT -> Json.decodeFromString<RemoveObjectSignal>(signalString)
        SIGNALTYPE.ANIMELIA_RECRUITED -> Json.decodeFromString<AnimeliaRecruitedSignal>(signalString)
        SIGNALTYPE.ANIMELIA_CITY_TALKED_WITH -> Json.decodeFromString<AnimeliaCityTalkedWithSignal>(signalString)
        SIGNALTYPE.RAILWAY_FIXED -> Json.decodeFromString<RailwayFixedSignal>(signalString)
        SIGNALTYPE.CRYSTAL_ACTIVATED -> Json.decodeFromString<CrystalActivatedSignal>(signalString)
        SIGNALTYPE.METAL_BIRD_TALKING -> Json.decodeFromString<MetalBirdTalkingSignal>(signalString)
        SIGNALTYPE.FIREPLACE_LIT -> Json.decodeFromString<FireplaceLitSignal>(signalString)
        SIGNALTYPE.CHANGE_VISIBLE -> Json.decodeFromString<ChangeVisibleSignal>(signalString)
    }
}