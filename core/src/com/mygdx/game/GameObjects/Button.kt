package com.mygdx.game.GameObjects

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.EntityRefData
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObject.GameObject
import com.mygdx.game.GameObjectData
import kotlinx.serialization.Serializable

class Button(val floorButtonData: FloorButtonData): GameObject(floorButtonData, Vector2(32f,32f)) {
    override val texture = DefaultTextureHandler.getTexture("GateButton.png")
    override val layer = Layer.ONGROUND
}


@Serializable
data class FloorButtonData(
    val id: String,
    override val iid: String,
    override val x: Int,
    override var y: Int,
    val customFields: FloorButtonCustomFields
    // Include other relevant fields
) : GameObjectData

@Serializable
data class FloorButtonCustomFields(val Entity_ref: List<EntityRefData>, val Entity_ref2: EntityRefData){

}