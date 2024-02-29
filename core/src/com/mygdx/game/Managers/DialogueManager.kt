package com.mygdx.game.Managers

import com.mygdx.game.DialogueSystem.Sentence
import com.mygdx.game.DialogueSystem.convertSpeakableEntity

class DialogueManager {
    companion object {
        private val dialogueMap = mutableMapOf<String,MutableList<Sentence>>()

        fun addDialogueText(key: String, entitySpeaking: String, text: String){
            val currentDialogue = dialogueMap.getOrPut(key) { mutableListOf() }
            currentDialogue.add(Sentence(text, convertSpeakableEntity(entitySpeaking)))
        }
    }
}