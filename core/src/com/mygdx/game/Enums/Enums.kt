package com.mygdx.game.Enums

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2


enum class Direction{UP,LEFT,RIGHT,DOWN}
enum class Layer {BEFORELOCATION,BEFOREGROUND,GROUND,ONGROUND,PERSON,AIR,FOREGROUND}

fun getDirectionUnitVector(direction: Direction): Vector2{
   return when(direction){
       Direction.UP -> Vector2(0f,1f)
       Direction.LEFT -> Vector2(-1f, 0f)
       Direction.RIGHT -> Vector2(1f, 0f)
       Direction.DOWN -> Vector2(0f,-1f)
    }
}

fun getDirectionFromString(direction: String): Direction{
    return when(direction){
        "Up" -> Direction.UP
        "Right" -> Direction.RIGHT
        "Down" -> Direction.DOWN
        else -> Direction.LEFT
    }
}

fun getDirectionUnitVector(direction: String): Vector2{
    return when(direction){
        "Up" -> Vector2(0f,1f)
        "Left" -> Vector2(-1f, 0f)
        "Right" -> Vector2(1f, 0f)
        else -> Vector2(0f,-1f)
    }
}

fun getDirectionFromUnitVector(directionUnitVector: Vector2): Direction{
    return when(directionUnitVector){
        Vector2(0f,1f) -> Direction.UP
        Vector2(-1f, 0f) -> Direction.LEFT
        Vector2(1f, 0f) -> Direction.RIGHT
        else -> Direction.DOWN
    }
}