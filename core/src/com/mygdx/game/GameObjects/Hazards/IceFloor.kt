package com.mygdx.game.GameObjects.Hazards

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collisions.AreaEntranceCollition
import com.mygdx.game.Collisions.CanMoveCollision
import com.mygdx.game.Collisions.DefaultAreaEntranceCollition
import com.mygdx.game.Collition.OnlyPlayerCollitionMask
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Enums.getDirectionUnitVector
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.player
import com.mygdx.game.renderRepeatedTexture

class IceFloor(gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())){
    override val texture = DefaultTextureHandler.getTexture("SnowTile.png")
    override val layer = Layer.ONGROUND
    override val collision = IceFloorCollision()

    override val collitionMask = OnlyPlayerCollitionMask

    override fun render(batch: SpriteBatch) {
        renderRepeatedTexture(batch, texture, this.currentPosition(), Vector2(sprite.width, sprite.height))
    }
}

class IceFloorCollision(): DefaultAreaEntranceCollition(){
    override var canMoveAfterCollision = true

    override fun actionWhileInside() {
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

}