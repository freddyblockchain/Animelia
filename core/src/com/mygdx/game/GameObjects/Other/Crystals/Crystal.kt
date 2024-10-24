package com.mygdx.game.GameObjects.Other.Crystals

import CrystalActivatedSignal
import com.badlogic.gdx.math.Polygon
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.Collition.OnlyPlayerCollitionMask
import com.mygdx.game.EntityRefData
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.CollisionManager
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.player
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

abstract class Crystal(gameObjectData: GameObjectData) : GameObject(gameObjectData) {

    lateinit var goToPosition: AnimeliaPosition
    lateinit var statue: Statue
    val posEntityRef = Json.decodeFromJsonElement<CrystalCustomFields>(gameObjectData.customFields).Entity_ref
    val statueEntityRef = Json.decodeFromJsonElement<CrystalCustomFields>(gameObjectData.customFields).Entity_ref2
    override val layer = Layer.ONGROUND

    override val collisionMask = OnlyPlayerCollitionMask

    override val collision = CrystalCollision(this)


    override fun initObject() {
        goToPosition = AreaManager.getObjectWithIid(
            posEntityRef.entityIid,
            posEntityRef.levelIid
        ) as AnimeliaPosition
        statue = AreaManager.getObjectWithIid(
            statueEntityRef.entityIid,
            statueEntityRef.levelIid
        ) as Statue
    }
}

@Serializable
data class CrystalCustomFields(val Entity_ref: EntityRefData, val Entity_ref2: EntityRefData){

}

class CrystalCollision(val crystal: Crystal): MoveCollision(){
    override var canMoveAfterCollision = true
    override fun collisionCheck(polygon1: Polygon, polygon2: Polygon): Boolean {
        return CollisionManager.isMiddleInPolygon(polygon1, polygon2)
    }
    override fun collisionHappened(collidedObject: GameObject) {
        val crystalPastSignals = SignalManager.pastSignals.filterIsInstance<CrystalActivatedSignal>()
        player.setPosition(crystal.goToPosition.currentPosition())

        if(crystalPastSignals.none { it.crystalEntityIId == crystal.gameObjectIid }){
            SignalManager.emitSignal(CrystalActivatedSignal(crystal.gameObjectIid, crystal.statue.gameObjectIid))
        }
    }

}