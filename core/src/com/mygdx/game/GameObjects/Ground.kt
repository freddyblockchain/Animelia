package com.mygdx.game.GameObjects

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collisions.DefaultAreaEntranceCollition
import com.mygdx.game.Collition.CollisionMask
import com.mygdx.game.Collition.OnlyPlayerCollitionMask
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.generalSaveState

class Ground(gameObjectData: GameObjectData, size: Vector2, textureName: String) : GameObject(gameObjectData, size) {

    override val texture = DefaultTextureHandler.getTexture(textureName)
    override val layer = Layer.GROUND
    override val collisionMask = OnlyPlayerCollitionMask

    override val collision = GroundCollision(this)
}

class GroundCollision(val ground: Ground):DefaultAreaEntranceCollition(){
    override var canMoveAfterCollision = true

    override fun movedInsideAction(objectEntered: GameObject) {
        if(ground.levelId !in generalSaveState.levelsVisited){
            generalSaveState.levelsVisited.add(ground.levelId)
            generalSaveState.updateSaveState()
        }
    }

}