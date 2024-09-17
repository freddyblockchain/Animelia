package com.mygdx.game.UI

import FontManager
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjects.Sign
import com.mygdx.game.Rendering.Renderable
import com.mygdx.game.Utils.Center
import com.mygdx.game.div
import com.mygdx.game.plus
import com.mygdx.game.zoomY
import kotlin.math.min
import kotlin.math.sign

class SignText(val text: String, val sign: Sign): Renderable {
    override val layer = Layer.AIR
    val sprite = Sprite(DefaultTextureHandler.getTexture("SignText.png"))
    val boxPos = Vector2(sign.x - 50f, sign.y + 50f)
    val textCutoff = 36
    var scale = 1f



    init {
        val ySize = if(text.length >= textCutoff) 4f else 2f
        sprite.setScale(3f,ySize)
        scale = if(text.length >= textCutoff) 1.5f else 1f
    }

    override fun render(batch: SpriteBatch) {
        sprite.setPosition(boxPos.x, boxPos.y)
        sprite.draw(batch)
        FontManager.TextFont.draw(batch, text.subSequence(0, min( text.length, textCutoff)), boxPos.x - 50f, sprite.y + sprite.height * scale)
        if(text.length >= textCutoff){
            FontManager.TextFont.draw(batch, text.subSequence(textCutoff,text.length), boxPos.x - 50f, sprite.y - 20f + sprite.height * scale)
        }
        /*if(text2 != null){
            FontManager.MediumFont.draw(batch, text2, sprite.x - 150f, sprite.y)
        }*/
    }


}