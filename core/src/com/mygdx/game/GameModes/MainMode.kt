package com.mygdx.game.GameModes

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.MyInputProcessor

class MainMode(val inputProcessor: MyInputProcessor): GameMode {
    override val spriteBatch = SpriteBatch()

    override fun FrameAction() {
        for(gameObject in AreaManager.getActiveArea()!!.gameObjects.toMutableList()){
            gameObject.frameTask()
        }
        inputProcessor.handleInput()
    }
}