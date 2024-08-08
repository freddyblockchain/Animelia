package com.mygdx.game.GameModes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.UI.TrainingScreen
import com.mygdx.game.UI.UIScreen
import com.mygdx.game.Utils.RenderGraph
import com.mygdx.game.mainMode

class UIMode(val uiScreen: UIScreen, override val spriteBatch: SpriteBatch = mainMode.spriteBatch): GameMode{

    init {
        uiScreen.create()
    }
    override val inputProcessor = uiScreen.stage

    override fun FrameAction() {
        spriteBatch.begin()
        uiScreen.render()
        for(gameObject in AreaManager.getActiveArea()!!.gameObjects.toMutableList()){
            RenderGraph.addToSceneGraph(gameObject)
        }
        spriteBatch.end()
    }
}