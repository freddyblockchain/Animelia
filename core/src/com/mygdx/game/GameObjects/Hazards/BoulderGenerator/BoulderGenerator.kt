package com.mygdx.game.GameObjects.Hazards.BoulderGenerator

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Animation.EffectAnimation
import com.mygdx.game.Collisions.CannotMoveCollision
import com.mygdx.game.DefaultParticleHandler
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Enums.getDirectionFromString
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.RockProjectile
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Particles.AnimeliaEffect
import com.mygdx.game.Timer.CooldownTimer
import com.mygdx.game.minus
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class BoulderGenerator(gameObjectData: GameObjectData) : GameObject(gameObjectData) {
    override val layer = Layer.ONGROUND
    val customFields = Json.decodeFromJsonElement<BoulderGeneratorCustomFields>(gameObjectData.customFields)
    val direction = getDirectionFromString(customFields.Direction)
    val automatic = customFields.Automatic
    override val collision = CannotMoveCollision()
    override val texture = DefaultTextureHandler.getTexture("BoulderGenerator.png")

    val timer = CooldownTimer(3f)

    var rockThrowCounter = 0
    var rockThrowOngoing = false

    var effect: ParticleEffect = ParticleEffect()

    override fun initObject() {
        effect.load(Gdx.files.internal("Particles/rockstart.p"), Gdx.files.internal("Particles"))
    }


    fun getRocPosition(): Vector2{
        return when (direction){
            Direction.DOWN -> this.bottomleft - Vector2(0f,32f)
            else -> this.bottomleft - Vector2(0f,32f)
        }
    }

    val rockPosition = getRocPosition()

    fun getRockUnitVector(): Vector2{
        return when (direction){
            Direction.DOWN -> Vector2(0f,-1f)
            else -> Vector2(0f,-1f)
        }
    }

    fun triggerGenerator(){
        if(timer.tryUseCooldown()){
            rockThrowOngoing = true
        }
    }

    override fun frameTask() {
        super.frameTask()
        if(automatic){
            triggerGenerator()
        }
        if(rockThrowOngoing){
            if(rockThrowCounter == 0){
                val rockEffect = AnimeliaEffect(effect)
                rockEffect.start()
                rockEffect.particleEffect.emitters.forEach { it.reset() }
                rockEffect.particleEffect.setPosition(rockPosition.x + 16f, rockPosition.y + 32f)
                val animation = EffectAnimation(rockEffect, 25)
                AnimationManager.animationManager.add(animation)
            }
            if(rockThrowCounter == 20){
                val rockProjectile = RockProjectile(GameObjectData(x = rockPosition.x.toInt(), y = rockPosition.y.toInt()), size, getRockUnitVector(), this, 180)
                rockProjectile.speed = 1.5f
                rockProjectile.add()

                rockThrowOngoing = false
                rockThrowCounter = -1
            }
            rockThrowCounter += 1
        }
    }
}
@Serializable
class BoulderGeneratorCustomFields(val Direction: String, val Automatic: Boolean)