package com.mygdx.game.Abilities

import com.mygdx.game.Enums.PlayerState
import com.mygdx.game.butler
import com.mygdx.game.player

class ButlerRiding: Ability {
    override fun onActivate() {
        player.state = PlayerState.BUTLERRIDING
        butler.active = false
    }

    override fun onDeactivate() {
        player.state = PlayerState.NORMAL
        butler.setPosition(player.currentPosition())
        butler.active = true
    }

    override val abilityId = AbilityId.BUTLERRIDING

}