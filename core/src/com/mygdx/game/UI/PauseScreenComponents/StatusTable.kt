package com.mygdx.game.UI.PauseScreenComponents
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.mygdx.game.UI.createBackgroundDrawable
class StatusTable(color: Color): Table() {
    init {
        val labelStyle = Label.LabelStyle(FontManager.ChapterFont, Color.WHITE)
        val reincarnationText = Label("Status", labelStyle)
        this.add(reincarnationText).expand().center()
        this.background = createBackgroundDrawable(color)

    }
}