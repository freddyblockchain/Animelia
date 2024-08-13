package com.mygdx.game.UI.Screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.UI.PauseScreenComponents.AnimeliaButton
import com.mygdx.game.UI.bigLabel
import com.mygdx.game.UI.createBackgroundDrawable

class StartScreen(val nextGameMode: GameMode): UIScreen() {
    override var activeButton: Actor? = null

    override fun create() {
        super.create()
        rootTable.background = createBackgroundDrawable(Color.BLACK)

        val buttonTable = Table()

        val newGameButton = AnimeliaButton("hello", bigLabel, this, 1)
        val loadGameButton = AnimeliaButton("Load Game", bigLabel, this, 2)
        val controlsButton = AnimeliaButton("Controls", bigLabel, this, 3)
        val optionsButton = AnimeliaButton("Options", bigLabel, this, 4)

        val randomLabel = TextButton("hello", skin)

        buttons.addAll(listOf(newGameButton, loadGameButton, controlsButton, optionsButton))
        loadGameButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                // Define what should happen when the button is clicked
                changeMode(nextGameMode)
            }
        })

        randomLabel.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                changeMode(nextGameMode)
            }
        })

        controlsButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                // Define what should happen when the button is clicked
                println("clicked!")
            }
        })
        rootTable.add(buttonTable).expand().center()

        buttonTable.add(randomLabel)
        /*buttonTable.row()
        buttonTable.add(loadGameButton)
        buttonTable.row()
        buttonTable.add(controlsButton)
        buttonTable.row()
        buttonTable.add(optionsButton)*/

    }
    override fun render() {
        super.render()
    }

}