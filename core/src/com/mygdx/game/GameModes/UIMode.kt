package com.mygdx.game.GameModes

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.UI.Scene2d.Screens.UIScreen
import com.mygdx.game.Utils.RenderGraph
import com.mygdx.game.mainMode

class UIMode(val uiScreen: UIScreen, override val spriteBatch: SpriteBatch = mainMode.spriteBatch, playConfirmationSound: Boolean = true): GameMode{
    val stage = uiScreen.stage
    init {
        uiScreen.create()
        uiScreen.associatedMode = this

        if(playConfirmationSound){
            val id = uiScreen.confirmSound.play()
            uiScreen.confirmSound.setVolume(id, 0.2f)
        }

    }
    override val inputProcessor = uiScreen.stage

    override fun FrameAction() {
        spriteBatch.begin()
        if(uiScreen.renderPrevGameMode){
            uiScreen.prevMode?.OnlyRenderFrameAction()
        }
        spriteBatch.end()
    }

    override fun render() {
        if(uiScreen.renderPrevGameMode){
            uiScreen.prevMode?.render()
        }
        uiScreen.render()
    }
}