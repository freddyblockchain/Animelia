package com.mygdx.game

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Utils.RectanglePolygon

fun GameObject.InitSprite(texture: Texture): Sprite {
    val sprite = Sprite(texture)
    sprite.setSize(size.x, size.y)
    sprite.setOriginCenter()
    sprite.setPosition(initPosition.x, initPosition.y)
    return sprite
}

fun InitPolygon(sprite: Sprite): Polygon {
    val polygon = RectanglePolygon(sprite.boundingRectangle)
    polygon.setOrigin(sprite.x + sprite.originX, sprite.y + sprite.originY)
    polygon.setPosition(sprite.x - polygon.vertices[0], sprite.y - polygon.vertices[1])
    return polygon
}

fun GameObject.setSize(newSize: Vector2){
    val differenceInSize = (Vector2(this.sprite.width, this.sprite.height) - newSize) / 2f
    this.sprite.setSize(newSize.x, newSize.y)
    this.sprite.setPosition(this.sprite.x + differenceInSize.x, this.sprite.y + differenceInSize.y)

    this.polygon.vertices = RectanglePolygon(this.sprite.boundingRectangle).vertices
    this.polygon.setOrigin(sprite.x + sprite.originX, sprite.y + sprite.originY)
    this.polygon.setPosition(sprite.x - polygon.vertices[0], sprite.y - polygon.vertices[1])
}