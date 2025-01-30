package com.mygdx.game.GameObjects.Hazards

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Collisions.AllMoveBackCollision
import com.mygdx.game.Collisions.CannotMoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.DefaultToggelable
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.Toggelable
import com.mygdx.game.player
import com.mygdx.game.renderRepeatedTexture


class TriggerGate(gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())), Toggelable by DefaultToggelable() {
    override val texture = DefaultTextureHandler.getTexture("EmptyDoor.png")
    override val layer = Layer.ONGROUND
    override val collision = CannotMoveCollision()


    override fun frameTask() {
        super.frameTask()

        if(player.animeliaInfo.animeliaEntity == ANIMELIA_ENTITY.FireArmadillo){
            this.remove()
        }
    }
}