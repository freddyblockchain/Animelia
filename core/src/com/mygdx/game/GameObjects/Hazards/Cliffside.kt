package com.mygdx.game.GameObjects.Hazards

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Collisions.DefaultAreaEntranceCollition
import com.mygdx.game.Collition.CollisionMask
import com.mygdx.game.Collition.OnlyPlayerCollitionMask
import com.mygdx.game.Collition.OnlyTheseObjectsCollisionMask
import com.mygdx.game.EntityRefCustomFields
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameModes.AnimationModes.CliffsideAnimationMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.GameObject.FlyingState
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.GameObject.State
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.CollisionManager
import com.mygdx.game.Managers.CollisionManager.Companion.isPolygonsColliding
import com.mygdx.game.Managers.RaycastObject
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class Cliffside(gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())) {
    override val texture = DefaultTextureHandler.getTexture("cliffside.png")

    val posEntityRef = Json.decodeFromJsonElement<EntityRefCustomFields>(gameObjectData.customFields).Entity_ref

    lateinit var animeliaPosition: AnimeliaPosition

    override val layer = Layer.ONGROUND
    override val collision = CliffSideCollision(this)

    override val collisionMask = OnlyTheseObjectsCollisionMask(listOf( Player::class.java, RaycastObject::class.java))

    override fun initObject() {
        animeliaPosition = AreaManager.getObjectWithIid(
            posEntityRef.entityIid,
            posEntityRef.levelIid
        ) as AnimeliaPosition
    }
}


class CliffSideCollision(val cliffside: Cliffside): DefaultAreaEntranceCollition(){

    override fun collisionCheck(polygonToCheck: Polygon, polygon2: Polygon, gameObject: GameObject): Boolean {
        return if(gameObject is Player){
            CollisionManager.isMiddleInPolygon(polygonToCheck, polygon2)
        } else{
            isPolygonsColliding(polygonToCheck, polygon2)
        }
    }

    override fun actionWhileInside() {

        if(player.state == State.NORMAL && player.flyingState == FlyingState.NOTFLYING){
            player.state = State.STUNNED
            changeMode(CliffsideAnimationMode(mainMode, returningPos = cliffside.animeliaPosition))
            movedOutside(player)
        }
    }

    override fun movedInside(objectEntered: GameObject) {
        if(objectEntered is Player){
            super.movedInside(objectEntered)
        }
    }

    override var canMoveAfterCollision = true

}