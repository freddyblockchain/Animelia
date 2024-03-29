package com.mygdx.game.GameModes

import com.badlogic.gdx.graphics.g2d.SpriteBatch

interface GameMode {
    val spriteBatch: SpriteBatch
    fun FrameAction(){

    }
}