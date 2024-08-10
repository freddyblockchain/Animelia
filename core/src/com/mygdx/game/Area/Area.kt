package com.mygdx.game.Area

import com.badlogic.gdx.audio.Music
import com.mygdx.game.DefaultMusicHandler
import com.mygdx.game.GameObjects.GameObject.GameObject

class Area(val areaIdentifier: String) {
    val gameObjects = mutableListOf<GameObject>()
    val associatedLevels: MutableList<String> = mutableListOf()
}
fun getAreaMusic(areaIdentifier: String): Music{
    val musicString =  when(areaIdentifier){
        "World1" -> "Music/Snow City Theme/snow_city.mp3"
        "World3" -> "Music/Stone Fortress/stone fortress.ogg"
        else -> "Music/Nature Theme sketch/nature sketch.wav"
    }
    return DefaultMusicHandler.getMusic(musicString)
}