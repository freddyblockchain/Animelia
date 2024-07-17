package com.mygdx.game.GameObjects.Hazards

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Collisions.CannotMoveCollision
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Managers.Stats
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class Rock(gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())) {
    override val texture = DefaultTextureHandler.getTexture("MediumRock.png")
    override val layer = Layer.ONGROUND
    override val collision = CannotMoveCollision()
    val customFields = Json.decodeFromJsonElement<RockCustomFields>(gameObjectData.customFields)

    init{
        val rockSize = getSize(this.customFields.StrengthToBreak)
        this.setSize(rockSize)
    }

    fun getSize(strengthToBreak: Int): Vector2{
        return when {
            strengthToBreak >= 15 -> Vector2(32f,32f)
            else -> Vector2(24f,24f)
        }
    }

    fun checkDestroyed(stats: Stats): Boolean{
        return stats.offence >= this.customFields.StrengthToBreak
    }
}

@Serializable
data class RockCustomFields(val StrengthToBreak: Int){

}