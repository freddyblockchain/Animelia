package com.mygdx.game.GameModes.AnimationModes

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameModes.DefaultInputProcessor
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.Structures.Railway.Cart
import com.mygdx.game.GameObjects.Structures.Railway.Railway
import com.mygdx.game.Inventory.RailwayTransportData
import com.mygdx.game.Managers.AreaManager


class CartRidingAnimationMode(val railwayArea: String, val prevMode: GameMode, override val spriteBatch: SpriteBatch = mainMode.spriteBatch, val cart: Cart, val railway: Railway): GameMode {
    var currentFrame = 0

    val firstAreaEndFrame = 90

    val secondAreaEndFrame = 180

    override val inputProcessor = DefaultInputProcessor()

    lateinit var prevVector2: Vector2

    lateinit var newDecrease: Vector2

    val increment = ((railway.width) / firstAreaEndFrame)
    var prevRotation: Float = 0f

    lateinit var railwayData: RailwayTransportData

    var cart2:Cart? = null

    override fun modeInit() {
        super.modeInit()

        player.layer = Layer.ONGROUND
        cart.layer = Layer.PERSON
        railwayData = generalSaveState.inventory.railwayConnections.first { it.areaIdentifier ==  railwayArea}
    }
    override fun FrameAction() {

        spriteBatch.begin()
        prevMode.OnlyRenderFrameAction()
        spriteBatch.end()

        if(currentFrame < firstAreaEndFrame ){
            cart.setPosition(Vector2(cart.x + increment, cart.y))
            player.setPosition(cart.currentMiddle - Vector2(player.width / 2f, 8f))
        }
        else if (currentFrame == firstAreaEndFrame){
            changeArea(Vector2(railwayData.x, railwayData.y), railwayData.areaIdentifier)
            cart2 = AreaManager.getActiveArea()!!.gameObjects.first { it is Cart } as Cart
            cart2!!.setPosition(Vector2(railwayData.x, railwayData.y))
            player.setPosition(cart.currentMiddle - Vector2(player.width / 2f, 8f))
            cart2!!.layer = Layer.PERSON
        }
        else if (currentFrame < secondAreaEndFrame){
            cart2!!.setPosition(Vector2(cart2!!.x - increment, cart2!!.y))
            player.setPosition(cart2!!.currentMiddle - Vector2(player.width / 2f, 8f))
        } else{
            player.layer = Layer.PERSON
            cart2!!.layer = Layer.ONGROUND
            changeMode(prevMode)
        }

        currentFrame += 1
    }

    override fun render() {
        prevMode.render()
    }
}