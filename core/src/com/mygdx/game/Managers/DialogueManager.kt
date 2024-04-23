package com.mygdx.game.Managers

import com.mygdx.game.DialogueSystem.Sentence
import com.mygdx.game.DialogueSystem.SpeakableEntity
import com.mygdx.game.DialogueSystem.convertSpeakableEntity
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.player

class DialogueManager {
    companion object {
        val dialogueMap = mutableMapOf<String,MutableList<Sentence>>()
        val speakableObjectMap = mutableMapOf<SpeakableEntity, GameObject>()

        fun addDialogueText(key: String, entitySpeaking: String, text: String){
            val currentDialogue = dialogueMap.getOrPut(key) { mutableListOf() }
            currentDialogue.add(Sentence(text, convertSpeakableEntity(entitySpeaking)))
        }
        fun initSpeakableObjects(){
            speakableObjectMap[SpeakableEntity.PLAYER] = player
        }
    }
}