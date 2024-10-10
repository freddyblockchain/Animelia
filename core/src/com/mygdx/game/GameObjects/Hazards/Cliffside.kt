package com.mygdx.game.GameObjects.Hazards

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Collisions.DefaultAreaEntranceCollition
import com.mygdx.game.Collition.CollisionMask
import com.mygdx.game.Collition.OnlyPlayerCollitionMask
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameModes.AnimationModes.CliffsideAnimationMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.GameObject.FlyingState
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.GameObject.State
import com.mygdx.game.Managers.CollisionManager

class Cliffside(gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())) {
    override val texture = DefaultTextureHandler.getTexture("sensor.png")

    override val layer = Layer.ONGROUND
    override val collision = CliffSideCollision(this)

    override val collitionMask = OnlyPlayerCollitionMask
    override fun render(batch: SpriteBatch) {

    }
}


class CliffSideCollision(val cliffside: Cliffside): DefaultAreaEntranceCollition(){
    override fun actionWhileInside() {

        if(player.state == State.NORMAL && player.flyingState == FlyingState.NOTFLYING){
            player.state = State.STUNNED
            changeMode(CliffsideAnimationMode(mainMode, endPos = cliffside.bottomleft + Vector2(-50f,50f)))
            movedOutside(player)
        }
    }

    override var canMoveAfterCollision = true

}