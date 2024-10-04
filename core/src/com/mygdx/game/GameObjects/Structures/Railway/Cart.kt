package com.mygdx.game.GameObjects.Structures.Railway

import RailwayFixedSignal
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Animation.TextAnimation
import com.mygdx.game.Collition.InputCollision
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameModes.AnimationModes.CartRidingAnimationMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Items.Material
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Managers.SignalManager

class Cart(gameObjectData: GameObjectData, railway: Railway) : GameObject(gameObjectData) {
    override var layer = Layer.ONGROUND
    override val texture = DefaultTextureHandler.getTexture("Minecart.png")
    override val collision = CartCollision(railway, this)
}

class CartCollision(val railway: Railway, val cart: Cart): InputCollision(){
    override val insideText = "RIDE"

    override fun collisionHappened(collidedObject: GameObject) {
        if(railway.brokenRailway.fixed){
            println("begin animation")
            val cartRidingMode = CartRidingAnimationMode(mainMode, spriteBatch = mainMode.spriteBatch, cart, railway)

            changeMode(cartRidingMode)
        }else {
            val textAnimation = TextAnimation(Color.RED, "Requires fixing railway", Vector2(cart.currentMiddle + Vector2(0f, cart.height)), false)
            if(!AnimationManager.animationManager.any { it is TextAnimation && textAnimation.text.startsWith("Requires")}){
                AnimationManager.animationManager.add(textAnimation)
            }
        }
    }

}