package com.mygdx.game.UI.Scene2d.Screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.mygdx.game.*
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.TalkMode
import com.mygdx.game.GameModes.UIMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.Other.SpiritOfAnimelia
import com.mygdx.game.Managers.*
import com.mygdx.game.Saving.SavingHandler.Companion.InitHandleSaving
import com.mygdx.game.UI.Scene2d.PauseScreenComponents.AnimeliaButton
import com.mygdx.game.UI.Scene2d.bigLabel
import com.mygdx.game.UI.Scene2d.createBackgroundDrawable

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
        val controlsButton = AnimeliaButton("Controls", bigLabel, this, 1)
        val loadGameButton = AnimeliaButton("Load Game", bigLabel, this, 2)
        //val optionsButton = AnimeliaButton("Options", bigLabel, this, 2)
        val randomLabel = TextButton("hello", skin)

        buttons.addAll(listOf(newGameButton, controlsButton))

        if(!FileHandler.SaveFileEmpty()){
            buttons.add(loadGameButton)
            //changeActive(2)
            activeButtonIndex = 2
            activeButton = loadGameButton
        }
        newGameButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                val id = confirmSound.play()
                confirmSound.setVolume(id,0.2f)
                // Define what should happen when the button is clicked
                val dialogScreen = DialogScreen(currentGameMode, nextGameMode, "Are you sure you want to start new game?"){
                    initNewGame()
                }
                if(!FileHandler.SaveFileEmpty()){
                    val uiConformMode = UIMode(dialogScreen, playConfirmationSound = false)
                    changeMode(uiConformMode)
                } else{
                    initNewGame()
                }
            }
        })
        loadGameButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                val id = confirmSound.play()
                confirmSound.setVolume(id,0.2f)
                initAndGoToGame()
                MusicManager.changeAndPlay(DefaultMusicHandler.getMusic("Music/Snow City Theme/snow_city.mp3"))
            }
        })
        rootTable.add(buttonTable).expand().center()

        if(!FileHandler.SaveFileEmpty()){
            buttonTable.add(loadGameButton).padBottom(20f)
            buttonTable.row()
        }
        buttonTable.add(newGameButton).padBottom(20f)
        buttonTable.row()
        buttonTable.add(controlsButton).padBottom(20f)

    }

    fun initNewGame(){
        FileHandler.clearSaves()
        initAndGoToGame()

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        mainCamera.position.set(player.sprite.x, player.sprite.y, 0f)
        mainCamera.update()
        currentGameMode.spriteBatch.projectionMatrix = mainCamera.combined
        currentGameMode.render()

        val spiritOfAnimelia: SpiritOfAnimelia = AreaManager.getActiveArea()!!.gameObjects.first { it is SpiritOfAnimelia } as SpiritOfAnimelia
        changeMode(TalkMode(spiritOfAnimelia.activeConversation, mainMode))
    }

    fun initAndGoToGame(){
        InitHandleSaving()
        changeArea(startPos, "World1")
        changeMode(nextGameMode)
        mainMode.abilityRowUi.updateToolTips()
    }

}