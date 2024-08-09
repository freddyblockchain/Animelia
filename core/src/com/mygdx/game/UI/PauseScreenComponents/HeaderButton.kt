package com.mygdx.game.UI.PauseScreenComponents
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.mygdx.game.UI.PauseScreen

class HeaderButton(text: String, labelStyle: LabelStyle, pauseScreen: PauseScreen, newTable: Table): Label(text, labelStyle) {
    init {
        this.setFontScale(0.5f)
        this.fontScaleX = 0.5f
        this.color = Color.WHITE
        this.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                val cellToChange = pauseScreen.rootTable.getCell(pauseScreen.activeTable)
                cellToChange.clearActor()
                cellToChange.setActor(newTable)
                pauseScreen.activeTable = newTable
            }
        })

    }

}