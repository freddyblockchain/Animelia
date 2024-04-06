package com.mygdx.game.GameObjects.Memory

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.EntityRefData
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.Managers.AreaManager
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class MemoryStone(val gameObjectData: GameObjectData) : GameObject(gameObjectData, Vector2(16f,16f)) {
    val customFields = Json.decodeFromJsonElement<MemoryStoneCustomFields>(gameObjectData.customFields)
    override val texture = DefaultTextureHandler.getTexture("MemoryStone.png")
    override val layer = Layer.ONGROUND

    lateinit var memoryPad: MemoryPad
    override val collision = MemoryStoneCollision(this)

    override fun initObject() {
        memoryPad = AreaManager.getObjectWithIid(customFields.MemoryPad.entityIid) as MemoryPad
    }
}

@Serializable
data class MemoryStoneCustomFields(val MemoryPad: EntityRefData){

}

class MemoryStoneCollision(val memoryStone: MemoryStone): MoveCollision(){
    override var canMoveAfterCollision = true

    override fun collisionHappened(collidedObject: GameObject) {
        if(collidedObject is Player){
            memoryStone.memoryPad.activateStone()
            AreaManager.getActiveArea()!!.gameObjects.remove(memoryStone)
        }
    }

}