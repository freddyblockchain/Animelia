package com.mygdx.game.GameObjects.MoveableObjects.Projectile

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.CannotMoveStrategy.MoveRegardless
import com.mygdx.game.DefaultSoundHandler
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Enums.getDirectionFromUnitVector
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject

class Icicle(gameObjectData: GameObjectData, size: Vector2, unitVectorDirection: Vector2, shooter: GameObject) : Projectile(gameObjectData,size, unitVectorDirection, shooter) {

    override var speed = 3f
    override val cannotMoveStrategy = MoveRegardless()
    override val texture = DefaultTextureHandler.getTexture("Icicle.png")
    override val layer = Layer.AIR
    override var direction = getDirectionFromUnitVector(unitVectorDirection)
    override var canChangeDirection = true
    val sound = DefaultSoundHandler.getSound("Sound/Projectile Sounds/Ice attack 2.wav")
    init {
        setRotation(unitVectorDirection,this,0f)
        val id = sound.play()
        sound.setPitch(id, 1f)
        sound.setVolume(id,0.5f)
    }
}