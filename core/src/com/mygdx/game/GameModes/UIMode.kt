package com.mygdx.game.GameModes

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.UI.Screens.UIScreen
import com.mygdx.game.Utils.RenderGraph
import com.mygdx.game.mainMode

class UIMode(val uiScreen: UIScreen, override val spriteBatch: SpriteBatch = mainMode.spriteBatch, val renderGameObjects: Boolean = true): GameMode{
    val stage = uiScreen.stage
    init {
        uiScreen.create()
    }
    override val inputProcessor = uiScreen.stage

    override fun FrameAction() {
        spriteBatch.begin()
        uiScreen.render()
        if(renderGameObjects){
            for(gameObject in AreaManager.getActiveArea()!!.gameObjects.toMutableList()){
                RenderGraph.addToSceneGraph(gameObject)
            }
        }
        spriteBatch.end()
    }
}