package com.mygdx.game.GameObjects.Bulbs

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.Collition.OnlyProjectileCollisionMask
import com.mygdx.game.DefaultSoundHandler
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.EntityRefCustomFields
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.Fireball
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.SoundProjectile
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Utils.Triggerable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class SoundBulb(gameObjectData: GameObjectData) :
    GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(), gameObjectData.height.toFloat())) {
    override val texture = DefaultTextureHandler.getTexture("soundbulb.png")
    val fireIconSprite = Sprite(DefaultTextureHandler.getTexture("MusicNode.png"))
    override val layer = Layer.PERSON
    override val collisionMask = OnlyProjectileCollisionMask
    override val collision = SoundBulbCollision(this)
    val entityRefData = Json.decodeFromJsonElement<EntityRefCustomFields>(gameObjectData.customFields).Entity_ref
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
        gameObjectToTrigger =
           AreaManager.getObjectWithIid(entityRefData.entityIid, entityRefData.levelIid) as Triggerable

        fireIconSprite.setPosition(currentMiddle.x - 6f, currentMiddle.y)
        fireIconSprite.setSize(12f, 16f)

        fireIconSprite.setColor(averageColor, averageColor, averageColor, fireIconSprite.color.a)
    }

    override fun render(batch: SpriteBatch) {
        super.render(batch)
        if (isHit) {
            if (alphaCounter <= alphaFrames) {
                fireIconSprite.setColor(red, green, blue, fireIconSprite.color.a)
            } else {
                fireIconSprite.setColor(averageColor, averageColor, averageColor, fireIconSprite.color.a)
                alphaCounter = 0f
                isHit = false
            }
            alphaCounter += 1
        }
        fireIconSprite.draw(batch)
    }
}

class SoundBulbCollision(val soundBulb: SoundBulb) : MoveCollision() {
    override var canMoveAfterCollision = true

    override fun collisionHappened(collidedObject: GameObject) {
        if (collidedObject is SoundProjectile) {
            collidedObject.remove()
            soundBulb.gameObjectToTrigger.onTrigger()
            soundBulb.sound.play()
            soundBulb.isHit = true
        }
    }

}