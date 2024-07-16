package com.mygdx.game.GameModes

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.mygdx.game.UI.TrainingScreen

class TrainingMode(val prevMode: GameMode): GameMode{
    val stage: Stage = Stage(ScreenViewport())
    override val spriteBatch = SpriteBatch()
    override val inputProcessor = stage
    val trainingScreen = TrainingScreen(prevMode, stage)

    init {
        trainingScreen.create()
    }

    override fun FrameAction() {
        trainingScreen.render()
    }
}