package com.mygdx.game.GameModes

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.mygdx.game.UI.TrainingScreen
import com.mygdx.game.UI.UIScreen

class UIMode(val uiScreen: UIScreen): GameMode{
    val stage: Stage = Stage(ScreenViewport())
    override val spriteBatch = SpriteBatch()
    override val inputProcessor = stage

    init {
        uiScreen.create()
    }

    override fun FrameAction() {
        uiScreen.render()
    }
}