package com.mygdx.game

import com.mygdx.game.GameObjectFactory.GetGameObjectsFromJson
import com.mygdx.game.GameObject.GameObject
import com.mygdx.game.FileHandler.Companion.getFileJson
import kotlinx.serialization.json.Json

class JsonParser {
    companion object {
        fun parseJson(fileName: String): List<GameObject> {

            val objectStrings = getFileJson(fileName)
            val json = Json { ignoreUnknownKeys = true } // Configure as needed
            val root = json.decodeFromString<Root>(objectStrings)
            return GetGameObjectsFromJson(root.entities, root.height);
        }
    }
}