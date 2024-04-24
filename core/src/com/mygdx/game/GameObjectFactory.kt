package com.mygdx.game
import com.mygdx.game.GameObjects.GameObject.GameObject
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

object GameObjectFactory {
    private val registry = mutableMapOf<String, (GameObjectData) -> GameObject>()

    fun register(type: String, constructorFunction: (GameObjectData) -> GameObject) {
        registry[type] = constructorFunction
    }

    private fun create(data: GameObjectData, root: Root): GameObject? {
        val constructor = registry[data.id]
        data.x += root.x
        data.y = root.height - data.y - data.height
        data.y += (-root.y) - root.height
        return constructor?.invoke(data)
    }

    fun GetGameObjectsFromJson(entities: Entities, root: Root): List<GameObject> {
        val allEntities = mutableListOf<GameObject>()
        Entities::class.memberProperties.forEach { property ->
            property.isAccessible = true // Make sure we can access the property

            val value = property.get(entities) // Get the property value from the entities instance

            if (value is List<*>) { // Check if the value is a List
                value.forEach { item ->
                    if (item is GameObjectData) { // Check if the item implements GameObjectData
                        // At this point, item is safely cast to GameObjectData
                        // You can now add item to your List<GameObjectData>
                        allEntities.add(create(item, root)!!)
                    }
                }
            }
        }

        return allEntities
    }
}