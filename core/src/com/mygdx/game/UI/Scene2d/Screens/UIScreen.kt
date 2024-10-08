package com.mygdx.game.UI.Scene2d.Screens

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
import com.mygdx.game.DefaultSoundHandler
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.UI.Scene2d.createBackgroundDrawable
import com.mygdx.game.Utils.Center

abstract class UIScreen {
    lateinit var rootTable: Table
    lateinit var associatedMode: GameMode
    var activeButtonIndex: Int = 0
    val shapeRenderer: ShapeRenderer = ShapeRenderer()
    val skin = Skin(Gdx.files.internal("ui/uiskin.json"))
    open val backgroundColor = Color.DARK_GRAY

    val stage = Stage(ScreenViewport())
    val buttons: MutableList<Actor> = mutableListOf()

    val confirmSound = DefaultSoundHandler.getSound("Sound/Menu FX example/Menu1A.wav")
    val backSound  = DefaultSoundHandler.getSound("Sound/Menu FX example/Menu1B.wav")
    val switchSound = DefaultSoundHandler.getSound("Sound/switch6.wav")

    open var prevMode: GameMode? = null
    open var renderPrevGameMode : Boolean = false

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
        if(buttons.size > 0){
            buttons[activeButtonIndex]?.let { simulateClick(it) }
        }
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
                            if(buttons.size > 0){
                                moveUp()
                            }
                            return true
                        }

                        Input.Keys.LEFT, Input.Keys.UP -> {
                            if(buttons.size > 0){
                                moveDown()
                            }
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
        Gdx.gl.glLineWidth(5f)
    }

    open fun changeActive(activeIndex: Int){
        activeButtonIndex = activeIndex
        switchSound.play()
    }

    open fun render(){
        stage.act(Gdx.graphics.deltaTime)
        //stage.isDebugAll = true
        stage.draw()

        buttons.forEachIndexed { index, actor ->
            val stageCoords = actor.localToStageCoordinates(Vector2(0f, 0f))
            val origColor = shapeRenderer.color
            val color = Color.WHITE
            shapeRenderer.projectionMatrix = stage.camera.combined
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
            shapeRenderer.setColor(color)
            shapeRenderer.rect(stageCoords.x, stageCoords.y, actor.width, actor.height)
            shapeRenderer.color = origColor
            shapeRenderer.end()
        }
        if(buttons.size > 0){
            buttons[activeButtonIndex]?.let {
                val stageCoords = it.localToStageCoordinates(Vector2(0f, 0f))
                val origColor = shapeRenderer.color
                val color = Color.GREEN
                shapeRenderer.projectionMatrix = stage.camera.combined
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
                shapeRenderer.setColor(color)
                shapeRenderer.rect(stageCoords.x, stageCoords.y, it.width, it.height)
                shapeRenderer.color = origColor
                shapeRenderer.end()
            }
        }

    }

}