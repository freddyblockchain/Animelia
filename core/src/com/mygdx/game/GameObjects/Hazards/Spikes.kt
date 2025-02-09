package com.mygdx.game.GameObjects.Hazards

import RemoveObjectSignal
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.CannotMoveStrategy.CannotMoveStrategy
import com.mygdx.game.Collisions.AreaEntranceCollition
import com.mygdx.game.Collisions.DefaultAreaEntranceCollition
import com.mygdx.game.Collition.CollisionMask
import com.mygdx.game.Collition.OnlyPlayerCollitionMask
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameModes.AnimationModes.CliffsideAnimationMode
import com.mygdx.game.GameModes.AnimationModes.SkewerAnimationMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.GameObject.FlyingState
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.GameObject.MoveableObject
import com.mygdx.game.GameObjects.GameObject.State
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.Utils.Triggerable
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class Spikes(gameObjectData: GameObjectData) : GameObject(gameObjectData), Triggerable {
    lateinit var goToPosition: AnimeliaPosition
    val retractable = Json.decodeFromJsonElement<SpikesCustomFields>(gameObjectData.customFields).Retractable
    val posEntityRef = Json.decodeFromJsonElement<SpikesCustomFields>(gameObjectData.customFields).Entity_ref
    override val layer = Layer.ONGROUND
    override val texture = DefaultTextureHandler.getTexture("Spike.png")

    override val collision = SpikesCollision(this)

    override val collisionMask = OnlyPlayerCollitionMask

    val maxHeight = 32f
    var currentHeight = maxHeight
    var counter = 0
    val cycleFrames = 96

    val currentFrame: Int
        get() = counter % cycleFrames

    val spikesActive: Boolean
        get() = if(!retractable) true else currentFrame >= 63

    override fun initObject() {
        goToPosition = AreaManager.getObjectWithIid(
            posEntityRef.entityIid,
            posEntityRef.levelIid
        ) as AnimeliaPosition
    }

    override fun frameTask() {
        super.frameTask()

        if(retractable){
            if(currentFrame < 32){
                currentHeight -= 1
            }
            else if(currentFrame in 32..63){
                currentHeight += 1
            }
            else {

            }
        }

            counter += 1


    }

    override fun render(batch: SpriteBatch) {
        if(!retractable){
            renderRepeatedTexture(batch, texture, this.currentPosition(),this.size)
        } else {
            for(i in 0 until this.size.x.toInt() step 16 ){
                for(j in 0 until this.size.y.toInt() step 32){
                    renderRepeatedTexture(batch, texture, this.currentPosition() + Vector2(i.toFloat(), j.toFloat()), Vector2(16f,currentHeight))
                }
            }
           // renderRepeatedTexture(batch, texture, this.currentPosition(), Vector2(16f,currentHeight))
        }
    }

    override fun onTrigger() {
        SignalManager.emitSignal(RemoveObjectSignal(this.gameObjectIid))
    }


}
class SpikesCollision(val spikes: Spikes): DefaultAreaEntranceCollition(){
    override fun actionWhileInside() {
        if(spikes.spikesActive && player.state != State.SHIELDED && player.flyingState != FlyingState.FLYING ){
            //hack right now
            player.state = State.STUNNED
            changeMode(SkewerAnimationMode(mainMode, returningPos = spikes.goToPosition))
            player.currentHealth -= 10
        }
    }

    override var canMoveAfterCollision = true

}

@Serializable
data class SpikesCustomFields(val Retractable: Boolean, val Entity_ref: EntityRefData){

}