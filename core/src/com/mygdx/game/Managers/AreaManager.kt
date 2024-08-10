package com.mygdx.game.Managers

import com.mygdx.game.Area.Area
import com.mygdx.game.Area.getAreaMusic
import com.mygdx.game.Collition.CollisionType
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.addObjectsToArea
import com.mygdx.game.getObjectsFromLevelName
import com.mygdx.game.player

class AreaManager {
    companion object {
        val areas = mutableListOf<Area>()
        private var activeArea: Area? = null
        val uniqueIdToLevelPathMap: MutableMap<String, String> = mutableMapOf()
        val levelToAreaMap: MutableMap<String, String> = mutableMapOf()

        fun setActiveArea(areaIdentifier: String){
            val areaWithIdentifier = areas.find { it.areaIdentifier == areaIdentifier }
            activeArea = areaWithIdentifier
        }
        fun getActiveArea(): Area?{
            return activeArea
        }

        fun getArea(areaIdentifier: String): Area{
            return areas.first {it.areaIdentifier == areaIdentifier}
        }

        fun getObjectsWithCollisionType(collisionType: CollisionType): List<GameObject>{
            val activeObjects = activeArea!!.gameObjects

            return activeObjects.filter { it.collision.collitionType == collisionType }
        }

        fun getObjectWithIid(entityId: String, levelId: String): GameObject {
            //Check objects in current area
            if(activeArea!!.gameObjects.any { it.gameObjectIid == entityId }){
                return activeArea!!.gameObjects.first { it.gameObjectIid == entityId }
            }
            // If object is in another area, create copy.
            val levelName = uniqueIdToLevelPathMap[levelId]!!
            val getLevelObjects = getObjectsFromLevelName(levelName)
            return getLevelObjects.filter { it.gameObjectIid == entityId }.first()
        }

        fun changeActiveArea(areaIdentifier: String){
            //cleanup old area
            val currentArea = activeArea!!
            currentArea.gameObjects.clear()

            //activate new area
            setActiveArea(areaIdentifier)
            val newArea = activeArea!!
            newArea.associatedLevels.forEach {
                val newObjects = getObjectsFromLevelName(it)
                addObjectsToArea(newArea, newObjects)
            }
            newArea.gameObjects.forEach { it.initObject() }
            newArea.gameObjects.add(player)

            val music = getAreaMusic(areaIdentifier)
            MusicManager.changeAndPlay(music)
        }
    }
}