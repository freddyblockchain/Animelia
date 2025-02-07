package com.mygdx.game.GameModes.AnimationModes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.GameModes.DefaultInputProcessor
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.UIMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.MusicManager
import com.mygdx.game.UI.Scene2d.Screens.ReincarnationScreen

class DeathMode(val prevMode: GameMode, override val spriteBatch: SpriteBatch = mainMode.spriteBatch):
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
            val reincarnationMode = UIMode(ReincarnationScreen(mainMode), playConfirmationSound = false)
            player.currentHealth = player.maxHealth
            changeMode(reincarnationMode)
        }

        currentFrame += 1
    }

    override fun render() {
        prevMode.render()
    }
}