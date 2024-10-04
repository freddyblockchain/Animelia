package com.mygdx.game.GameModes.AnimationModes

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameModes.DefaultInputProcessor
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.GameObject.State
import com.mygdx.game.GameObjects.Structures.Railway.BrokenRailway
import com.mygdx.game.GameObjects.Structures.Railway.Cart
import com.mygdx.game.GameObjects.Structures.Railway.Railway
import com.mygdx.game.mainMode
import com.mygdx.game.minus
import com.mygdx.game.player
import org.w3c.dom.css.ViewCSS


class CartRidingAnimationMode(val prevMode: GameMode, override val spriteBatch: SpriteBatch = mainMode.spriteBatch, val cart: Cart, val railway: Railway): GameMode {
    var currentFrame = 0

    val endFrame = 90f

    override val inputProcessor = DefaultInputProcessor()

    lateinit var prevVector2: Vector2

    lateinit var newDecrease: Vector2

    val increment = ((railway.width) / endFrame)
    var prevRotation: Float = 0f

    override fun modeInit() {
        super.modeInit()

        player.layer = Layer.ONGROUND
        cart.layer = Layer.PERSON
    }
    override fun FrameAction() {

        spriteBatch.begin()
        prevMode.OnlyRenderFrameAction()
        spriteBatch.end()

        if(currentFrame < endFrame ){
            cart.setPosition(Vector2(cart.x + increment, cart.y))
            player.setPosition(cart.currentMiddle - Vector2(player.width / 2f, 8f))
        }
        else {
            //player.setPosition(endPos)
            player.layer = Layer.PERSON
            cart.layer = Layer.ONGROUND
            changeMode(prevMode)
        }

        currentFrame += 1
    }

    override fun render() {
        prevMode.render()
    }
}