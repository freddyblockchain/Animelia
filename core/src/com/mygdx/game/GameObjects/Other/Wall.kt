package com.mygdx.game.GameObjects.Other

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.renderRepeatedTexture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.Ground

class Wall(gameObjectData: GameObjectData, size: Vector2, val ground: Ground) : GameObject(gameObjectData, size) {
    override val texture = DefaultTextureHandler.getTexture("Walls/Dungeon/Up.png")
    val textureUp = DefaultTextureHandler.getTexture("Walls/Dungeon/Up.png")
    val textureRight = DefaultTextureHandler.getTexture("Walls/Dungeon/Right.png")
    val textureLeft = DefaultTextureHandler.getTexture("Walls/Dungeon/Left.png")
    val textureBottom = DefaultTextureHandler.getTexture("Walls/Dungeon/Bottom.png")
    override val layer = Layer.BEFORELOCATION
    override val shouldCollide = false

    init {
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
    }
    val wallTopLeft = Pair(ground.topleft,Vector2(ground.topright.x - ground.topleft.x,32f))
    val wallTopRight = Pair(ground.bottomright,Vector2(32f,ground.topright.y - ground.bottomright.y))
    val wallBottomRight = Pair(Vector2(ground.bottomleft.x, ground.bottomleft.y - 32f),Vector2(ground.bottomright.x - ground.bottomleft.x,32f))
    val wallBottomLeft = Pair(Vector2(ground.bottomleft.x - 32f, ground.bottomleft.y),Vector2(32f,ground.topleft.y - ground.bottomleft.y + 0f))
    override fun render(batch: SpriteBatch) {
        renderRepeatedTexture(batch,textureUp,wallTopLeft.first,wallTopLeft.second)
        renderRepeatedTexture(batch,textureRight,wallTopRight.first,wallTopRight.second)
        renderRepeatedTexture(batch,textureBottom,wallBottomRight.first,wallBottomRight.second)
        renderRepeatedTexture(batch,textureLeft,wallBottomLeft.first,wallBottomLeft.second)
    }
}