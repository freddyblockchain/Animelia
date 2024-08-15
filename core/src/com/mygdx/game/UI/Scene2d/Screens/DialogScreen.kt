package com.mygdx.game.UI.Scene2d.Screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.UI.Scene2d.PauseScreenComponents.AnimeliaButton
import com.mygdx.game.UI.Scene2d.bigLabel
import com.mygdx.game.UI.Scene2d.createBackgroundDrawable
import com.mygdx.game.UI.Scene2d.mediumLabel

class DialogScreen(override var prevMode: GameMode?, val nextGameMode: GameMode, val description: String, val onChange: () -> Unit = {}): UIScreen() {
    override var activeButton: Actor? = null

    override var renderPrevGameMode = true

    override fun create() {
        super.create()
        rootTable.background = createBackgroundDrawable(Color(0f,0f,0f,0f))
        val newBackground = createBackgroundDrawable(Color.BLACK)

        val descriptionTable = Table()
        val descriptionLabel = Label(description, mediumLabel)
        descriptionTable.add(descriptionLabel).expand().center()

        val buttonTable = Table()

        val yesButton = AnimeliaButton("Yes", bigLabel, this, 0)
        val noButton = AnimeliaButton("No", bigLabel, this, 1)
        yesButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                // Define what should happen when the button is clicked
                onChange()
                changeMode(nextGameMode)
                confirmSound.play()
            }
        })
        noButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                // Define what should happen when the button is clicked
                changeMode(prevMode!!)
                backSound.play()
            }
        })

        val wholeTable = Table()

        buttons.addAll(listOf( yesButton, noButton))

        buttonTable.add(yesButton).expand().center().padRight(25f)
        buttonTable.add(noButton).expand().center()

        wholeTable.add(descriptionTable).expand().top().padTop(10f).padBottom(25f)
        wholeTable.row()
        wholeTable.add(buttonTable).expand().top().padBottom(25f)
        wholeTable.background = newBackground
        rootTable.add(wholeTable).expand().top().padBottom(50f)

    }
    override fun render() {
        super.render()
    }

}