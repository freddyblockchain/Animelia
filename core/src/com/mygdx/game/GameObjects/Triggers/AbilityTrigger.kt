package com.mygdx.game.GameObjects.Triggers

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Abilities.getAbilityBasedOnEnum
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.player
import kotlinx.serialization.Serializable

class AbilityTrigger(val gameObjectData: AbilityTriggerData)
    : GameObject(gameObjectData, Vector2(32f,32f)){
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
            val currentObjects = AreaManager.getActiveArea()!!.gameObjects
            currentObjects.remove(abilityTrigger)
            val abilityString = abilityTrigger.gameObjectData.customFields.Ability
            val ability = getAbilityBasedOnEnum(abilityString)
            player.abilities.add(ability)
        }
    }

}

@Serializable
data class AbilityTriggerData(
    override val iid: String,
    override val x: Int,
    override var y: Int,
    override val width: Int,
    override val height: Int,
    val customFields: AbilityCustomFields,
): GameObjectData

@Serializable
data class AbilityCustomFields(val Ability: String){

}