package com.mygdx.game

import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
operator fun Vector2.plus(other: Vector2): Vector2{
     return Vector2(this.x +  other.x,this.y + other.y)
}
operator fun Vector2.minus(other: Vector2): Vector2{
    return Vector2(this.x - other.x,this.y - other.y)
}
operator fun Vector2.times(scalar: Float): Vector2{
    return Vector2(this.x * scalar,this.y * scalar)
}

operator fun Vector2.div(scalar: Float): Vector2{
    return Vector2(this.x / scalar,this.y / scalar)
}

operator fun FloatArray.plus(vector2: Vector2): FloatArray{
    val result = this.mapIndexed { index,x -> if(index % 2 == 0) x + vector2.x else x + vector2.y}.toFloatArray()
    return result
}
operator fun FloatArray.minus(vector2: Vector2): FloatArray{
    return this.map { x -> if(x % 2 == 0f) x - vector2.x else x - vector2.y }.toFloatArray()
}
operator fun Vector2.unaryMinus(): Vector2 {
    return Vector2(-this.x,-this.y)
}