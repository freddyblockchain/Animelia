package com.mygdx.game.GameObjects.Hazards.ConveyerBelt

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Collisions.DefaultAreaEntranceCollition
import com.mygdx.game.Collition.OnlyPlayerCollitionMask
import com.mygdx.game.Collition.OnlyProjectileCollisionMask
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Enums.getDirectionFromString
import com.mygdx.game.Enums.getDirectionUnitVector
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.Utils.Triggerable
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class ConveyerBelt(gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())),Triggerable {
    override val texture = DefaultTextureHandler.getTexture("sensor.png")
    override val layer = Layer.ONGROUND
    val directionString = Json.decodeFromJsonElement<ConveyerBeltCustomFields>(gameObjectData.customFields).Direction
    var direction = getDirectionFromString(directionString)
    private val isVertical = (direction == Direction.DOWN || direction == Direction.UP)
    private val brickLength = 20f
    private val brickCount = if(isVertical) size.y / brickLength else size.x / brickLength
    val offsetStartBrick = when(direction){
        Direction.DOWN -> Vector2(0f,0f)
        Direction.UP -> Vector2(0f,brickLength)
        Direction.RIGHT -> Vector2(0f,0f)
        Direction.LEFT -> Vector2(brickLength,0f)
    }
    val speed =  0.5f
    val start = initPosition + if(isVertical) Vector2(0f,size.y) else Vector2(0f,0f)
    val end = if(isVertical) initPosition + offsetStartBrick else bottomright - Vector2(brickLength,0f)
    val brickImageFileName = if(isVertical) "ConveyerBrick.png" else "ConveyerBrickDown.png"
    val startBrick = ConveyerBrick(if(isVertical) start + offsetStartBrick else start - offsetStartBrick, if(isVertical) Vector2(size.x,brickLength) else Vector2(brickLength,size.y),speed,direction, DefaultTextureHandler.getTexture(brickImageFileName))
    val endBrick = ConveyerBrick(end, if(isVertical) Vector2(size.x,brickLength) else Vector2(brickLength,size.y),speed,direction, DefaultTextureHandler.getTexture(brickImageFileName))
    val bricks = constructBricks()
    override val collision = ConveyerBeltCollition(this)
    override val collitionMask = OnlyPlayerCollitionMask
    override fun render(batch: SpriteBatch) {
        endBrick.draw(batch)
        startBrick.draw(batch)
        bricks.forEach { it.draw(batch) }
    }

    override fun frameTask() {
        bricks.forEach { it.move() }
        super.frameTask()
    }

    fun constructBricks(): List<ConveyerBrick>{
        val list = mutableListOf<ConveyerBrick>()
        for(i in 0 until brickCount.toInt()){
            val increment = if(isVertical) Vector2(0f, i * brickLength) else Vector2(-(i * brickLength), 0f)
            val brick = ConveyerBrick(start - increment, if(isVertical) Vector2(size.x,brickLength) else Vector2(brickLength,size.y),speed,direction, DefaultTextureHandler.getTexture(brickImageFileName))
            list.add(brick)
        }
        return list.toList()
    }

    override fun onTrigger() {
        if(direction == Direction.LEFT){
            direction = Direction.RIGHT
        }
        else if(direction == Direction.RIGHT){
            direction = Direction.LEFT
        }
        startBrick.direction = direction
        endBrick.direction = direction
        bricks.forEach { it.direction = direction }
    }
}

class ConveyerBeltCollition(val conveyerBelt: ConveyerBelt) : DefaultAreaEntranceCollition() {
    override fun actionWhileInside() {
        player.currentUnitVector = getDirectionUnitVector(conveyerBelt.direction)
        player.setRotation(player.currentUnitVector, player, 90f)
        player.forceMove(2f)
    }

    override fun movedInsideAction(objectEntered: GameObject) {
        player.cannotMoveCount+= 1
        super.movedInsideAction(objectEntered)
    }

    override fun movedOutsideAction(objectLeaved: GameObject) {
        super.movedOutsideAction(objectLeaved)
        player.cannotMoveCount -= 1
        player.setRotation(player.currentUnitVector, player, 90f)
    }

    override var canMoveAfterCollision = true
}

@Serializable
data class ConveyerBeltCustomFields(val Direction: String){

}