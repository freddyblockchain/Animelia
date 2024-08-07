package com.mygdx.game.WorldItems

import com.badlogic.gdx.graphics.Color
import com.mygdx.game.Animation.TextAnimation
import com.mygdx.game.Collition.Collision
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.Collition.OnlyPlayerCollitionMask
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Managers.AnimationManager

abstract class WorldItem (gameObjectData: GameObjectData) : GameObject(gameObjectData) {
    override val collitionMask = OnlyPlayerCollitionMask
    abstract val itemAquiredText: String
    override val collision = ItemAquiredCollision(this)
    override val layer = Layer.ONGROUND

    open fun itemGained(){
        this.remove()
    }
}

class ItemAquiredCollision(val item: WorldItem): MoveCollision() {
    override fun collisionHappened(collidedObject: GameObject) {
        val textAnimation = TextAnimation(
            Color.GREEN,
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