package com.mygdx.game.GameModes.AnimationModes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.GameModes.DefaultInputProcessor
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.GameObject.State
import com.mygdx.game.Managers.CollisionManager.Companion.handleObjectMovedOutside
import com.mygdx.game.mainMode
import com.mygdx.game.player
import kotlin.math.max

class FireAnimationMode(val prevMode: GameMode, override val spriteBatch: SpriteBatch = mainMode.spriteBatch, val returningPos: AnimeliaPosition):
    GameMode {
    var currentFrame = 0

    val endFrame = 60

    override val inputProcessor = DefaultInputProcessor()

    lateinit var orgColor: Color

    val colorInc = 4.5f

    override fun modeInit() {
        super.modeInit()
        orgColor = player.sprite.color
        player.collidingObjects.forEach {
            handleObjectMovedOutside(it.collision, player)
        }

    }
    override fun FrameAction() {

        spriteBatch.begin()
        prevMode.OnlyRenderFrameAction()
        spriteBatch.end()

        if(currentFrame < endFrame ){
            val currentColor = player.sprite.color

            val newColor = Color()
            newColor.r = max(0f,currentColor.r - (colorInc / 255))
            newColor.g = max(0f,currentColor.g - (colorInc / 255))
            newColor.b = max(0f,currentColor.b - (colorInc / 255))
            newColor.a = 1f
            player.sprite.color = newColor
        }
        else {
            player.state = State.NORMAL
            player.setPosition(returningPos.currentPosition())
            player.sprite.color = Color.WHITE
            changeMode(prevMode)
        }

        currentFrame += 1
    }

    override fun render() {
        prevMode.render()
    }
}