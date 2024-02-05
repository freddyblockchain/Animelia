package com.mygdx.game.Interfaces

import com.mygdx.game.BaseClasses.GameObject

interface Collision {
    fun collitionHappened(collidedObject: GameObject)
    fun filterCollitions(gameObjects: List<GameObject>): List<GameObject>{
        return gameObjects
    }
}