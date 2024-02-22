package com.mygdx.game.GameObjects.Memory

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.EntityRefData
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObject.GameObject
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.Managers.AreaManager
import kotlinx.serialization.Serializable

class MemoryStone(val gameObjectData: MemoryStoneData) : GameObject(gameObjectData, Vector2(16f,16f)) {
    override val texture = DefaultTextureHandler.getTexture("MemoryStone.png")
    override val layer = Layer.ONGROUND

    lateinit var memoryPad: MemoryPad
    override val collision = MemoryStoneCollision(this)

    override fun initObject() {
        memoryPad = AreaManager.getObjectWithIid(gameObjectData.customFields.MemoryPad.entityIid) as MemoryPad
    }
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

@Serializable
data class MemoryStoneData(
    override val iid: String,
    override val x: Int,
    override var y: Int,
    override val width: Int,
    override val height: Int,
    val customFields: MemoryStoneCustomFields
): GameObjectData

@Serializable
data class MemoryStoneCustomFields(val MemoryPad: EntityRefData){

}