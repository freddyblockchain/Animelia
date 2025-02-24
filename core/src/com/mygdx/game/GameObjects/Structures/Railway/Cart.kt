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
import com.mygdx.game.GameModes.UIMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Inventory.RailwayTransportData
import com.mygdx.game.Items.Material
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.UI.Scene2d.Screens.PickOptionsScreen
import com.mygdx.game.UI.Scene2d.Screens.PickRailwayScreen

class Cart(gameObjectData: GameObjectData, railway: Railway) : GameObject(gameObjectData) {
    override var layer = Layer.AIR
    override val texture = DefaultTextureHandler.getTexture("Minecart.png")
    override val collision = CartCollision(railway, this)
}

class CartCollision(val railway: Railway, val cart: Cart): InputCollision(){
    override var insideText: String = ""
        get() = if(railway.brokenRailway.fixed) "RIDE" else "FIX"

    override fun collisionHappened(collidedObject: GameObject) {
        if(railway.brokenRailway.fixed){
            val pickRailwayScreen = PickRailwayScreen(prevMode = mainMode, cart, railway)
            changeMode(UIMode(pickRailwayScreen))
        }else{
            val animeliaBoneAmount = generalSaveState.inventory.materialItems[Material.ANIMELIABONE]
            if(animeliaBoneAmount != null && animeliaBoneAmount > 0){
                SignalManager.emitSignal(RailwayFixedSignal(railway.gameObjectIid))
                generalSaveState.inventory.materialItems[Material.ANIMELIABONE] = animeliaBoneAmount - 1
                generalSaveState.inventory.railwayConnections.add(RailwayTransportData(AreaManager.getActiveArea()!!.areaIdentifier,railway.brokenRailway.bottomright.x, railway.brokenRailway.bottomright.y))
                generalSaveState.updateSaveState()
            } else {
                val textAnimation = TextAnimation(Color.RED, "Requires 1 Animelia Bone", Vector2(cart.currentMiddle + Vector2(0f, cart.height)), false)
                if(!AnimationManager.animationManager.any { it is TextAnimation && textAnimation.text.startsWith("Requires")}){
                    AnimationManager.animationManager.add(textAnimation)
                }
            }
        }
    }
}