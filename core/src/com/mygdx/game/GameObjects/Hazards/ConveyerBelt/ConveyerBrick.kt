package com.mygdx.game.GameObjects.Hazards.ConveyerBelt

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.getDirectionUnitVector
import com.mygdx.game.times

class ConveyerBrick(val pos: Vector2, val size: Vector2, val speed: Float, var direction: Direction, texture: Texture): Sprite(texture) {
    init {
        val startPos = when(direction){
            Direction.DOWN -> Vector2(pos.x,pos.y)
            Direction.UP -> Vector2(pos.x, pos.y - size.y)
            Direction.RIGHT -> Vector2(pos.x, pos.y)
            Direction.LEFT -> Vector2(pos.x + size.x, pos.y)
        }
        this.setPosition(startPos.x,startPos.y)
        this.setSize(size.x,size.y)
    }

    fun move(){
        val directionUnitVector = getDirectionUnitVector(direction)
        val nextIncrement = directionUnitVector * speed
        this.setPosition(this.x + nextIncrement.x, this.y + nextIncrement.y)
        if(direction == Direction.DOWN){
            if(this.y <= pos.y - size.y){
                this.setPosition(pos.x,pos.y)
            }
        }else if(direction == Direction.UP){
            if(this.y >= pos.y){
                this.setPosition(pos.x,pos.y - size.y)
            }
        }else if(direction == Direction.RIGHT){
            if(this.x >= pos.x + size.x){
                this.setPosition(pos.x,pos.y)
            }
        }
        else if(direction == Direction.LEFT){
            if(this.x <= pos.x){
                this.setPosition(pos.x + size.x,pos.y)
            }
        }

    }
}