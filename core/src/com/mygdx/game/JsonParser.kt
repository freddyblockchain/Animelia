package com.mygdx.game

import com.mygdx.game.FileHandler.Companion.getFileJson
import com.mygdx.game.GameObject.GameObject
import com.mygdx.game.GameObjectFactory.GetGameObjectsFromJson
import kotlinx.serialization.json.Json

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
    }
}