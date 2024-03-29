package com.mygdx.game.GameObjects

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjectData
import kotlinx.serialization.Serializable

class Entrance(entranceData: EntranceData): GameObject(entranceData, Vector2(32f,32f)){
    override val texture = DefaultTextureHandler.getTexture("EmptyDoor.png")
    override val layer = Layer.ONGROUND
}

@Serializable
data class EntranceData(
    val id: String,
    override val iid: String,
    override val x: Int,
    override var y: Int,
    override val width: Int,
    override val height: Int,
) : GameObjectData