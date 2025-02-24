package com.mygdx.game.GameObjects.Hazards

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.*
import com.mygdx.game.Collisions.DefaultAreaEntranceCollition
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Timer.CooldownTimer
import com.mygdx.game.Timer.Timer

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collition.CollisionMask
import com.mygdx.game.Collition.OnlyPlayerCollitionMask
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameModes.AnimationModes.SkewerAnimationMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.GameObject.State
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.Managers.AreaManager
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlin.math.max
import kotlin.math.min

enum class FiregateState{INACTIVE, CLOSING, CLOSED}
class FireGate(gameObjectData: GameObjectData) :
    GameObject(gameObjectData) {
    lateinit var goToPosition: AnimeliaPosition
    val posEntityRef = Json.decodeFromJsonElement<EntityRefCustomFields>(gameObjectData.customFields).Entity_ref
    override val texture = DefaultTextureHandler.getTexture("FiregatePillar.png")
    val brickTexture = DefaultTextureHandler.getTexture("FiregateBrick.png")
    override val layer = Layer.ONGROUND
    val maxTicks = ((size.x - 6f) / 2)
    var counter = 0f;
    var firegateState: FiregateState = FiregateState.INACTIVE
    val timer = CooldownTimer(0.55f)
    var increasing = false

    val counterIncrement = 0.8f

    val firegateCollitionObject = FiregateCollitionObject(this)
    override fun initObject() {
        super.initObject()
        firegateCollitionObject.add()

        goToPosition = AreaManager.getObjectWithIid(
            posEntityRef.entityIid,
            posEntityRef.levelIid
        ) as AnimeliaPosition
    }

    override val collision = FiregateCollition(this)

    override fun frameTask() {
        super.frameTask()
        if(firegateState == FiregateState.CLOSING && timer.tryUseCooldown()){
            firegateState = FiregateState.CLOSED
        }
    }

    init {
        polygon.setScale(1f, 6.5f)
    }

    override fun render(batch: SpriteBatch) {
       // super.render(batch)
        batch.draw(texture, sprite.x, sprite.y)
        batch.draw(texture, sprite.x + size.x - texture.width, sprite.y)

        if(increasing){
            counter = min(counter + counterIncrement, maxTicks)
        } else{
            counter = max(counter - counterIncrement, 0f)
        }
        var currentStepLeft = if(increasing && counter == maxTicks) maxTicks else counter % maxTicks
        renderRepeatedTexture(batch, brickTexture, Vector2(initPosition + Vector2(+ 3f,0f)), Vector2(currentStepLeft, 32f))
        renderRepeatedTexture(batch, brickTexture, Vector2(initPosition + Vector2(this.width - 3f,0f)), Vector2(-currentStepLeft, 32f))
       /* if(firegateState == FiregateState.CLOSED){
            renderRepeatedTexture(batch, brickTexture, Vector2(initPosition + Vector2(3f,0f)), Vector2(maxTicks, 32f))
            renderRepeatedTexture(batch, brickTexture, Vector2(initPosition + Vector2(this.width - 3f,0f)), Vector2(-maxTicks, 32f))
        }*/
    }
}

class FiregateCollition(val fireGate: FireGate): DefaultAreaEntranceCollition(){
    override fun movedInsideAction(objectEntered: GameObject) {
        if(objectEntered is Player){
            fireGate.firegateState = FiregateState.CLOSING
            fireGate.increasing = true
            fireGate.timer.reset()
            fireGate.timer.tryUseCooldown()
        }
    }

    override fun movedOutsideAction(objectLeaved: GameObject) {
        if(objectLeaved is Player){
           // fireGate.firegateState = FiregateState.INACTIVE
            fireGate.increasing = false

        }
    }

    override var canMoveAfterCollision = true

}

class FiregateCollitionObject(fireGate: FireGate) :
    GameObject(GameObjectData(x = fireGate.x.toInt(), y = fireGate.y.toInt(), width = fireGate.width.toInt(), height = fireGate.height.toInt())){
    override val texture = fireGate.texture
    override val layer = fireGate.layer
    override val collisionMask = OnlyPlayerCollitionMask

    override fun frameTask() {

    }

    override fun render(batch: SpriteBatch) {
    }

    override val collision = object: DefaultAreaEntranceCollition() {
        override var canMoveAfterCollision: Boolean = true

        override fun actionWhileInside() {
            if(fireGate.firegateState == FiregateState.CLOSED){
                player.state = State.STUNNED
                changeMode(SkewerAnimationMode(mainMode, returningPos = fireGate.goToPosition))
                player.currentHealth -= 10
            }
        }
    }

}

@Serializable
data class EntityRefCustomFields(val Entity_ref: EntityRefData){

}