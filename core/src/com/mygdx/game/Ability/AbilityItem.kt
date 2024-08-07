package com.mygdx.game.Ability

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Animation.TextAnimation
import com.mygdx.game.Collition.Collision
import com.mygdx.game.Collition.CollisionMask
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.Collition.OnlyPlayerCollitionMask
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.plus
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class AbilityItem(gameObjectData: GameObjectData) : GameObject(gameObjectData) {
    override val layer = Layer.ONGROUND

    val abilityString = Json.decodeFromJsonElement<AbilityCustomFields>(gameObjectData.customFields).Ability
    val abilityType = convertAbilityToType(abilityString)
    val abilityData = convertAbilityTypeToData(abilityType)
    override val collitionMask = OnlyPlayerCollitionMask
    override val collision = AbilityItemCollision(this)
    override val texture = DefaultTextureHandler.getTexture(abilityData.imageIcon)

    init {
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

}

@Serializable
data class AbilityCustomFields(val Ability: String){

}

class AbilityItemCollision(val abilityItem: AbilityItem): MoveCollision(){
    override var canMoveAfterCollision = true

    override fun collisionHappened(collidedObject: GameObject) {
        abilityItem.remove()
        val textAnimation = TextAnimation(
            Color.GREEN,
            "You Gained the ${abilityItem.abilityType.name} Ability",
            abilityItem.currentMiddle,
            false,
            120
        )
        AnimationManager.animationManager.add(textAnimation)

        if (abilityItem.abilityType == AbilityType.Fireball) {
            val textAnimation2 = TextAnimation(
                Color.WHITE,
                "Press Escape to switch abilities",
                abilityItem.currentMiddle + Vector2(-75f, 100f),
                false,
                420
            )
            AnimationManager.animationManager.add(textAnimation2)

        }
    }
}
