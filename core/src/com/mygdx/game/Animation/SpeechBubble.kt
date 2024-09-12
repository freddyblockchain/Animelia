package com.mygdx.game.Animation

import FontManager
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.DialogueSystem.Sentence
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Managers.DialogueManager
import com.mygdx.game.player

class SpeechBubble(val sentence: String, talkingEntity: GameObject, duration: Int): DefaultAnimation() {
    override val durationFrames = duration
    override val animationAction = {}
    override var actionFrame = duration
    val texture = DefaultTextureHandler.getTexture("speechicon.png")
    val speechBubbleSprite = Sprite(texture)
    val talkingObject = talkingEntity
    override val layer = Layer.AIR

    init {
        speechBubbleSprite.setSize(32f*5.5f ,32f)
    }

    override fun render(batch: SpriteBatch) {
        val speechX = talkingObject.currentPosition().x
        val speechY = talkingObject.currentPosition().y + talkingObject.height
        speechBubbleSprite.setPosition(speechX,speechY)
        val textX = speechBubbleSprite.x
        val textY = speechBubbleSprite.y + speechBubbleSprite.height - 8f
        speechBubbleSprite.draw(batch)
        FontManager.TextFont.draw(batch, sentence, textX, textY)
    }
}