package com.mygdx.game.GameObjects.Other.Crystals

import com.badlogic.gdx.math.Polygon
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.Collition.OnlyPlayerCollitionMask
import com.mygdx.game.EntityRefData
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.Hazards.BoxingGloveCustomFields
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.CollisionManager
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

abstract class Crystal(gameObjectData: GameObjectData) : GameObject(gameObjectData) {

    lateinit var goToPosition: AnimeliaPosition
    val posEntityRef = Json.decodeFromJsonElement<BoxingGloveCustomFields>(gameObjectData.customFields).Entity_ref
    override val layer = Layer.ONGROUND

    override val collisionMask = OnlyPlayerCollitionMask

    override val collision = CrystalCollision(this)


    override fun initObject() {
        goToPosition = AreaManager.getObjectWithIid(
            posEntityRef.entityIid,
            posEntityRef.levelIid
        ) as AnimeliaPosition
    }
}

@Serializable
data class CrystalCustomFields(val Entity_ref: EntityRefData){

}

class CrystalCollision(crystal: Crystal): MoveCollision(){
    override var canMoveAfterCollision = true
    override fun collisionCheck(polygon1: Polygon, polygon2: Polygon): Boolean {
        return CollisionManager.isMiddleInPolygon(polygon1, polygon2)
    }
    override fun collisionHappened(collidedObject: GameObject) {

    }

}