package com.mygdx.game.GameModes.AnimationModes

import com.mygdx.game.Utils.drawPolygonShape

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Enums.Direction
import com.mygdx.game.GameModes.DefaultInputProcessor
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.GameObject.State
import com.mygdx.game.Managers.CollisionManager.Companion.handleObjectMovedOutside

class SkewerAnimationMode(val prevMode: GameMode, override val spriteBatch: SpriteBatch = mainMode.spriteBatch, val returningPos: AnimeliaPosition):
    GameMode {
    var currentFrame = 0

    val endFrame = 60

    var offsetX = 0f

    override val inputProcessor = DefaultInputProcessor()

    var prevRotation: Float = 0f

    override fun modeInit() {
        super.modeInit()
        player.drawPlayer = false
        player.collidingObjects.forEach {
            handleObjectMovedOutside(it.collision, player)
        }
    }
    override fun FrameAction() {

        spriteBatch.begin()
        prevMode.OnlyRenderFrameAction()
        spriteBatch.end()

        offsetX += 0.25f

        currentFrame += 1

        if(currentFrame >= endFrame ){
            player.setPosition(returningPos.currentPosition())
            player.state = State.NORMAL
            player.drawPlayer = true
            changeMode(prevMode)
        }
    }

    override fun render() {
        prevMode.render()
        spriteBatch.begin()

        if (player.direction == Direction.DOWN || player.direction == Direction.UP) {
            val yPosLowerHalf = if (player.direction == Direction.DOWN) player.sprite.y + player.sprite.height / 2 else player.sprite.y
            val yPosUpperHalf = if (player.direction == Direction.DOWN) player.sprite.y else player.sprite.y + player.sprite.height / 2

            // Split vertically for up and down movement
            spriteBatch.draw(player.sprite.texture,
                player.sprite.x + offsetX, yPosLowerHalf,
                player.sprite.width / 2f, player.sprite.height / 4f,
                player.sprite.width, player.sprite.height / 2,
                1.0f, 1.0f, player.sprite.rotation,
                0, 0, player.sprite.width.toInt(), player.sprite.height.toInt() / 2,
                false, false)

            spriteBatch.draw(player.sprite.texture,
                player.sprite.x - offsetX, yPosUpperHalf,
                player.sprite.width / 2f, player.sprite.height / 4f,
                player.sprite.width, player.sprite.height / 2,
                1.0f, 1.0f, player.sprite.rotation,
                0, player.sprite.height.toInt() / 2, player.sprite.width.toInt(), player.sprite.height.toInt() / 2,
                false, false)
        } else if (player.direction == Direction.LEFT || player.direction == Direction.RIGHT) {
            // Split horizontally for left and right movement
            val xPosLowerHalf =
                if (player.direction == Direction.LEFT) player.sprite.y + player.sprite.height / 2 else player.sprite.x
            val xPosUpperHalf =
                if (player.direction == Direction.LEFT) player.sprite.y + player.sprite.height / 2 else player.sprite.x

            // Draw the lower half of the sprite with rotation
            spriteBatch.draw(
                player.sprite.texture,
                player.sprite.x + 8f, player.sprite.y - offsetX - 8f,
                player.sprite.width / 4f, player.sprite.height / 2f, // Origin for rotation in left half
                player.sprite.width / 2, player.sprite.height,       // Width and Height (splitting vertically)
                1.0f, 1.0f, player.sprite.rotation,
                0, 0, player.sprite.width.toInt() / 2, player.sprite.height.toInt(),
                player.direction == Direction.LEFT, false
            )
           spriteBatch.draw(
                player.sprite.texture,
                player.sprite.x + 8f, player.sprite.y + offsetX + 8f,
                player.sprite.width / 4f, player.sprite.height / 2f, // Origin for rotation in left half
                player.sprite.width / 2, player.sprite.height,       // Width and Height (splitting vertically)
                1.0f, 1.0f, player.sprite.rotation,
                16, 0, player.sprite.width.toInt() / 2, player.sprite.height.toInt(),
               player.direction == Direction.LEFT, false
            )
        }
        spriteBatch.end()
    }
}