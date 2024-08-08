package com.mygdx.game.UI

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.mygdx.game.Utils.Center

abstract class UIScreen {
    lateinit var rootTable: Table
    abstract var activeButton: Button?
    val buttons = mutableListOf<Button>()
    private var activeButtonIndex: Int = 0
    val shapeRenderer: ShapeRenderer = ShapeRenderer()
    val skin = Skin(Gdx.files.internal("assets/ui/uiskin.json"))
    open val backgroundColor = Color.DARK_GRAY

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

    open fun create(){
        rootTable = Table()
        stage.addListener(
            object : InputListener() {
                override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                    when (keycode) {
                        Input.Keys.RIGHT -> {
                            moveUp()
                            return true
                        }

                        Input.Keys.LEFT -> {
                            moveDown()
                            return true
                        }

                        Input.Keys.ENTER -> {
                            pressEnter()
                            return true
                        }
                        else -> return false
                    }
                }
            })

        Gdx.input.inputProcessor = stage
        rootTable.setFillParent(false)
        rootTable.setBackground(createBackgroundDrawable(backgroundColor))
        rootTable.width = (Gdx.graphics.width / 1.5).toFloat()
        rootTable.height = (Gdx.graphics.height / 1.25f).toFloat()
        rootTable.setPosition(Center.x / 3f, Center.y / 5f)
        stage.addActor(rootTable)
    }

    abstract fun render()

}