package com.mygdx.game.Managers

import com.mygdx.game.Area.Area
import com.mygdx.game.Collition.CollisionType
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.player

class AreaManager {
    companion object {
        val areas = mutableListOf<Area>()
        private var activeArea: Area? = null

        fun setActiveArea(areaIdentifier: String){
            val areaWithIdentifier = areas.find { it.areaIdentifier == areaIdentifier }
            activeArea = areaWithIdentifier
        }
        fun getActiveArea(): Area?{
            return activeArea
        }

        fun getObjectsWithCollisionType(collisionType: CollisionType): List<GameObject>{
            val activeObjects = activeArea!!.gameObjects

            return activeObjects.filter { it.collision.collitionType == collisionType }
        }

        fun getObjectWithIid(iidToFind: String): GameObject {
            val allObjects = areas.flatMap { it.gameObjects }
            return allObjects.filter { it.gameObjectIid == iidToFind }.first()
        }

        fun changeActiveArea(areaIdentifier: String){
            //cleanup old area
            val currentArea = activeArea!!
            currentArea.gameObjects.remove(player)

            //activate new area
            setActiveArea(areaIdentifier)
            val newArea = activeArea!!
            newArea.gameObjects.add(player)
        }
    }
}