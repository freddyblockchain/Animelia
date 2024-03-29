package com.mygdx.game.GameObjects.Memory

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Animation.Conversation
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameModes.FlashbackMode
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.currentGameMode
import com.mygdx.game.player
import kotlinx.serialization.Serializable

class MemoryPad(val gameObjectData: MemoryPadData) : GameObject(gameObjectData, Vector2(32f,32f)) {
    override val texture = DefaultTextureHandler.getTexture("Box.png")
    override val layer = Layer.ONGROUND
    private val amountOfStone: Int = gameObjectData.customFields.AmountOfStones
    private var stonesActivateSoFar = 0
    override val collision = MemoryPadCollision(this)
    var activated: Boolean = false


    fun activateStone(){
        stonesActivateSoFar += 1
        if(stonesActivateSoFar == amountOfStone){
            println("Memory pad activated")
            activated = true
        }
    }
}

@Serializable
data class MemoryPadData(
    override val iid: String,
    override val x: Int,
    override var y: Int,
    override val width: Int,
    override val height: Int,
    val customFields: MemoryPadCustomFields
): GameObjectData

@Serializable
data class MemoryPadCustomFields(val AmountOfStones: Int){

}

class MemoryPadCollision(val memoryPad: MemoryPad): MoveCollision(){
    override var canMoveAfterCollision = true

    override fun collisionHappened(collidedObject: GameObject) {
        if(collidedObject is Player && memoryPad.activated){
            AnimationManager.animationManager.add(Conversation("Flashback1 Before") {
                currentGameMode = FlashbackMode(player.currentPosition(), AreaManager.getActiveArea()!!.areaIdentifier)
            })
            memoryPad.activated = false
        }
    }

}
