package com.mygdx.game.UI.Scene2d.Screens
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.UI.Scene2d.PauseScreenComponents.AbilityTable.AbilityTable
import com.mygdx.game.UI.Scene2d.PauseScreenComponents.AnimeliaButton
import com.mygdx.game.UI.Scene2d.PauseScreenComponents.InventoryTable
import com.mygdx.game.UI.Scene2d.PauseScreenComponents.StatusTable
import com.mygdx.game.UI.Scene2d.createBackgroundDrawable

class PauseScreen(override var prevMode: GameMode?) : UIScreen() {
    val statusTable = StatusTable(this.backgroundColor)
    //val inventoryTable = InventoryTable(this.backgroundColor)
    val abilityTable = AbilityTable(this.backgroundColor)
    var activeTable: Table = statusTable

    override var renderPrevGameMode = true
    override var activeButton: Actor? = null

    override fun changeActive(activeIndex: Int){
        val newTable = when(activeIndex){
            0 -> statusTable
            else -> abilityTable
            //else -> inventoryTable
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
                        Input.Keys.SPACE, Input.Keys.ESCAPE -> {
                            changeMode(prevMode!!)

                            val id = backSound.play()
                            backSound.setVolume(id,0.2f)
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
        //val headerButton3 = AnimeliaButton("Inventory", labelStyle, this,2)
        buttons.addAll(listOf( headerButton1, headerButton2))
        buttonTable.add(headerButton1).expand().center()

        buttonTable.add(headerButton2).expand().center()
        //buttonTable.add(headerButton3).expand().center()
        buttonTable.background = createBackgroundDrawable(Color.BLACK)

        //Expand og fill
        rootTable.add(buttonTable).fill().width(rootTable.width / 1.5f)
        rootTable.row()
        rootTable.add(activeTable).expand().top().fill()

    }
    override fun render() {
        super.render()
    }
}