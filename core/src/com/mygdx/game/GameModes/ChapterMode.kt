package com.mygdx.game.GameModes

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.Animation.ChapterAnimation
import com.mygdx.game.Managers.AnimationManager

class ChapterMode: GameMode {
    override val spriteBatch = SpriteBatch()
    var frameCounter = 0
    override fun FrameAction(){
        if(frameCounter == 0){
            AnimationManager.animationManager.add(ChapterAnimation("Prologue"))
        }
        frameCounter += 1
    }
}