package com.mygdx.game.UI

import HeaderButton
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.changeMode

class PauseScreen(val prevMode: GameMode) : UIScreen() {
    override var activeButton: Button? = null

    override fun create() {
        super.create()
        val labelStyle = Label.LabelStyle(FontManager.ChapterFont, Color.WHITE)
        val reincarnationText = Label("Status", labelStyle)
        rootTable.background = createBackgroundDrawable(Color(0f,0f,0f,0f))
        stage.addListener  (
            object : InputListener() {
                override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                    when (keycode) {
                        Input.Keys.ESCAPE -> {
                            changeMode(prevMode)
                            return true
                        }
                    }
                    return false
                }
            })

        val buttonTable = Table()
        buttonTable.height = 100f
        val headerButton1 = HeaderButton("Status", labelStyle, buttonTable)
        val headerButton2 = HeaderButton("Ability", labelStyle, buttonTable)
        val headerButton3 = HeaderButton("Inventory", labelStyle, buttonTable)
        buttonTable.add(headerButton1).expand().center()
        buttonTable.add(headerButton2).expand().center()
        buttonTable.add(headerButton3).expand().center()
        buttonTable.background = createBackgroundDrawable(Color.BLACK)

        val contentTable = Table()
        contentTable.add(reincarnationText).expand().center()
        contentTable.background = createBackgroundDrawable(backgroundColor)


        //Expand og fill
        rootTable.add(buttonTable).fill().width(rootTable.width / 1.5f)
        rootTable.row()
        rootTable.add(contentTable).expand().fill()

    }
    override fun render() {
        stage.act(Gdx.graphics.deltaTime)
        stage.isDebugAll = true
        stage.draw()
        activeButton?.let {
            val stageCoords = it.localToParentCoordinates(Vector2(it.parent.x, it.parent.y));

            shapeRenderer.projectionMatrix = stage.camera.combined
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
            shapeRenderer.setColor(0f, 1f, 0f, 1f)
            shapeRenderer.circle(stageCoords.x + it.width / 2, stageCoords.y + it.height / 2, it.width / 2 + 10)
            shapeRenderer.end()
        }

    }
}