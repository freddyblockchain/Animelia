package com.mygdx.game.GameObjects.MoveableObjects.Projectile

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.CannotMoveStrategy.CannotMoveStrategy
import com.mygdx.game.CannotMoveStrategy.MoveRegardless
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.DefaultSoundHandler
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Enums.getDirectionFromUnitVector
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.GameObject.MoveableObject
import com.mygdx.game.GameObjects.Hazards.FiregateCollitionObject
import com.mygdx.game.GameObjects.Hazards.TargetCircle
import com.mygdx.game.Managers.SoundManager
import com.mygdx.game.getUnitVectorTowardsPoint

class Missile(gameObjectData: GameObjectData, size: Vector2, unitVectorDirection: Vector2, shooter: GameObject, val newSpan: Int = 90) : Projectile(gameObjectData,size, unitVectorDirection, shooter) {

    override var speed = 2.5f
    override val cannotMoveStrategy = MoveRegardless()
    override val texture = DefaultTextureHandler.getTexture("Rocket.png")
    override val layer = Layer.AIR
    override var direction = getDirectionFromUnitVector(unitVectorDirection)
    override var canChangeDirection = true
    override val projectileLifespan = newSpan
    var fixatedObject:GameObject? = null

    val sound = DefaultSoundHandler.getSound("Sound/Projectile Sounds/Fire impact 1.wav")

    val missileAggroRadius = MissileAggroBox(this)
    init {
        setRotation(unitVectorDirection,this,0f)
        SoundManager.playWorldSound(this, sound, 1f, 0.5f)
        missileAggroRadius.add()
    }

    override fun frameTask() {
        if(this.fixatedObject != null){
            currentUnitVector = getUnitVectorTowardsPoint(this.currentMiddle, fixatedObject!!.currentMiddle)
            this.setRotation(currentUnitVector,this,0f)
        }
        super.frameTask()
        missileAggroRadius.move(currentUnitVector, speed)
        missileAggroRadius.frameTask()

    }

    override fun remove(){
        missileAggroRadius.remove()
        super.remove()
    }

    override fun handleReflection(collidedObject: GameObject) {
        super.handleReflection(collidedObject)

        this.fixatedObject = null
        missileAggroRadius.currentUnitVector = this.currentUnitVector
        missileAggroRadius.collisionMask = this.collisionMask
    }


}

class MissileAggroBox(val missile: Missile): MoveableObject(GameObjectData(x = missile.x.toInt() - 64 + missile.width.toInt() / 2, y = missile.y.toInt() - 64 + missile.height.toInt() / 2, width = 128, height = 128)) {
    override val layer = Layer.ONGROUND
    override var direction = missile.direction
    override var canChangeDirection = missile.canChangeDirection()
    override var speed = missile.speed
    override val cannotMoveStrategy = missile.cannotMoveStrategy
    override var collisionMask = missile.collisionMask

    override val collision = MissileAggroBoxCollision(missile)

    override fun render(batch: SpriteBatch) {

    }

}

class MissileAggroBoxCollision(val missile: Missile): MoveCollision() {
    override var canMoveAfterCollision = true

    override fun collisionHappened(collidedObject: GameObject) {
        if(missile.fixatedObject == null && (collidedObject is FightableObject || collidedObject is TargetCircle)){
            missile.fixatedObject = collidedObject
        }
    }

}