package com.mygdx.game.UI.Scene2d.Screens.TrainingScreen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.mygdx.game.Animelia.anivolutionCheck
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.Managers.PlayerStatus
import com.mygdx.game.UI.Scene2d.Screens.UIScreen
import com.mygdx.game.player

class TrainingScreen(override var prevMode: GameMode?): UIScreen() {

    override var activeButton: Actor? = null
    override var renderPrevGameMode = true

    var offence = player.stats.offence
    var defence = player.stats.defence
    var speed = player.stats.speed
    var intelligence = player.stats.intelligence
    var sp = PlayerStatus.sp

    val labelStyle = Label.LabelStyle(FontManager.ChapterFont, Color.WHITE)
    val spLabel = Label("Training Points: " + PlayerStatus.sp, labelStyle)


    override fun create() {
        super.create()



        rootTable.add(spLabel).padBottom(100f)
        rootTable.row()
        val offenceAttributeRow = AttributeRow(this)


        val offenceTable = offenceAttributeRow.getNewTable("Offence", offence, {offence += 1; offence},{offence -= 1; offence})
        val defenceTable = offenceAttributeRow.getNewTable("Defence", defence, {defence += 1; defence},{defence -= 1; defence})
        val speedTable = offenceAttributeRow.getNewTable("Speed", speed, {speed += 1; speed},{speed -= 1; speed})
        val intelligenceTable = offenceAttributeRow.getNewTable("Intelligence", intelligence, {intelligence += 1; intelligence},{intelligence -= 1; intelligence})

        rootTable.add(offenceTable)

        rootTable.row()
        rootTable.add(defenceTable)
        rootTable.row()
        rootTable.add(speedTable)
        rootTable.row()
        rootTable.add(intelligenceTable)
        rootTable.row()

        val finishTrainingButton = TextButton("Confirm Training", skin)
        rootTable.add(finishTrainingButton).center().padTop(20f).size(100f)

        finishTrainingButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                PlayerStatus.sp = sp
                player.stats.offence = offence
                player.stats.defence = defence
                player.stats.speed = speed
                player.stats.intelligence = intelligence

                changeMode(prevMode!!)
                anivolutionCheck()
            }
        })





        stage.addListener(object : InputListener() {
            override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                when (keycode) {
                    Input.Keys.SPACE -> {
                        changeMode(prevMode!!)
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

    override fun render() {
        stage.act(Gdx.graphics.deltaTime)
       // stage.isDebugAll = true
        stage.draw()
    }
}