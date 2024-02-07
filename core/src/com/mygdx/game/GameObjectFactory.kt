package com.mygdx.game
import LockedDoor
import com.mygdx.game.GameObject.GameObject
import com.mygdx.game.GameObjects.Button
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

object GameObjectFactory {
    private val registry = mutableMapOf<Class<*>, (GameObjectData) -> GameObject>()

    fun register(type: Class<out GameObjectData>, constructorFunction: (GameObjectData) -> GameObject) {
        registry[type] = constructorFunction
    }

    private fun create(data: GameObjectData, height: Int): GameObject? {
        val constructor = registry[data::class.java]
        data.y = height - data.y - 32
        return constructor?.invoke(data)
    }

    fun GetGameObjectsFromJson(entities: Entities, height: Int): List<GameObject> {
        val allEntities = mutableListOf<GameObject>()
        Entities::class.memberProperties.forEach { property ->
            property.isAccessible = true // Make sure we can access the property

            val value = property.get(entities) // Get the property value from the entities instance

            if (value is List<*>) { // Check if the value is a List
                value.forEach { item ->
                    if (item is GameObjectData) { // Check if the item implements GameObjectData
                        // At this point, item is safely cast to GameObjectData
                        // You can now add item to your List<GameObjectData>
                        allEntities.add(create(item, height)!!)
                    }
                }
            }
        }

        return allEntities
    }

    fun initMappings(){
        register(FloorButtonData::class.java) {
            Button(it as FloorButtonData)
        }
        register(LockedDoorData::class.java) {
            LockedDoor(it as LockedDoorData)
        }
    }
}