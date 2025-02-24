package com.mygdx.game.GameModes

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.InGameInputProcessor
import com.mygdx.game.Managers.EventManager
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
        EventManager.executeEvents()
        drawHealthBars()
    }


    fun drawHealthBars(){
        for (fightableObject in AreaManager.getActiveArea()!!.gameObjects.filterIsInstance<FightableObject>()){
            fightableObject.healthStrategy.showHealth(fightableObject.sprite, fightableObject.currentHealth, fightableObject.maxHealth)
        }
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