package com.mygdx.game.Area

import com.badlogic.gdx.audio.Music
import com.mygdx.game.DefaultMusicHandler
import com.mygdx.game.GameObjects.GameObject.GameObject

enum class AreaType{Fire,Ice, Normal}

class Area(val areaIdentifier: String) {
    val gameObjects = mutableListOf<GameObject>()
    val associatedLevels: MutableList<String> = mutableListOf()
}
fun getPrettyAreaName(areaIdentifier: String): String{
    return when(areaIdentifier){
        "World1" -> "Central City"
        "World2" -> "Canyon"
        "World3" -> "Firelands"
        "World4" -> "Icelands"
        "Swamp0" -> "Forest"
        "Metal_Factory" -> "Metal Factory"
        "SpiritGrounds" -> "Spirit Grounds"
        "FrogHouse","FrogHouse_City" -> "King House"
        "Iglo","Iglo_City" -> "Iglo"
        "Abandoned_House" -> "Abandoned Building"
        "Vulcano" -> "Vulcano"
        "Ice_castle" -> "Ice Castle"
        else -> areaIdentifier
    }

}
fun getAreaMusic(areaIdentifier: String): Music{
    val musicString =  when(areaIdentifier){
        "World1" -> "Music/Snow City Theme/snow_city.mp3"
        "World3" -> "Music/Stone Fortress/stone fortress.mp3"
        "World4"-> "Music/Snowy Music/snowytheme.ogg"
        else -> "Music/Nature Theme sketch/nature sketch.wav"
    }
    return DefaultMusicHandler.getMusic(musicString)
}

fun getAreaType(areaIdentifier: String): AreaType {
      return when(areaIdentifier){
        "World1" -> AreaType.Normal
        "World3" -> AreaType.Fire
        "World4"-> AreaType.Ice
        else -> AreaType.Normal
    }
}