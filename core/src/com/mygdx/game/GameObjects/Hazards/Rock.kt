package com.mygdx.game.GameObjects.Hazards

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Animation.EffectAnimation
import com.mygdx.game.Animation.TextAnimation
import com.mygdx.game.Collisions.CannotMoveCollision
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Managers.Stats
import com.mygdx.game.Particles.AnimeliaEffect
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class Rock(gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())) {
    override val texture = DefaultTextureHandler.getTexture("MediumRock.png")
    override val layer = Layer.ONGROUND
    override val collision = CannotMoveCollision()
    val customFields = Json.decodeFromJsonElement<RockCustomFields>(gameObjectData.customFields)
    val particleEffect = DefaultParticleHandler.getParticle("rockexplode.p")
    val rockEffect = AnimeliaEffect(particleEffect)

    val rockDestroyedSoundEffect = DefaultSoundHandler.getSound("Sound/rock_break.ogg")

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
        val isDestroyed = stats.offence >= this.customFields.StrengthToBreak

        if(!isDestroyed){
            val textAnimation = TextAnimation(Color.LIGHT_GRAY, "requires ${this.customFields.StrengthToBreak} offence", this.currentMiddle + Vector2(this.width, 0f), false, 120)
            if(!AnimationManager.animationManager.any { it is TextAnimation && textAnimation.text.startsWith("requires")}){
                AnimationManager.animationManager.add(textAnimation)
            }
        } else{

            activateDestroyedEffect()
        }
        return stats.offence >= this.customFields.StrengthToBreak
    }

    fun activateDestroyedEffect(){
        rockEffect.start()
        rockEffect.particleEffect.emitters.forEach { it.reset()
        }
        rockEffect.particleEffect.setPosition(this.currentMiddle.x, this.currentMiddle.y)
        val animation = EffectAnimation(rockEffect, 60)
        AnimationManager.animationManager.add(animation)
        val id = rockDestroyedSoundEffect.play()
        rockDestroyedSoundEffect.setVolume(id,0.2f)
    }
}

@Serializable
data class RockCustomFields(val StrengthToBreak: Int){

}