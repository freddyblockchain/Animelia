package com.mygdx.game.Area

import com.mygdx.game.GameObjects.GameObject.GameObject

class Area(val areaIdentifier: String) {
    val gameObjects = mutableListOf<GameObject>()
}