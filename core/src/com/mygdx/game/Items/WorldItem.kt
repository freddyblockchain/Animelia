package com.mygdx.game.Items

import RemoveObjectSignal
import com.badlogic.gdx.graphics.Color
import com.mygdx.game.Animation.TextAnimation
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.Collition.OnlyPlayerCollitionMask
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Managers.SignalManager

abstract class WorldItem (gameObjectData: GameObjectData) : GameObject(gameObjectData) {
    override val collisionMask = OnlyPlayerCollitionMask
    abstract val itemAquiredText: String
    override val collision = ItemAquiredCollision(this)
    override val layer = Layer.AIR

    open fun itemGained(){
        SignalManager.emitSignal(RemoveObjectSignal(this.gameObjectIid))
    }
}

class ItemAquiredCollision(val item: WorldItem): MoveCollision() {
    override fun collisionHappened(collidedObject: GameObject) {
        val textAnimation = TextAnimation(
            Color.WHITE,
            item.itemAquiredText,
            item.currentMiddle,
            false,
            120
        )
        AnimationManager.animationManager.add(textAnimation)

        item.itemGained()
    }

    override var canMoveAfterCollision = true
}