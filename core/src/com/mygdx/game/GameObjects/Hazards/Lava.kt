package com.mygdx.game.GameObjects.Hazards

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collisions.CanMoveCollision
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.Collition.OnlyPlayerCollitionMask
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.CollisionManager
import com.mygdx.game.player
import com.mygdx.game.renderRepeatedTexture
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class Lava(gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())){
    lateinit var goToPosition: AnimeliaPosition
    val posEntityRef = Json.decodeFromJsonElement<EntityRefCustomFields>(gameObjectData.customFields).Entity_ref
    override val texture = DefaultTextureHandler.getTexture("lava.png")
    override val layer = Layer.ONGROUND
    override val collision = LavaCollision(this)
    override val collisionMask = OnlyPlayerCollitionMask

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

class LavaCollision(val lava: Lava): MoveCollision(){
    override var canMoveAfterCollision = true

    override fun collisionCheck(polygon1: Polygon, polygon2: Polygon): Boolean {
        return CollisionManager.isMiddleInPolygon(polygon1, polygon2)
    }

    override fun collisionHappened(collidedObject: GameObject) {
        player.setPosition(lava.goToPosition.currentPosition())
    }

}