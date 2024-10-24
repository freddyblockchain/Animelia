package com.mygdx.game.GameObjects.Other.Crystals

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collisions.CannotMoveCollision
import com.mygdx.game.Collition.OnlyPlayerCollitionMask
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.minus
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
class Statue(gameObjectData: GameObjectData) : GameObject(gameObjectData) {

    override val layer = Layer.ONGROUND
    override val texture = DefaultTextureHandler.getTexture("EmptyDoor.png")
    override val collision = CannotMoveCollision()

    val crystalTexture = DefaultTextureHandler.getTexture("IceCrystal.png")
    val crystal1 = Sprite(crystalTexture)
    val crystal2 = Sprite(crystalTexture)

    var activatedFirst = false

    fun activateCrystal(){
        if(!activatedFirst){
            activatedFirst = true
            crystal1.setAlpha(1f)
        } else{
            crystal2.setAlpha(1f)
            this.setPosition(this.currentPosition() - Vector2(64f,0f))
        }
    }

    init {
        val pos1 = this.currentPosition() - Vector2(32f,0f)
        crystal1.setPosition(pos1.x, pos1.y)
        val pos2 = this.bottomright
        crystal2.setPosition(pos2.x, pos2.y)

        crystal1.setAlpha(0.3f)
        crystal2.setAlpha(0.3f)
    }

    override fun render(batch: SpriteBatch) {
        super.render(batch)
        crystal1.draw(batch)
        crystal2.draw(batch)
    }
}