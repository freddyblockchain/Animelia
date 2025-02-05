package com.mygdx.game.GameObjects.Hazards

import RemoveObjectSignal
import com.badlogic.gdx.graphics.Color
import com.mygdx.game.*
import com.mygdx.game.Animation.TextAnimation
import com.mygdx.game.Collition.InputCollision
import com.mygdx.game.Items.KeyItem
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Managers.SignalManager

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Collisions.AllMoveBackCollision
import com.mygdx.game.Collisions.CannotMoveCollision
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjects.DefaultToggelable
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.Toggelable


class KingDoor(gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())), Toggelable by DefaultToggelable() {
    override val texture = DefaultTextureHandler.getTexture("KingDoor.png")
    override val layer = Layer.ONGROUND
    override val collision = CannotMoveCollision()
    val kingDoorSensor = KingDoorSensor(gameObjectData = GameObjectData(x = gameObjectData.x, y = gameObjectData.y - 32, height = 32, width = 64),this)

    override fun initObject() {
        super.initObject()
        kingDoorSensor.add()
        kingDoorSensor.initObject()
    }

    override fun remove() {
        super.remove()
        kingDoorSensor.remove()
    }

}

class KingDoorSensor(gameObjectData: GameObjectData, kingDoor: KingDoor): GameObject(gameObjectData){
    override val layer = Layer.ONGROUND
    override val collision = KingDoorCollision(kingDoor)

    override fun render(batch: SpriteBatch) {
    }

}
class KingDoorCollision(val kingDoor: KingDoor): InputCollision() {
    override fun collisionHappened(collidedObject: GameObject) {
        if(KeyItem.KINGSEAL in generalSaveState.inventory.keyItems){
            SignalManager.emitSignal(RemoveObjectSignal(kingDoor.gameObjectIid))
        } else{

            val textAnimation = TextAnimation(Color.RED, "You do not have the King Seal", Vector2(kingDoor.currentMiddle.x - 150f, kingDoor.currentMiddle.y - 32f), false)
            if(!AnimationManager.animationManager.any { it is TextAnimation && textAnimation.text.startsWith("You do not")}){
                AnimationManager.animationManager.add(textAnimation)
            }
        }
    }

    override val insideText = "OPEN"
}