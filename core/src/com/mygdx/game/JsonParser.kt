package com.mygdx.game

import com.mygdx.game.DialogueSystem.Dialogue
import com.mygdx.game.FileHandler.Companion.getFileJson
import com.mygdx.game.GameObjectFactory.GetGameObjectsFromJson
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Managers.DialogueManager
import kotlinx.serialization.json.*

class JsonParser {
    companion object {
        fun getRoot(fileName: String): Root{
            val objectStrings = getFileJson(fileName)
            val json = Json { ignoreUnknownKeys = true } // Configure as needed
            return json.decodeFromString<Root>(objectStrings)
        }
        fun getGameObjects(root: Root): List<GameObject>{
            return GetGameObjectsFromJson(root.entities, root);
        }

        fun getArticyDraftEntries(): Map<String, JsonElement>{
            val objectStrings = getFileJson("ArticyDraft/Entries.json")
            val json = Json { ignoreUnknownKeys = true } // Configure as needed
            val result = json.decodeFromString<Map<String, JsonElement>>(objectStrings)
            val dialogues: Map<String, Dialogue> = result.map {
                val jsonObject: JsonObject = it.value as JsonObject
                val emptyStringObject = jsonObject[""]!!.jsonObject
                val textContent = emptyStringObject["Text"]!!.jsonPrimitive.content
                val context = jsonObject["Context"]!!.jsonPrimitive.content

                it.key to Dialogue(Text = textContent, Context = context)
            }.toMap()
            val dialogueFragments = dialogues.filter { it.key.contains("DFr") }
            dialogueFragments.forEach {
                //  example string "Flow/Ch1-First-Conversation/Butler: \"Maybe he is in the garden?\""
                val splittedContext = it.value.Context.split("/")
                val dialogueKey = splittedContext[1]
                val dialogueEntity = splittedContext[2].split(":")[0]

                DialogueManager.addDialogueText(key = dialogueKey, entitySpeaking = dialogueEntity, text = it.value.Text)
            }
            return result
        }
    }
}