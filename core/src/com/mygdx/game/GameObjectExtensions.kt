package com.mygdx.game

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Polygon
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