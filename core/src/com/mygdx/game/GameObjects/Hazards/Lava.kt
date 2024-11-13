package com.mygdx.game.GameObjects.Hazards

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collisions.CanMoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.renderRepeatedTexture
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class Lava(gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())){
    lateinit var goToPosition: AnimeliaPosition
    val posEntityRef = Json.decodeFromJsonElement<EntityRefCustomFields>(gameObjectData.customFields).Entity_ref
    override val texture = DefaultTextureHandler.getTexture("lava.png")
    override val layer = Layer.ONGROUND
    override val collision = CanMoveCollision()

    override fun initObject() {
        super.initObject()
        goToPosition = AreaManager.getObjectWithIid(
            posEntityRef.entityIid,
            posEntityRef.levelIid
        ) as AnimeliaPosition
    }

    override fun render(batch: SpriteBatch) {
        renderRepeatedTexture(batch, texture, this.currentPosition(), Vector2(sprite.width, sprite.height))
    }
}