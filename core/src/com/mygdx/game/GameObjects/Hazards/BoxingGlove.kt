package com.mygdx.game.GameObjects.Hazards

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.CannotMoveStrategy.CannotMoveStrategy
import com.mygdx.game.CannotMoveStrategy.MoveRegardless
import com.mygdx.game.Collition.CollisionMask
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.Collition.OnlyPlayerCollitionMask
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Enums.getDirectionUnitVector
import com.mygdx.game.GameModes.AnimationModes.CliffsideAnimationMode
import com.mygdx.game.GameModes.AnimationModes.SpinningAnimationMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.GameObject.FlyingState
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.GameObject.MoveableObject
import com.mygdx.game.GameObjects.GameObject.State
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia.AnimeliaCustomFields
import com.mygdx.game.Managers.AreaManager
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class BoxingGlove(gameObjectData: GameObjectData) : MoveableObject(gameObjectData) {

    lateinit var goToPosition: AnimeliaPosition
    val posEntityRef = Json.decodeFromJsonElement<BoxingGloveCustomFields>(gameObjectData.customFields).Entity_ref
    override var speed  = 0.5f
    override val cannotMoveStrategy = MoveRegardless()

    override val texture = DefaultTextureHandler.getTexture("BoxingGlove.png")
    val springTexture = DefaultTextureHandler.getTexture("Spring.png")
    val springSprite = Sprite(springTexture)

    override val collitionMask = OnlyPlayerCollitionMask

    override val collision = BoxingGloveCollsion(this)

    val extendDistance = 64f

    override fun initObject() {
        goToPosition = AreaManager.getObjectWithIid(
            posEntityRef.entityIid,
            posEntityRef.levelIid
        ) as AnimeliaPosition

        springSprite.setPosition(this.x - springSprite.width + 4f, this.y + this.height/4)
    }

    override val layer = Layer.AIR
    override var direction = Direction.RIGHT
    override var canChangeDirection = false

    override fun render(batch: SpriteBatch) {
        super.render(batch)

        renderRepeatedTexture(batch, springTexture, Vector2(springSprite.x,springSprite.y), Vector2(16f + (this.currentPosition().x - this.startingPosition.x) - 4f,16f))
        //springSprite.draw(batch)
    }

    override fun frameTask() {
        super.frameTask()

        if(this.currentPosition().x >= this.startingPosition.x + extendDistance){
            currentUnitVector = getDirectionUnitVector(Direction.LEFT)
        }
        if(this.currentPosition().x <= this.startingPosition.x){
            currentUnitVector = getDirectionUnitVector(Direction.RIGHT)
        }
        this.move(currentUnitVector)
    }
}

class BoxingGloveCollsion(val boxingGlove: BoxingGlove): MoveCollision() {
    override fun collisionHappened(collidedObject: GameObject) {
        if(collidedObject is Player){
            if(player.state == State.NORMAL){
                player.state = State.STUNNED
                changeMode(SpinningAnimationMode(mainMode, endPos = boxingGlove.goToPosition.currentPosition()))
            }
        }
    }

    override var canMoveAfterCollision = true
}

@Serializable
data class BoxingGloveCustomFields(val Entity_ref: EntityRefData){

}