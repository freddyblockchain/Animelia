package com.mygdx.game.UI

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.Managers.PlayerStatus

class TrainingScreen(val prevMode: GameMode, val stage: Stage): UIScreen() {
    override var activeButton: Button? = null
    lateinit var rootTable: Table

    fun create() {
        rootTable = Table()
        val arrowTable = Table()

        Gdx.input.inputProcessor = stage
        rootTable.setFillParent(true)


        val backgroundColor = Color(1f, 1f, 1f, 1f) // Example color

        rootTable.background = createBackgroundDrawable(backgroundColor)
        stage.addActor(rootTable)

        val labelStyle = Label.LabelStyle(FontManager.ChapterFont, Color.BLACK)

        var offence = PlayerStatus.playerStats.offence
        var defence = PlayerStatus.playerStats.defence
        var speed = PlayerStatus.playerStats.speed
        var intelligence = PlayerStatus.playerStats.intelligence

        var age = PlayerStatus.age
        var exp = PlayerStatus.exp

        val expLabel = Label("Exp: " + PlayerStatus.exp, labelStyle)
        val ageLabel = Label("Age " + PlayerStatus.age, labelStyle)

        rootTable.add(expLabel)
        rootTable.add(ageLabel).padLeft(30f)
        rootTable.row()

        val offenceLabel = Label("Offence $offence", labelStyle)


        val upArrowTexture = TextureRegionDrawable(DefaultTextureHandler.getTexture("UpArrow.png"))
        upArrowTexture.setMinSize(64f,64f)
        val downArrowTexture = TextureRegionDrawable(DefaultTextureHandler.getTexture("DownArrow.png"))
        downArrowTexture.setMinSize(64f,64f)
        val UpButton = ImageButton(upArrowTexture)
        val DownButton = ImageButton(downArrowTexture)
        arrowTable.add(UpButton)
        arrowTable.add(DownButton)

        rootTable.add(offenceLabel)
        rootTable.add(arrowTable)

        rootTable.row()
        val finishTrainingButton = TextButton("FinishTraining", skin)
        rootTable.add(finishTrainingButton).right().padTop(20f).size(100f)

        UpButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                // Define what should happen when the button is clicked
                println("Button clicked!")
                age += 1
                exp -= 1
                offence += 1


                offenceLabel.setText("Offence $offence")
                expLabel.setText("Exp: $exp")
                ageLabel.setText("Age $age")
            }
        })
        DownButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                // Define what should happen when the button is clicked
                println("Button clicked!")
                age -= 1
                exp += 1
                offence -= 1


                offenceLabel.setText("Offence $offence")
                expLabel.setText("Exp: $exp")
                ageLabel.setText("Age $age")
            }
        })

        finishTrainingButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                PlayerStatus.age = age
                PlayerStatus.exp = exp
                PlayerStatus.playerStats.offence = offence
                changeMode(prevMode)
            }
        })





        stage.addListener(object : InputListener() {
            override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                when (keycode) {
                    Input.Keys.SPACE -> {
                        changeMode(prevMode)
                        return true
                    }
                    Input.Keys.UP -> {
                        moveUp()
                        return true
                    }
                    Input.Keys.DOWN -> {
                        moveDown()
                        return true
                    }
                    Input.Keys.ENTER -> {
                        pressEnter()
                        return true
                    }
                    else -> return false
                }
            }
        })

        //activeButton = buttons[0]

    }

    fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(Gdx.graphics.deltaTime)
        stage.isDebugAll = true
        stage.draw()

    }

}