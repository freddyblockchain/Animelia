
import com.mygdx.game.BaseClasses.GameObject
import com.mygdx.game.Entities
import com.mygdx.game.FloorButtonData
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.Button
import com.mygdx.game.LockedDoorData
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

object GameObjectFactory {
    private val registry = mutableMapOf<Class<*>, (GameObjectData) -> GameObject>()

    fun register(type: Class<out GameObjectData>, constructorFunction: (GameObjectData) -> GameObject) {
        registry[type] = constructorFunction
    }

    private fun create(data: GameObjectData): GameObject? {
        val constructor = registry[data::class.java]
        return constructor?.invoke(data)
    }

    fun GetGameObjectsFromJson(entities: Entities): List<GameObject> {
        val allEntities = mutableListOf<GameObject>()
        Entities::class.memberProperties.forEach { property ->
            property.isAccessible = true // Make sure we can access the property

            val value = property.get(entities) // Get the property value from the entities instance

            if (value is List<*>) { // Check if the value is a List
                value.forEach { item ->
                    if (item is GameObjectData) { // Check if the item implements GameObjectData
                        // At this point, item is safely cast to GameObjectData
                        // You can now add item to your List<GameObjectData>
                        allEntities.add(create(item)!!)
                    }
                }
            }
        }

        return allEntities
    }

    fun initMappings(){
        this.register(FloorButtonData::class.java) {
            Button(it as FloorButtonData)
        }
        this.register(LockedDoorData::class.java) {
            LockedDoor(it as LockedDoorData)
        }
    }
}