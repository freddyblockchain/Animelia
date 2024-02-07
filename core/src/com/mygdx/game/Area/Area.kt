package com.mygdx.game.Area

import com.mygdx.game.GameObject.GameObject

class Area(val areaIdentifier: AreaIdentifier) {
    val gameObjects = mutableListOf<GameObject>()
}

enum class AreaIdentifier {Level_0, Level_1}