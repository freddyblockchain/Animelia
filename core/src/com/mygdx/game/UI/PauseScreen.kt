package com.mygdx.game.UI
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
import com.mygdx.game.UI.PauseScreenComponents.AbilityTable.AbilityTable
import com.mygdx.game.UI.PauseScreenComponents.HeaderButton
import com.mygdx.game.UI.PauseScreenComponents.InventoryTable
import com.mygdx.game.UI.PauseScreenComponents.StatusTable

class PauseScreen(val prevMode: GameMode) : UIScreen() {
    override var activeButton: Button? = null
    val statusTable = StatusTable(this.backgroundColor)
    val inventoryTable = InventoryTable(this.backgroundColor)
    val abilityTable = AbilityTable(this.backgroundColor)
    var activeTable: Table = statusTable


    override fun create() {
        super.create()
        val labelStyle = Label.LabelStyle(FontManager.ChapterFont, Color.WHITE)
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
        val headerButton1 = HeaderButton("Status", labelStyle, this, statusTable)
        val headerButton2 = HeaderButton("Ability", labelStyle, this, abilityTable)
        val headerButton3 = HeaderButton("Inventory", labelStyle, this, inventoryTable)
        buttonTable.add(headerButton1).expand().center()

        buttonTable.add(headerButton2).expand().center()
        buttonTable.add(headerButton3).expand().center()
        buttonTable.background = createBackgroundDrawable(Color.BLACK)

        //Expand og fill
        rootTable.add(buttonTable).fill().width(rootTable.width / 1.5f)
        rootTable.row()
        rootTable.add(activeTable).expand().top().fill()

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