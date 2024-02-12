package com.mygdx.game.GameObjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObject.GameObject
import com.mygdx.game.GameObjectData
import kotlinx.serialization.Serializable

class LockedDoor(val lockedDoorData: LockedDoorData): GameObject(lockedDoorData, Vector2(32f,32f)) {
    override val texture = DefaultTextureHandler.getTexture("EmptyDoor.png")
    override val layer = Layer.ONGROUND
    var unlocked = false

    fun unlockDoor(){
        unlocked = true
    }

    override fun render(batch: SpriteBatch) {
        //Don't show the lock when unlocked
        if(!unlocked){
            super.render(batch)
        }
    }
}

@Serializable
data class LockedDoorData(
    val id: String,
    override val iid: String,
    override val x: Int,
    override var y: Int
    // Include other relevant fields
): GameObjectData