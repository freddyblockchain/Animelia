package com.mygdx.game.GameModes

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.InGameInputProcessor
import com.mygdx.game.UI.MainGameUi.AbilityTooltipRow
import com.mygdx.game.Utils.RenderGraph
import com.mygdx.game.currentGameMode

class MainMode(override val inputProcessor: InGameInputProcessor): GameMode {
    override val spriteBatch = SpriteBatch()

    val uiSpriteBatch = SpriteBatch()
    val abilityRowUi = AbilityTooltipRow()

    override fun FrameAction() {
        for(gameObject in AreaManager.getActiveArea()!!.gameObjects.toMutableList()){
            gameObject.frameTask()
        }
        inputProcessor.handleInput()
    }

    override fun OnlyRenderFrameAction() {
        for(gameObject in AreaManager.getActiveArea()!!.gameObjects.toMutableList()){
            RenderGraph.addToSceneGraph(gameObject)
        }
    }

    override fun render() {
        RenderGraph.render(spriteBatch)
        uiSpriteBatch.begin()
        abilityRowUi.render(uiSpriteBatch)
        uiSpriteBatch.end()
    }
}