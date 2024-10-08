package com.mygdx.game.GameObjects.GameObject

import com.badlogic.gdx.math.Vector2
import kotlin.math.PI
import kotlin.math.atan2

interface RotationalObject {
    var angle: Float
    fun setRotation(unitVectorDirection: Vector2, gameObject: GameObject, angleModifier: Float)
    fun GameObject.rotateByAmount(amount: Float)
}

class DefaultRotationalObject: RotationalObject {
    override var angle = 0f
    override fun setRotation(unitVectorDirection: Vector2, gameObject: GameObject, angleModifier: Float) {
        angle = unitVectorToAngle(unitVectorDirection) + angleModifier
        gameObject.polygon.rotation = angle
        gameObject.sprite.rotation = angle
    }
    override fun GameObject.rotateByAmount(amount: Float){
        this.polygon.rotate(amount)
        this.sprite.rotate(amount)
    }
}

fun unitVectorToAngle(unitVector: Vector2): Float {
    return (atan2(unitVector.y, unitVector.x) * 180 / PI).toFloat()
}