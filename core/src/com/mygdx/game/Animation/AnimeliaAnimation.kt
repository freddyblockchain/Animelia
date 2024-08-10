package com.mygdx.game.Animation

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer

class AnimeliaAnimation(val straight: String, val right: String, val left: String): DefaultAnimation() {
    override val durationFrames = 60
    override val layer = Layer.ONGROUND
    val straightTexture = DefaultTextureHandler.getTexture(straight)
    val rightTexture = DefaultTextureHandler.getTexture(right)
    val leftTexture = DefaultTextureHandler.getTexture(left)

    val textures = listOf(straightTexture, rightTexture, straightTexture, leftTexture)

    var textureIndex = 0

    val changeImageFrameTime = 10
    override var currentFrame = 8

    fun setSpriteTextureBasedOnAnimation(sprite: Sprite){
        if(currentFrame % changeImageFrameTime == 0){
            textureIndex = (textureIndex + 1) % textures.size
            val nextTexture = textures[textureIndex]
            sprite.texture = nextTexture
        }
        currentFrame += 1
    }
    override fun reset(){
        currentFrame = 8
        textureIndex = 0
    }

    override fun render(batch: SpriteBatch) {
    }
}