package com.mygdx.game.Abilities

import com.mygdx.game.butler
import com.mygdx.game.player

class ButlerSwitch: KeyAbility {
    override val triggerKey = com.badlogic.gdx.Input.Keys.NUM_1

    override fun onActivate() {
        if(butler.active){
            val playerPos = player.currentPosition()
            val butlerPosition = butler.currentPosition()

            player.setPosition(butlerPosition)
            butler.setPosition(playerPos)
        }
    }

    override fun onDeactivate() {

    }
}