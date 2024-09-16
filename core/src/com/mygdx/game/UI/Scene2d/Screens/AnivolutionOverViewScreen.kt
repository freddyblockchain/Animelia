package com.mygdx.game.UI.Scene2d.Screens
import FontManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.ANIMELIA_STAGE
import com.mygdx.game.Animelia.getAnimeliaData
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.GameModes.UIMode
import com.mygdx.game.UI.Scene2d.PauseScreenComponents.AnimeliaButton
import com.mygdx.game.UI.Scene2d.bigLabel
import com.mygdx.game.UI.Scene2d.createBackgroundDrawable
import com.mygdx.game.currentGameMode
import com.mygdx.game.generalSaveState


class AnivolutionOverViewScreen(override var prevMode: GameMode?): UIScreen(){

    override var activeButton: Actor? = null
    override var renderPrevGameMode = true
    var currentIndex = 0

    override fun create() {

        super.create()

        val juniorTable = Table()
        val masterTable = Table()

        val combinedTable = Table()


        combinedTable.add(juniorTable).padRight(400f).padTop(100f).expand().right()
        combinedTable.add(masterTable).padRight(200f).padTop(50f).expand().left()

        rootTable.add(combinedTable).expand().fill()


        val skin = Skin(Gdx.files.internal("ui/uiskin.json"))

        val labelStyle = Label.LabelStyle(FontManager.MediumFont, Color.BLACK)

        val juniorLabel = Label("Junior", labelStyle)
        val masterLabel = Label("Master", labelStyle)

        juniorTable.add(juniorLabel).size(50f).padBottom(20f).padRight(150f)
        masterTable.add(masterLabel).size(50f).expand().padBottom(20f)
        juniorTable.row()
        masterTable.row()

        ANIMELIA_ENTITY.entries.forEach {
            val data = getAnimeliaData(it)
            val textureRegionDrawable = TextureRegionDrawable(data.gameTexture)
            textureRegionDrawable.setMinSize(64f,64f)
            val button = ImageButton(textureRegionDrawable)
            if(data.animeliaStage == ANIMELIA_STAGE.JUNIOR){
                juniorTable.add(button).width(64f).height(64f).padBottom(50f)
                juniorTable.row()
            }
            else {
                masterTable.add(button).width(100f).height(100f).padBottom(50f).padRight(0f)
                val textureRegionDrawable = TextureRegionDrawable(DefaultTextureHandler.getTexture("book.png"))
                textureRegionDrawable.setMinSize(32f,32f)

                val image = Image(textureRegionDrawable)

                val alpha = if(data.animeliaEntity in generalSaveState.inventory.entityBooks) 1f else 0.3f
                image.color = Color(1f, 1f, 1f, alpha) // RGB = white, Alpha = 0.5 (50% transparency)

                masterTable.add(image).top().padLeft(-32f)
                masterTable.row()
            }
            val indexForButton = currentIndex
            button.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    // Define what should happen when the button is clicked
                    changeMode(UIMode(AnivolutionSpecificViewScreen(associatedMode, ANIMELIA_ENTITY.entries[indexForButton])))
                }
            })

            buttons.add(button)
            currentIndex += 1
        }
        //Exit button
        val animeliaButton = AnimeliaButton("Exit", bigLabel, this, buttons.size)
        animeliaButton.setAlignment(Align.center)
        buttons.add(animeliaButton)
        this.activeButtonIndex = animeliaButton.activeIndex
        this.activeButton = animeliaButton
        rootTable.row()
        rootTable.add(animeliaButton).expand().width(200f).top()

        animeliaButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                // Define what should happen when the button is clicked
                val id = backSound.play()
                backSound.setVolume(id,0.2f)
                changeMode(prevMode!!)
            }
        })

        //Exit

        stage.addListener(object : InputListener() {
            override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                when (keycode) {
                    Input.Keys.ESCAPE, Input.Keys.SPACE -> {
                        val id = backSound.play()
                        backSound.setVolume(id,0.2f)
                        changeMode(prevMode!!)
                        return true
                    }
                    else -> return false
                }
            }
        })

    }
}