package com.mygdx.game.GameModes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.currentGameMode
import com.mygdx.game.defaultLineWidth

interface GameMode {
    val spriteBatch: SpriteBatch
    fun render()
    fun FrameAction(){

    }
    fun OnlyRenderFrameAction(){

    }
    val inputProcessor: InputProcessor

    fun modeInit() {

    }
}

open class DefaultInputProcessor(): InputProcessor {
    override fun keyDown(keycode: Int): Boolean {
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchCancelled(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        return false
    }
}
fun changeMode(newMode: GameMode){
    currentGameMode = newMode
    newMode.modeInit()
    Gdx.input.inputProcessor = newMode.inputProcessor
    Gdx.gl.glLineWidth(defaultLineWidth)
}