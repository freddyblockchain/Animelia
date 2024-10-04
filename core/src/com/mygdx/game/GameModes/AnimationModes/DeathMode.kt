package com.mygdx.game.GameModes.AnimationModes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.mygdx.game.*
import com.mygdx.game.GameModes.DefaultInputProcessor
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.Managers.MusicManager

class DeathMode(val prevMode: GameMode, val nextMode: GameMode, override val spriteBatch: SpriteBatch = mainMode.spriteBatch):
    GameMode {
    var currentFrame = 0

    val endFrame = 180

    val deathBatch = SpriteBatch()

    //val currentTexture = player.sprite.texture
    val deathTexture = DefaultTextureHandler.getTexture("death.png")

    val vertexShader = Gdx.files.internal("Shaders/BlackAndWhite/vertex.glsl").readString();
    val fragmentShader = Gdx.files.internal("Shaders/BlackAndWhite/fragment.glsl").readString();
    val shaderProgram = ShaderProgram(vertexShader,fragmentShader);
    val originalShader = spriteBatch.shader

    override val inputProcessor = DefaultInputProcessor()

    override fun modeInit() {
        MusicManager.currentTrack?.stop()
    }
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