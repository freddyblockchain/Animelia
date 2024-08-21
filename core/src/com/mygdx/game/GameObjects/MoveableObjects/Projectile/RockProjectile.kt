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
import com.mygdx.game.GameObjects.Hazards.IceCone
import com.mygdx.game.GameObjects.Hazards.Rock

class RockProjectile(gameObjectData: GameObjectData, size: Vector2, unitVectorDirection: Vector2, shooter: GameObject) : Projectile(gameObjectData,size, unitVectorDirection, shooter) {

    override var speed = 3f
    override val cannotMoveStrategy = MoveRegardless()
    override val texture = DefaultTextureHandler.getTexture("MediumRock.png")
    override val layer = Layer.AIR
    override var direction = getDirectionFromUnitVector(unitVectorDirection)
    override var canChangeDirection = true
    override val collision = RockCollision(this, shooter)
    val sound = DefaultSoundHandler.getSound("Sound/FireExplotion/explosion_01.ogg")

    init {
        val id = sound.play()
        sound.setPitch(id, 1f)
        sound.setVolume(id,0.5f)
    }

    override fun frameTask() {
        super.frameTask()
        this.rotateByAmount(3f)
    }
}

class RockCollision(rock: RockProjectile, val shooter: GameObject): ProjectileCollision(rock){
    override fun collisionHappened(collidedObject: GameObject) {
        if(collidedObject is Rock && shooter is FightableObject && collidedObject.checkDestroyed(shooter.stats)){
            collidedObject.remove()
        }
    }
}