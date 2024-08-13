package com.mygdx.game.UI.Screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.mygdx.game.DefaultMusicHandler
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.Managers.MusicManager
import com.mygdx.game.UI.PauseScreenComponents.AnimeliaButton
import com.mygdx.game.UI.bigLabel
import com.mygdx.game.UI.createBackgroundDrawable

class StartScreen(val nextGameMode: GameMode): UIScreen() {
    override var activeButton: Actor? = null

    init {
        MusicManager.changeAndPlay(DefaultMusicHandler.getMusic("Music/Forest/forest.mp3"))
    }

    override fun create() {
        super.create()
        rootTable.background = createBackgroundDrawable(Color.BLACK)

        val buttonTable = Table()

        val newGameButton = AnimeliaButton("New Game", bigLabel, this, 0)
        val loadGameButton = AnimeliaButton("Load Game", bigLabel, this, 1)
        val controlsButton = AnimeliaButton("Controls", bigLabel, this, 2)
        val optionsButton = AnimeliaButton("Options", bigLabel, this, 3)

        val randomLabel = TextButton("hello", skin)

        buttons.addAll(listOf(newGameButton, loadGameButton, controlsButton, optionsButton))
        newGameButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                // Define what should happen when the button is clicked
                changeMode(nextGameMode)
                confirmSound.play()
                MusicManager.changeAndPlay(DefaultMusicHandler.getMusic("Music/Snow City Theme/snow_city.mp3"))
            }
        })
        rootTable.add(buttonTable).expand().center()

        buttonTable.add(newGameButton)
        buttonTable.row()
        buttonTable.add(loadGameButton)
        buttonTable.row()
        buttonTable.add(controlsButton)
        buttonTable.row()
        buttonTable.add(optionsButton)

    }
    override fun render() {
        super.render()
    }

}