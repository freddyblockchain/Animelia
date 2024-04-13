import com.mygdx.game.Abilities.AbilityId
import com.mygdx.game.Signal.Signal
import kotlinx.serialization.Serializable

enum class SIGNALTYPE { ABILITY_GAINED, REMOVE_OBJECT }

@Serializable
class RemoveObjectSignal(val entityIid: String) : Signal(SIGNALTYPE.REMOVE_OBJECT) {

}


@Serializable
class AbilityGainedSignal(val abilityId: AbilityId) : Signal(SIGNALTYPE.ABILITY_GAINED) {

}
