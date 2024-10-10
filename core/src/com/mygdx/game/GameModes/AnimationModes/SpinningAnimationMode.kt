package com.mygdx.game.GameModes.AnimationModes

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.GameModes.DefaultInputProcessor
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.GameObject.State

class SpinningAnimationMode(val prevMode: GameMode, override val spriteBatch: SpriteBatch = mainMode.spriteBatch, val endPos: Vector2):
    GameMode {
    var currentFrame = 0

    val endFrame = 30

    override val inputProcessor = DefaultInputProcessor()

    var prevRotation: Float = 0f

    lateinit var playerDistanceVector: Vector2

    override fun modeInit() {
        super.modeInit()
        prevRotation = player.sprite.rotation

        playerDistanceVector = (endPos  - player.currentPosition()) / endFrame.toFloat()
    }
    override fun FrameAction() {

        spriteBatch.begin()
        prevMode.OnlyRenderFrameAction()
        spriteBatch.end()

        if(currentFrame < endFrame ){
            player.sprite.rotate(12f)
            val newPos = player.currentPosition() + playerDistanceVector
            player.sprite.setPosition(newPos.x, newPos.y)
        }
        else {
            player.setPosition(endPos)
            player.sprite.rotation = prevRotation
            player.state = State.NORMAL
            changeMode(prevMode)
        }

        currentFrame += 1
    }

    override fun render() {
        prevMode.render()
    }
}