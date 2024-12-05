package com.mygdx.game.GameModes

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.UI.Conversation.Conversation
import com.mygdx.game.UI.Conversation.SpeechTextBubble
import com.mygdx.game.Utils.RenderGraph
import com.mygdx.game.getMapObjects
import com.mygdx.game.mainCamera

class MapMode(val prevMode: GameMode): GameMode {
    override val spriteBatch = SpriteBatch()
    override val inputProcessor = MapInputProcessor(this)
    val mapToDraw = mutableListOf<GameObject>()
    var prevZoom: Float = 0f
    override fun modeInit() {
        super.modeInit()
        mapToDraw.addAll(getMapObjects())
        prevZoom = mainCamera.zoom

        mainCamera.zoom += 20f
    }

    override fun FrameAction() {
        super.FrameAction()
        mapToDraw.forEach {
            RenderGraph.addToSceneGraph(it)
        }
    }
    override fun render() {
        RenderGraph.render(spriteBatch)
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