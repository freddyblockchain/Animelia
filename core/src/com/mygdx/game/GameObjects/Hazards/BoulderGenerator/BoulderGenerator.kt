package com.mygdx.game.GameObjects.Hazards.BoulderGenerator

import RemoveObjectSignal
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Animation.EffectAnimation
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Enums.getDirectionFromString
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.Missile
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.Projectile
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.RockProjectile
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.Particles.AnimeliaEffect
import com.mygdx.game.Utils.Triggerable
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class BoulderGenerator(gameObjectData: GameObjectData) : GameObject(gameObjectData), Triggerable {
    override val layer = Layer.ONGROUND
    val customFields = Json.decodeFromJsonElement<BoulderGeneratorCustomFields>(gameObjectData.customFields)
    val direction = getDirectionFromString(customFields.Direction)
    val automatic = customFields.Automatic
    override val collision = BoulderGeneratorCollision(this)
    override val texture = DefaultTextureHandler.getTexture("BoulderGenerator.png")

   // val timer = CooldownTimer(customFields.Cooldown.toFloat())
    var currentFrame = 0
    val frames = customFields.Cooldown * 60

    var rockThrowCounter = 0
    var rockThrowOngoing = false

    var effect: ParticleEffect = ParticleEffect()

    init {
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    override fun initObject() {
        effect.load(Gdx.files.internal("Particles/rockstart.p"), Gdx.files.internal("Particles"))

    }


    fun getRocPosition(): Vector2{
        return when (direction){
            Direction.DOWN -> this.bottomleft - Vector2(0f,32f)
            Direction.RIGHT -> this.bottomright
            Direction.LEFT -> this.bottomleft + Vector2(-32f,0f)
            else -> this.bottomleft - Vector2(0f,32f)
        }
    }

    val rockPosition = getRocPosition()

    fun getRockUnitVector(): Vector2{
        return when (direction){
            Direction.DOWN -> Vector2(0f,-1f)
            Direction.RIGHT -> Vector2(1f,0f)
            Direction.LEFT -> Vector2(-1f,0f)
            else -> Vector2(0f,-1f)
        }
    }


    fun triggerGenerator(){
        rockThrowOngoing = true
        currentFrame = 0
    }
    fun checkShouldShoot(){
        if(currentFrame >= frames){
            triggerGenerator()
        }
    }

    override fun frameTask() {
        super.frameTask()
        currentFrame += 1
        if(automatic){
            checkShouldShoot()
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
                var projectile: Projectile? = null
                if(this.customFields.ProjectileType == "Boulder") {
                    projectile = RockProjectile(GameObjectData(x = rockPosition.x.toInt(), y = rockPosition.y.toInt()), size, getRockUnitVector(), this, 180)
                } else{
                    projectile = Missile(GameObjectData(x = rockPosition.x.toInt(), y = rockPosition.y.toInt()), size, getRockUnitVector(), this, 180)
                    (projectile as Missile).missileAggroRadius.speed = 1.5f
                }
                projectile.speed = 1.5f
                projectile.add()

                rockThrowOngoing = false
                rockThrowCounter = -1
            }
            rockThrowCounter += 1
        }
    }

    override fun onTrigger() {
        triggerGenerator()
    }
}
@Serializable
class BoulderGeneratorCustomFields(val Direction: String, val Automatic: Boolean, val ProjectileType: String, val Cooldown: Int)

class BoulderGeneratorCollision(val boulderGenerator: BoulderGenerator): MoveCollision(){
    override var canMoveAfterCollision = false
    override fun collisionHappened(collidedObject: GameObject) {
        if(collidedObject is RockProjectile || collidedObject is Missile){
            SignalManager.emitSignal(RemoveObjectSignal(boulderGenerator.gameObjectIid))
        }
    }

}