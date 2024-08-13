package com.mygdx.game.UI.Screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.mygdx.game.UI.createBackgroundDrawable
import com.mygdx.game.Utils.Center

abstract class UIScreen {
    lateinit var rootTable: Table
    var activeButtonIndex: Int = 0
    val shapeRenderer: ShapeRenderer = ShapeRenderer()
    val skin = Skin(Gdx.files.internal("assets/ui/uiskin.json"))
    open val backgroundColor = Color.DARK_GRAY

    val stage = Stage(ScreenViewport())
    val buttons: MutableList<Actor> = mutableListOf()

    abstract var activeButton: Actor?

    fun moveUp() {
        activeButtonIndex = (activeButtonIndex + 1) % buttons.size

        changeActive(activeButtonIndex)
    }

    fun moveDown() {
        activeButtonIndex = if(activeButtonIndex - 1 < 0 ) buttons.size - 1 else activeButtonIndex - 1

        changeActive(activeButtonIndex)
    }

    fun pressEnter() {
        activeButton?.let { simulateClick(it) }
    }

    fun simulateClick(actor: Actor) {
        val event = InputEvent()
        event.type = InputEvent.Type.touchDown
        actor.fire(event)
        event.type = InputEvent.Type.touchUp
        actor.fire(event)
    }

    open fun create(){
        rootTable = Table()
        stage.addListener(
            object : InputListener() {
                override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                    when (keycode) {
                        Input.Keys.RIGHT, Input.Keys.DOWN -> {
                            moveUp()
                            return true
                        }

                        Input.Keys.LEFT, Input.Keys.UP -> {
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

    open fun changeActive(activeIndex: Int){
        activeButtonIndex = activeIndex
    }

    open fun render(){
        stage.act(Gdx.graphics.deltaTime)
        stage.isDebugAll = true
        stage.draw()

       /* buttons.forEachIndexed { index, actor ->
            val stageCoords = actor.localToStageCoordinates(Vector2(0f, 0f))
            val origColor = shapeRenderer.color
            val color = if (index == activeButtonIndex) Color.GREEN else Color.WHITE
            shapeRenderer.projectionMatrix = stage.camera.combined
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
            shapeRenderer.setColor(color)
            shapeRenderer.rect(stageCoords.x, stageCoords.y, actor.width, actor.height)
            shapeRenderer.color = origColor
            shapeRenderer.end()
        }*/

    }

}