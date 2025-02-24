package com.mygdx.game.Area

import com.badlogic.gdx.audio.Music
import com.mygdx.game.*
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.Structures.Fountain
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.AreaManager.Companion.levelNameToLevelId
import com.mygdx.game.Managers.AreaManager.Companion.levelVisibleMap

enum class AreaType { Fire, Ice, Normal }

class Area(val areaIdentifier: String) {
    val gameObjects = mutableListOf<GameObject>()
    val associatedLevels: MutableList<String> = mutableListOf()

    fun initArea(areaIdentifier: String) {
        var fountainAreaString = ""
        if (areaIdentifier == "Ice_castle") {
            fountainAreaString = "World4"
        }
        if (areaIdentifier == "Vulcano") {
            fountainAreaString = "World3"
        }
        if (areaIdentifier == "Abandoned_House") {
            fountainAreaString = "World2"
        }

        if (fountainAreaString != "") {
            val fountainArea = AreaManager.getArea(fountainAreaString)

            fountainArea.associatedLevels.forEach {
                val levelId = levelNameToLevelId[it]
                if (levelVisibleMap[levelId]!!) {
                    val newObjects = getObjectsFromLevelName(it)
                    val fountain = newObjects.filterIsInstance<Fountain>().firstOrNull()
                    fountain?.let {
                        generalSaveState.lastReincarnationLevelId = fountain.levelId
                        generalSaveState.lastReincarnationEntityId = fountain.gameObjectIid
                        generalSaveState.updateSaveState()
                    }
                }
            }

        }
    }

}

fun getPrettyAreaName(areaIdentifier: String): String {
    return when (areaIdentifier) {
        "World1" -> "Central City"
        "World2" -> "Canyon"
        "World3" -> "Firelands"
        "World4" -> "Icelands"
        "Swamp0" -> "Forest"
        "Metal_Factory" -> "Metal Factory"
        "SpiritGrounds" -> "Spirit Grounds"
        "FrogHouse", "FrogHouse_City" -> "King House"
        "Iglo", "Iglo_City" -> "Iglo"
        "Abandoned_House" -> "Abandoned Building"
        "Vulcano" -> "Vulcano"
        "Ice_castle" -> "Ice Castle"
        else -> areaIdentifier
    }

}

fun getAreaMusic(areaIdentifier: String): Music {
    val musicString = when (areaIdentifier) {
        "World1" -> "Music/Snow City Theme/snow_city.mp3"
        "World3" -> "Music/Stone Fortress/stone fortress.mp3"
        "World4" -> "Music/Snowy Music/snowytheme.ogg"
        "World2" -> "Music/Wind/wind1.wav"
        "Swamp0" -> "Music/Forest/forest.mp3"
        "Ice_castle" -> "Music/No More Magic/No More Magic.mp3"
        "Metal_Factory" -> "Music/Futuristic Ambience/Futuristic_ambient_4.ogg"
        "Abandoned_House" -> "Music/Dark Ambience/Socapex - Dark ambiance_3.mp3"
        "Vulcano" -> "Music/Stone Fortress/stone fortress.mp3"
        else -> "Music/Nature Theme sketch/nature sketch.wav"
    }
    return DefaultMusicHandler.getMusic(musicString)
}

fun getAreaType(areaIdentifier: String): AreaType {
    return when (areaIdentifier) {
        "World1" -> AreaType.Normal
        "World3" -> AreaType.Fire
        "World4" -> AreaType.Ice
        else -> AreaType.Normal
    }
}