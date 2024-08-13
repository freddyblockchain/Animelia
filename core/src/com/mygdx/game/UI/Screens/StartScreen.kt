package com.mygdx.game.UI.Screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.mygdx.game.GameModes.GameMode

class StartScreen(val nextGameMode: GameMode): UIScreen() {
    override val nrOfButtons = 3
    override var activeButton: Actor? = null

    override fun render() {

    }

}