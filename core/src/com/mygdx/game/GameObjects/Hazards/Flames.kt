package com.mygdx.game.GameObjects.Hazards

import RemoveObjectSignal
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.Icicle
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.Particles.AnimeliaEffect
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class Flames(gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())) {
    override val texture = DefaultTextureHandler.getTexture("sensor.png")
    override val layer = Layer.ONGROUND

    lateinit var goToPosition: AnimeliaPosition
    val posEntityRef = Json.decodeFromJsonElement<FlamesCustomFields>(gameObjectData.customFields).Position

    var effect: ParticleEffect = ParticleEffect()
    lateinit var animeliaEffect: AnimeliaEffect

    override val collision = FlamesCollision(this)

    override fun initObject() {
        super.initObject()
        effect.load(Gdx.files.internal("Particles/flames.p"), Gdx.files.internal("Particles"))
        animeliaEffect = AnimeliaEffect(effect)
        animeliaEffect.particleEffect.setPosition(this.currentMiddle.x, this.currentMiddle.y)
        animeliaEffect.particleEffect.start()

        goToPosition = AreaManager.getObjectWithIid(
            posEntityRef.entityIid,
            posEntityRef.levelIid
        ) as AnimeliaPosition

    }

    override fun render(batch: SpriteBatch) {
        animeliaEffect.render(batch)
    }
}

@Serializable
class FlamesCustomFields(val Position: EntityRefData)
class FlamesCollision(val flames: Flames): MoveCollision() {
    override var canMoveAfterCollision = true

    override fun collisionHappened(collidedObject: GameObject) {
        if(collidedObject is Icicle){
            SignalManager.emitSignal(RemoveObjectSignal(flames.gameObjectIid))
            collidedObject
        }
        if(collidedObject is Player){
            player.setPosition(flames.goToPosition.currentPosition())
        }
    }

}