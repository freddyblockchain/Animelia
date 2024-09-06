package com.mygdx.game.GameModes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Animation.TextAnimation
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.getAnimeliaData
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Managers.MusicManager
import kotlin.math.min

class DeathMode(val prevMode: GameMode, val nextMode: GameMode, override val spriteBatch: SpriteBatch = mainMode.spriteBatch): GameMode {
    var currentFrame = 0

    val endFrame = 180

    //val currentTexture = player.sprite.texture
    val deathTexture = DefaultTextureHandler.getTexture("death.png")

    val vertexShader = Gdx.files.internal("Shaders/BlackAndWhite/vertex.glsl").readString();
    val fragmentShader = Gdx.files.internal("Shaders/BlackAndWhite/fragment.glsl").readString();
    val shaderProgram = ShaderProgram(vertexShader,fragmentShader);
    val originalShader = spriteBatch.shader

    override val inputProcessor = DefaultInputProcessor()
    override fun FrameAction() {

        spriteBatch.begin()
        prevMode.OnlyRenderFrameAction()
        spriteBatch.end()

        if(currentFrame == 0){
            player.sprite.texture = deathTexture
            spriteBatch.shader = shaderProgram
        }
        if(currentFrame == endFrame){
            spriteBatch.shader = originalShader

            changeArea(startPos, "World1")
            player.currentHealth = player.maxHealth
            changeMode(nextMode)
        }

        currentFrame += 1
    }

    override fun render() {
        prevMode.render()
    }
}