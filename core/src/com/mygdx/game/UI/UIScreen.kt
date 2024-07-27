package com.mygdx.game.UI

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.viewport.ScreenViewport

abstract class UIScreen {
    abstract var activeButton: Button?
    val buttons = mutableListOf<Button>()
    private var activeButtonIndex: Int = 0
    val shapeRenderer: ShapeRenderer = ShapeRenderer()
    val skin = Skin(Gdx.files.internal("assets/ui/uiskin.json"))

    val stage = Stage(ScreenViewport())

    fun moveUp() {
        activeButtonIndex = (activeButtonIndex - 1 + buttons.size) % buttons.size
        activeButton = (buttons[activeButtonIndex])
    }

    fun moveDown() {
        activeButtonIndex = (activeButtonIndex + 1) % buttons.size
        activeButton = (buttons[activeButtonIndex])
    }

    fun pressEnter() {
        activeButton?.let { simulateClick(it) }
    }

    fun simulateClick(button: Button) {
        val event = InputEvent()
        event.type = InputEvent.Type.touchDown
        button.fire(event)
        event.type = InputEvent.Type.touchUp
        button.fire(event)
    }

    abstract fun create()

    abstract fun render()

}