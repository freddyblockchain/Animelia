package com.mygdx.game.Animation

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Managers.DialogueManager

class Conversation(val dialogueName: String): DefaultAnimation() {
    val speeches = DialogueManager.dialogueMap[dialogueName]!!
    val speechLength = 300
    override val duration = speechLength * speeches.size
    override val animationAction: () -> Unit = {}
    override var actionFrame = 0
    override val layer = Layer.AIR

    var counter = 0
    var currentIndex = 0
    val speechBubbles = speeches.map { SpeechBubble(it, speechLength) }

    override fun render(batch: SpriteBatch) {
        val currentSpeechBubble = speechBubbles[currentIndex]
        currentSpeechBubble.render(batch)
        counter++
        if(counter % speechLength == 0){
            currentIndex++
        }

    }
}