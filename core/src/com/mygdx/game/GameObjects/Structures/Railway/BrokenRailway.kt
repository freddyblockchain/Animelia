package com.mygdx.game.GameObjects.Structures.Railway

import RailwayFixedSignal
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Animation.TextAnimation
import com.mygdx.game.Collisions.CanMoveCollision
import com.mygdx.game.Collition.Collision
import com.mygdx.game.Collition.InputCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Inventory.RailwayTransportData
import com.mygdx.game.Items.Material
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.generalSaveState
import com.mygdx.game.plus
import kotlin.concurrent.fixedRateTimer

class BrokenRailway(val railway: Railway, gameObjectData: GameObjectData) : GameObject(gameObjectData) {
    override val layer = Layer.ONGROUND

    override val texture = DefaultTextureHandler.getTexture("BrokenRails.png")

    val fixedTexture = DefaultTextureHandler.getTexture("HealthyRails.png")

    override var collision: Collision = FixRailsCollision(this)

    var fixed = false

    init {
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
    }

    fun fix(){
        sprite.texture = fixedTexture
        collision = CanMoveCollision()
        fixed = true
    }
}

class FixRailsCollision(val brokenRailway: BrokenRailway): InputCollision(){
    override val insideText = "FIX"

    override fun collisionHappened(collidedObject: GameObject) {
        val animeliaBoneAmount = generalSaveState.inventory.materialItems[Material.ANIMELIABONE]!!
        if(animeliaBoneAmount > 0){
            SignalManager.emitSignal(RailwayFixedSignal(brokenRailway.railway.gameObjectIid))
            generalSaveState.inventory.materialItems[Material.ANIMELIABONE] = animeliaBoneAmount - 1
            generalSaveState.inventory.railwayConnections.add(RailwayTransportData(AreaManager.getActiveArea()!!.areaIdentifier,brokenRailway.bottomright.x, brokenRailway.bottomright.y))
            generalSaveState.updateSaveState()
        } else{
            val textAnimation = TextAnimation(Color.RED, "Requires 1 Animelia Bone", Vector2(brokenRailway.currentMiddle + Vector2(0f, brokenRailway.height)), false)
            if(!AnimationManager.animationManager.any { it is TextAnimation && textAnimation.text.startsWith("Requires")}){
                AnimationManager.animationManager.add(textAnimation)
            }
        }
    }

}