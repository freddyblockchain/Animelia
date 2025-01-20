package com.mygdx.game.GameObjects.Hazards

import RemoveObjectSignal
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Ability.Abilities.Metal.MissileAbility
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.Collition.OnlyTheseObjectsCollisionMask
import com.mygdx.game.Collition.OnlyThisObjectCollisionMask
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Enums.getDirectionFromString
import com.mygdx.game.Enums.getDirectionUnitVector
import com.mygdx.game.GameModes.AnimationModes.FireAnimationMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.GameObject.State
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.Missile
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.MissileAggroBox
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.Utils.Triggerable
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class TargetCircle(gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())){
    val customFields = Json.decodeFromJsonElement<TargetCircleCustomFields>(gameObjectData.customFields)
    val direction = getDirectionFromString(customFields.Direction)
    var unitVectorDirection = getDirectionUnitVector(direction)
    val range = 180

    var currentDistance = 0
    var speed = customFields.Speed

    lateinit var triggerable: Triggerable
    var otherCircle: TargetCircle? = null

    override val texture = DefaultTextureHandler.getTexture("TargetCircle.png")
    override val layer = Layer.AIR
    override val collision = TargetCircleCollision(this)
    override val collisionMask = OnlyTheseObjectsCollisionMask(listOf(MissileAggroBox::class.java, Missile::class.java))
    var activated = false

    fun triggerActivated(){
        this.activated = true
        //Hack so it only saves when all the targets are hit
        this.remove()
        if(otherCircle == null){
            triggerable.onTrigger()
            SignalManager.emitSignal(RemoveObjectSignal(this.gameObjectIid))
        }

        else if(otherCircle != null && otherCircle!!.activated){
            triggerable.onTrigger()
            SignalManager.emitSignal(RemoveObjectSignal(this.gameObjectIid))
            SignalManager.emitSignal(RemoveObjectSignal(otherCircle!!.gameObjectIid))
        }
    }

    override fun frameTask() {
        super.frameTask()

        this.setPosition(this.currentPosition() + this.unitVectorDirection * speed.toFloat())
        currentDistance += speed
        if(currentDistance >= range){
            this.unitVectorDirection = -this.unitVectorDirection
            currentDistance = 0
        }

    }

    override fun initObject() {
        triggerable = AreaManager.getObjectWithIid(
            customFields.Triggerable.entityIid,
            customFields.Triggerable.levelIid
        ) as Triggerable
        if(customFields.TargetCircle != null){
            otherCircle = AreaManager.getObjectWithIid(
                customFields.TargetCircle.entityIid,
                customFields.TargetCircle.levelIid
            ) as TargetCircle
        }
    }
}

@Serializable
class TargetCircleCustomFields(val Range: Int, val Triggerable: EntityRefData, val TargetCircle: EntityRefData?, val Direction: String, val Speed: Int)

class TargetCircleCollision(val targetCircle: TargetCircle): MoveCollision(){
    override var canMoveAfterCollision = true

    override fun collisionHappened(collidedObject: GameObject) {
        if(collidedObject is Missile){
            collidedObject.remove()
            targetCircle.triggerActivated()
        }
    }

}