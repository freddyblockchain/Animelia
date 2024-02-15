package com.mygdx.game.Area

import com.mygdx.game.GameObject.GameObject

class Area(val areaIdentifier: String) {
    val gameObjects = mutableListOf<GameObject>()
}