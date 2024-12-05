package com.mygdx.game.Saving.Signal.SignalListeners

import AnimeliaCityTalkedWithSignal
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Animation.TextAnimation
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Items.KeyItem
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Signal.Signal
import com.mygdx.game.Signal.SignaledEventListener
import com.mygdx.game.generalSaveState
import com.mygdx.game.player
import com.mygdx.game.plus

class AnimeliaTalkedWithInCity: SignaledEventListener {
    override val signaltype = SIGNALTYPE.ANIMELIA_CITY_TALKED_WITH

    override fun triggerEvent(signal: Signal) {
        val animeliaCityTalkedWithSignal = signal as AnimeliaCityTalkedWithSignal
        val entity = animeliaCityTalkedWithSignal.animeliaEntity

        if(entity == ANIMELIA_ENTITY.IceBird && KeyItem.FROZENHEART !in generalSaveState.inventory.keyItems){
            val textAnimation = TextAnimation(
                Color.WHITE,
                "You got the frozen heart!",
                player.currentMiddle + Vector2(0f,64f),
                false,
                120
            )
            AnimationManager.animationManager.add(textAnimation)
            generalSaveState.inventory.keyItems.add(KeyItem.FROZENHEART)
            generalSaveState.updateSaveState()
        }
        if(entity == ANIMELIA_ENTITY.MetalBird && KeyItem.MAP !in generalSaveState.inventory.keyItems){
            val textAnimation = TextAnimation(
                Color.WHITE,
                "You got the World Map!",
                player.currentMiddle + Vector2(0f,64f),
                false,
                120
            )
            AnimationManager.animationManager.add(textAnimation)
            generalSaveState.inventory.keyItems.add(KeyItem.MAP)
            generalSaveState.updateSaveState()
        }
    }
}