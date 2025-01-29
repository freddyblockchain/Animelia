package com.mygdx.game.GameModes

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.Ability.Abilities.Fire.getAngleModifier
import com.mygdx.game.Animelia.setAnimeliaSpriteTexture
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Rendering.Renderable
import com.mygdx.game.UI.Conversation.Conversation
import com.mygdx.game.UI.Conversation.SpeechTextBubble
import com.mygdx.game.Utils.RenderGraph
import com.mygdx.game.getMapObjects
import com.mygdx.game.mainCamera
import com.mygdx.game.player

class MapMode(val prevMode: GameMode): GameMode {
    override val spriteBatch = SpriteBatch()
    override val inputProcessor = MapInputProcessor(this)
    val mapToDraw = mutableListOf<Renderable>()
    var prevZoom: Float = 0f

    val playerSprite = Sprite(DefaultTextureHandler.getTexture(player.animeliaInfo.textureName))
    val mapPlayer = MapPlayer(playerSprite)
    override fun modeInit() {
        super.modeInit()
        mapToDraw.addAll(getMapObjects())
        prevZoom = mainCamera.zoom

       // playerSprite.texture = DefaultTextureHandler.getTexture("book.png")
        playerSprite.setPosition(player.currentPosition().x - 32f * 3, player.currentPosition().y - 32f * 3)
        playerSprite.setSize(32f*6, 32f * 6)
        playerSprite.setOriginCenter()

        playerSprite.rotation = (getAngleModifier(player) + 180) % 360

        mainCamera.zoom += 15f
    }

    override fun FrameAction() {
        super.FrameAction()
        mapToDraw.forEach {
            RenderGraph.addToSceneGraph(it)
        }
        mapToDraw.add(MapPlayer(playerSprite))
    }
    override fun render() {
        RenderGraph.render(spriteBatch)
    }
}

class MapPlayer(val sprite: Sprite): Renderable {
    override val layer = Layer.AIR

    override fun render(batch: SpriteBatch) {
        sprite.draw(batch)
    }
}

class MapInputProcessor(val mapMode: MapMode) : DefaultInputProcessor(){
    override fun keyDown(keycode: Int): Boolean {
        if(keycode == Input.Keys.M || keycode == Input.Keys.ESCAPE){
            mainCamera.zoom = mapMode.prevZoom
            changeMode(mapMode.prevMode)
        }
        return true
    }
}