package com.mygdx.game.Animation

import FontManager.Companion.ChapterFont
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.Enums.Layer
import com.mygdx.game.currentGameMode
import com.mygdx.game.mainMode

class ChapterAnimation(val text: String): DefaultAnimation() {
    override val duration = 360
    override val animationAction = {
        currentGameMode = mainMode
    }
    override var actionFrame = 360
    override val layer = Layer.AIR
    val alphaTime = (duration / 3)
    var alpha = 0f

    override fun render(batch: SpriteBatch) {
        if (currentFrame <= alphaTime + 1) {
            alpha += 1f / alphaTime
        }

        if(currentFrame > alphaTime * 2){
            alpha -= 1f / alphaTime
        }
        val color = Color.WHITE
        color.a = alpha
        ChapterFont.color = color
        ChapterFont.draw(batch,text, -32f , 300f)
    }
}