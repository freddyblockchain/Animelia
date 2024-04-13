package com.mygdx.game.Signal.SignalListeners

import AbilityGainedSignal
import SIGNALTYPE
import com.mygdx.game.Abilities.getAbility
import com.mygdx.game.Signal.Signal
import com.mygdx.game.Signal.SignaledEventListener
import com.mygdx.game.player

class AbilityGained: SignaledEventListener {
    override val signaltype = SIGNALTYPE.ABILITY_GAINED

    override fun triggerEvent(signal: Signal) {
        val abilityGainedSignal = signal as AbilityGainedSignal
        val abilityType = abilityGainedSignal.abilityId
        val ability = getAbility(abilityType)
        player.abilities.add(ability)
    }
}