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
import com.mygdx.game.player

class AttributeRow(val trainingScreen: TrainingScreen) {
    val labelStyle = Label.LabelStyle(FontManager.ChapterFont, Color.WHITE)
    val skin = Skin(Gdx.files.internal("ui/uiskin.json"))
    val switchSound = DefaultSoundHandler.getSound("Sound/switch6.wav")
    val unpressableColor = Color(1f, 1f, 1f, 0.3f)
    val normalColor = Color(1f, 1f, 1f, 1f)
    fun getNewTable(text: String, amount: Int, changeFunctionUp: () -> Int, changeFunctionDown: () -> Int): Table{
        val attributeTable = Table()
        val arrowTable = Table()
        val statLabel = Label("$text $amount", labelStyle)


        val upArrowTexture = TextureRegionDrawable(DefaultTextureHandler.getTexture("UpArrow.png"))
        upArrowTexture.setMinSize(96f,96f)
        val downArrowTexture = TextureRegionDrawable(DefaultTextureHandler.getTexture("DownArrow.png"))
        downArrowTexture.setMinSize(96f,96f)
        val UpButton = ImageButton(upArrowTexture)
        val DownButton = ImageButton(downArrowTexture)
        DownButton.color = unpressableColor
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

                    UpButton.color = if(trainingScreen.sp == 0) unpressableColor else normalColor
                    DownButton.color = if(trainingScreen.sp == player.stats.tp) unpressableColor else normalColor
                }
            }
        })
        DownButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                // Define what should happen when the button is clicked
                if(trainingScreen.sp < player.stats.tp){
                    trainingScreen.sp += 1

                    val newStat = changeFunctionDown()

                    statLabel.setText("$text $newStat")
                    trainingScreen.spLabel.setText("Training Points: " + trainingScreen.sp)
                    switchSound.play()

                    UpButton.color = if(trainingScreen.sp == 0) unpressableColor else normalColor
                    DownButton.color = if(trainingScreen.sp == player.stats.tp) unpressableColor else normalColor
                }
            }
        })
        return attributeTable
    }
}
