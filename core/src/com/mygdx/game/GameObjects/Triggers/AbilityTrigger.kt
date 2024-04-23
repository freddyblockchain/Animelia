package com.mygdx.game.GameObjects.Triggers

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class AbilityTrigger(val gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2(32f,32f)){
    val abilityFields = Json.decodeFromJsonElement<AbilityCustomFields>(gameObjectData.customFields)
    override val texture = DefaultTextureHandler.getTexture("Box.png")
    override val layer = Layer.PERSON
    override val collision = AbilityCollision(this)
    init {
        polygon.scale(1.5f)
    }
}

class AbilityCollision(val abilityTrigger: AbilityTrigger): MoveCollision(){
    override var canMoveAfterCollision = true

    override fun collisionHappened(collidedObject: GameObject) {
        if(collidedObject is Player){
        }
    }

}

@Serializable
data class AbilityCustomFields(val Ability: String){

}