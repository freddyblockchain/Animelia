package com.mygdx.game.GameModes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.currentGameMode

interface GameMode {
    val spriteBatch: SpriteBatch
    fun FrameAction(){

    }
    val inputProcessor: InputProcessor
}

fun changeMode(newMode: GameMode){
    currentGameMode = newMode
    Gdx.input.inputProcessor = newMode.inputProcessor
}