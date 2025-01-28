package com.mygdx.game.GameObjects.MoveableObjects.Projectile

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.CannotMoveStrategy.MoveRegardless
import com.mygdx.game.DefaultSoundHandler
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Enums.getDirectionFromUnitVector
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.Hazards.Rock

class SpearProjectile(gameObjectData: GameObjectData, size: Vector2, unitVectorDirection: Vector2, shooter: GameObject, val newSpan: Int = 90) : Projectile(gameObjectData,size, unitVectorDirection, shooter) {

    override var speed = 4f
    override val cannotMoveStrategy = MoveRegardless()
    override val texture = DefaultTextureHandler.getTexture("spear.png")
    override val layer = Layer.AIR
    override var direction = getDirectionFromUnitVector(unitVectorDirection)
    override var canChangeDirection = true
    override val collision = SpearCollision(this, shooter)
    override val projectileLifespan = newSpan
    val sound = DefaultSoundHandler.getSound("Sound/FireExplotion/explosion_01.ogg")

    init {
        setRotation(unitVectorDirection,this,-90f)
        val id = sound.play()
        sound.setPitch(id, 1f)
        sound.setVolume(id,0.5f)
    }
}

class SpearCollision(spear: SpearProjectile, val shooter: GameObject): ProjectileCollision(spear){
    override fun collisionHappened(collidedObject: GameObject) {
        super.collisionHappened(collidedObject)
        if(collidedObject is Rock && shooter is FightableObject){
            collidedObject.handleRockDestroyed(shooter.stats)
        }
    }
}