package com.mygdx.game.GameObjects.Hazards

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.Collition.OnlyProjectileCollisionMask
import com.mygdx.game.DefaultSoundHandler
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.EntityRefData
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.Fireball
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Utils.Triggerable
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class LightBulb(gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())){
    override val texture = DefaultTextureHandler.getTexture("lightbulb.png")
    val fireIconSprite = Sprite(DefaultTextureHandler.getTexture("fireball-icon.png"))
    override val layer = Layer.PERSON
    override val collitionMask = OnlyProjectileCollisionMask
    override val collision = LightBulbCollision(this)
    val entityRefData = Json.decodeFromJsonElement<LightbulbCustomFields>(gameObjectData.customFields).Entity_ref
    val sound = DefaultSoundHandler.getSound("Sound/bell.wav")
    lateinit var gameObjectToTrigger: Triggerable
    var isHit = false
    var alphaCounter = 0f
    var alphaFrames = 30f
    val red = fireIconSprite.color.r
    val green = fireIconSprite.color.g
    val blue = fireIconSprite.color.b
    val averageColor = (blue + green + red) / 10.0f

    override fun initObject() {
        gameObjectToTrigger = AreaManager.getObjectWithIid(entityRefData.entityIid, entityRefData.levelIid) as Triggerable

        fireIconSprite.setPosition(currentMiddle.x - 8f,currentMiddle.y)
        fireIconSprite.setSize(16f,16f)

        fireIconSprite.setColor(averageColor, averageColor, averageColor, fireIconSprite.color.a)
    }

    override fun render(batch: SpriteBatch) {
        super.render(batch)
        if(isHit){
            if(alphaCounter <= alphaFrames){
                fireIconSprite.setColor(red,green,blue, fireIconSprite.color.a)
            }
            else {
                fireIconSprite.setColor(averageColor,averageColor,averageColor, fireIconSprite.color.a)
                alphaCounter = 0f
                isHit = false
            }
            alphaCounter += 1
        }
        fireIconSprite.draw(batch)
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
            lightBulb.sound.play()
            lightBulb.isHit = true
        }
    }

}