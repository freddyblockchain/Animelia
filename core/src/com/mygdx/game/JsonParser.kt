package com.mygdx.game

import com.mygdx.game.DialogueSystem.Dialogue
import com.mygdx.game.FileHandler.Companion.getFileJson
import com.mygdx.game.GameObject.GameObject
import com.mygdx.game.GameObjectFactory.GetGameObjectsFromJson
import kotlinx.serialization.json.*

class JsonParser {
    companion object {
        fun getRoot(fileName: String): Root{
            val objectStrings = getFileJson(fileName)
            val json = Json { ignoreUnknownKeys = true } // Configure as needed
            return json.decodeFromString<Root>(objectStrings)
        }
        fun getGameObjects(root: Root): List<GameObject>{
            return GetGameObjectsFromJson(root.entities, root.height);
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
            return result
        }

        fun preprocessJson(jsonString: String): String {
            val jsonObject = Json.parseToJsonElement(jsonString).jsonObject
            val modifiedObject = buildJsonObject {
                jsonObject.forEach { (key, value) ->
                    val newKey = if (key.isEmpty()) "Textholder" else key
                    put(newKey, value)
                }
            }
            return modifiedObject.toString()
        }
    }
}