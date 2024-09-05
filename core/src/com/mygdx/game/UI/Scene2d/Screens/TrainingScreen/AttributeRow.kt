package com.mygdx.game.UI.Scene2d.Screens.TrainingScreen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.mygdx.game.DefaultSoundHandler
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Managers.PlayerStatus

class AttributeRow(val trainingScreen: TrainingScreen) {
    val upArrowTexture = TextureRegionDrawable(DefaultTextureHandler.getTexture("UpArrow.png"))
    val downArrowTexture = TextureRegionDrawable(DefaultTextureHandler.getTexture("DownArrow.png"))
    val labelStyle = Label.LabelStyle(FontManager.ChapterFont, Color.WHITE)
    val skin = Skin(Gdx.files.internal("assets/ui/uiskin.json"))
    val switchSound = DefaultSoundHandler.getSound("Sound/switch6.wav")

    init {
        upArrowTexture.setMinSize(64f,64f)
        downArrowTexture.setMinSize(64f,64f)
    }

    fun getNewTable(text: String, amount: Int, changeFunctionUp: () -> Int, changeFunctionDown: () -> Int): Table{
        val attributeTable = Table()
        val arrowTable = Table()
        val statLabel = Label("$text $amount", labelStyle)


        val upArrowTexture = TextureRegionDrawable(DefaultTextureHandler.getTexture("UpArrow.png"))
        upArrowTexture.setMinSize(64f,64f)
        val downArrowTexture = TextureRegionDrawable(DefaultTextureHandler.getTexture("DownArrow.png"))
        downArrowTexture.setMinSize(64f,64f)
        val UpButton = ImageButton(upArrowTexture)
        val DownButton = ImageButton(downArrowTexture)
        arrowTable.add(UpButton)
        arrowTable.add(DownButton)

        attributeTable.add(statLabel).padRight(100f)
        attributeTable.add(arrowTable)

        UpButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                // Define what should happen when the button is clicked
                if(trainingScreen.sp > 0){
                    trainingScreen.sp -= 1

                    val newOffence = changeFunctionUp()


                    statLabel.setText("$text $newOffence")
                    trainingScreen.spLabel.setText("Training Points: " + trainingScreen.sp)
                    switchSound.play()
                }
            }
        })
        DownButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                // Define what should happen when the button is clicked
                if(trainingScreen.sp < PlayerStatus.tp){
                    trainingScreen.sp += 1

                    val newStat = changeFunctionDown()

                    statLabel.setText("$text $newStat")
                    trainingScreen.spLabel.setText("Training Points: " + trainingScreen.sp)
                    switchSound.play()
                }
            }
        })
        return attributeTable
    }
}
