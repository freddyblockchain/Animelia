package com.mygdx.game.GameObjects.Hazards

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collisions.CanMoveCollision
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.Collition.OnlyPlayerCollitionMask
import com.mygdx.game.Collition.OnlyProjectileCollisionMask
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.EntityRefData
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.Fireball
import com.mygdx.game.GameObjects.Triggers.AbilityCustomFields
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Utils.Triggerable
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class LightBulb(gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())){
    override val texture = DefaultTextureHandler.getTexture("lightbulb.png")
    override val layer = Layer.PERSON
    override val collitionMask = OnlyProjectileCollisionMask
    override val collision = LightBulbCollision(this)
    val entityRefData = Json.decodeFromJsonElement<LightbulbCustomFields>(gameObjectData.customFields).Entity_ref
    lateinit var gameObjectToTrigger: Triggerable

    override fun initObject() {
        gameObjectToTrigger = AreaManager.getObjectWithIid(entityRefData.entityIid, entityRefData.levelIid) as Triggerable
    }
}

@Serializable
data class LightbulbCustomFields(val Entity_ref: EntityRefData){

}

class LightBulbCollision(val lightBulb: LightBulb): MoveCollision(){
    override var canMoveAfterCollision = true

    override fun collisionHappened(collidedObject: GameObject) {
        if(collidedObject is Fireball){
            collidedObject.remove()
            lightBulb.gameObjectToTrigger.onTrigger()
        }
    }

}