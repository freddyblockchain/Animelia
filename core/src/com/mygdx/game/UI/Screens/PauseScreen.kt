package com.mygdx.game.UI.Screens
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.UI.PauseScreenComponents.AbilityTable.AbilityTable
import com.mygdx.game.UI.PauseScreenComponents.AnimeliaButton
import com.mygdx.game.UI.PauseScreenComponents.InventoryTable
import com.mygdx.game.UI.PauseScreenComponents.StatusTable
import com.mygdx.game.UI.createBackgroundDrawable

class PauseScreen(val prevMode: GameMode) : UIScreen() {
    override val nrOfButtons = 3
    val statusTable = StatusTable(this.backgroundColor)
    val inventoryTable = InventoryTable(this.backgroundColor)
    val abilityTable = AbilityTable(this.backgroundColor)
    var activeTable: Table = statusTable
    override var activeButton: Actor? = null

    override fun changeActive(activeIndex: Int){
        val newTable = when(activeIndex){
            0 -> statusTable
            1 -> inventoryTable
            else -> abilityTable
        }
        val cellToChange = this.rootTable.getCell(this.activeTable)
        cellToChange.clearActor()
        cellToChange.setActor(newTable)
        this.activeTable = newTable

        super.changeActive(activeIndex)
    }


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
        val headerButton1 = AnimeliaButton("Status", labelStyle, this, 0)
        val headerButton2 = AnimeliaButton("Ability", labelStyle, this, 1)
        val headerButton3 = AnimeliaButton("Inventory", labelStyle, this,2)
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
        /*activeButton?.let {
            val stageCoords = it.localToParentCoordinates(Vector2(it.parent.x, it.parent.y));

            shapeRenderer.projectionMatrix = stage.camera.combined
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
            shapeRenderer.setColor(0f, 1f, 0f, 1f)
            shapeRenderer.circle(stageCoords.x + it.width / 2, stageCoords.y + it.height / 2, it.width / 2 + 10)
            shapeRenderer.end()
        }*/

    }
}