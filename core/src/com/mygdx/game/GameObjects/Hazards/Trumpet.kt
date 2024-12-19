package com.mygdx.game.GameObjects.Hazards

import RemoveObjectSignal
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Collisions.CannotMoveCollision
import com.mygdx.game.Collisions.DefaultAreaEntranceCollition
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.Collition.OnlyPlayerCollitionMask
import com.mygdx.game.EntityRefCustomFields
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Enums.getDirectionFromString
import com.mygdx.game.Enums.getDirectionUnitVector
import com.mygdx.game.GameModes.AnimationModes.CliffsideAnimationMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.GameObject.FlyingState
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.GameObject.State
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.Projectile
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.RockProjectile
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.SoundProjectile
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.SignalManager
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class Trumpet(gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())) {
    override val texture = DefaultTextureHandler.getTexture("trumpet.png")

    val customFields = Json.decodeFromJsonElement<TrumpetCustomFields>(gameObjectData.customFields)
    val direction = getDirectionFromString(customFields.Direction)

    override val layer = Layer.ONGROUND
    override val collision = TrumpetCollision(this)

    override fun initObject() {
        super.initObject()
        if(this.direction == Direction.LEFT){
            this.sprite.rotation = 180f
        }
    }

    fun getOffset(): Vector2{
        if(direction == Direction.RIGHT){
            return Vector2(this.width, 0f)
        }
        return Vector2(0f,0f)
    }
}

@Serializable
class TrumpetCustomFields(val Direction: String)

class TrumpetCollision(val trumpet: Trumpet): MoveCollision(){
    override var canMoveAfterCollision = false

    override fun collisionHappened(collidedObject: GameObject) {
        if(collidedObject is SoundProjectile){
            collidedObject.remove()
            val offset = trumpet.getOffset()
            val vec2 = Vector2(trumpet.x + offset.x, trumpet.y + offset.y)
            val newProjectile = SoundProjectile(GameObjectData(x = vec2.x.toInt(), y = vec2.y.toInt()), Vector2(16f,32f), getDirectionUnitVector(trumpet.direction), trumpet)
            newProjectile.add()
        }
        if(collidedObject is RockProjectile){
            SignalManager.emitSignal(RemoveObjectSignal(this.trumpet.gameObjectIid))
            collidedObject.remove()
        }
    }
}