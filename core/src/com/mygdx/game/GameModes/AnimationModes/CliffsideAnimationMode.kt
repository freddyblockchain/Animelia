package com.mygdx.game.GameModes.AnimationModes

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.GameModes.DefaultInputProcessor
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.GameObject.State

class CliffsideAnimationMode(val prevMode: GameMode, override val spriteBatch: SpriteBatch = mainMode.spriteBatch, val endPos: Vector2):
    GameMode {
    var currentFrame = 0

    val endFrame = 60

    override val inputProcessor = DefaultInputProcessor()

    lateinit var prevVector2: Vector2

    lateinit var newDecrease: Vector2
    var prevRotation: Float = 0f

    override fun modeInit() {
        super.modeInit()

        prevVector2 = Vector2(player.sprite.width, player.sprite.height)
        newDecrease = Vector2(prevVector2.x / endFrame, prevVector2.y / endFrame)
        prevRotation = player.sprite.rotation
    }
    override fun FrameAction() {

        spriteBatch.begin()
        prevMode.OnlyRenderFrameAction()
        spriteBatch.end()

        if(currentFrame < endFrame ){
            player.sprite.setSize(player.sprite.width - newDecrease.x, player.sprite.height - newDecrease.y)
            player.sprite.rotate(2f)
        }
        else {
            player.setPosition(endPos)
            player.sprite.setSize(prevVector2.x, prevVector2.y)
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