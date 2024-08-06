package com.mygdx.game.GameModes

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.UI.Conversation.Conversation
import com.mygdx.game.UI.Conversation.SpeechTextBubble
import com.mygdx.game.Utils.RenderGraph

class TalkMode(val conversation: Conversation, val prevMode: GameMode): GameMode {
    override val spriteBatch = SpriteBatch()
    override val inputProcessor = TalkInputProcessor(::updateSpeechIndex)
    val speechTextBubble = SpeechTextBubble()
    var currentSpeechIndex = 0

    override fun FrameAction() {
        spriteBatch.begin()
        for(gameObject in AreaManager.getActiveArea()!!.gameObjects.toMutableList()){
            RenderGraph.addToSceneGraph(gameObject)
        }
        val textBubbleToRender = conversation.speechDataList[currentSpeechIndex]
        speechTextBubble.render(spriteBatch, textBubbleToRender)
        spriteBatch.end()
    }


    fun updateSpeechIndex(){
        currentSpeechIndex += 1

        if(currentSpeechIndex >= conversation.speechDataList.size){
            changeMode(prevMode)
        }
    }
}

class TalkInputProcessor(val advanceSpeechFunction: () -> Unit) : DefaultInputProcessor(){
    override fun keyDown(keycode: Int): Boolean {
        if(keycode == Input.Keys.SPACE){
            advanceSpeechFunction()
        }
        return true
    }
}