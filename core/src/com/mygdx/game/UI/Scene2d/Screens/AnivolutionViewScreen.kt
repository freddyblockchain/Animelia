package com.mygdx.game.UI.Scene2d.Screens
import FontManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.ANIMELIA_STAGE
import com.mygdx.game.Animelia.getAnimeliaData
import com.mygdx.game.UI.Scene2d.PauseScreenComponents.AbilityTable.AbilityButton
import com.mygdx.game.UI.Scene2d.createBackgroundDrawable


class AnivolutionViewScreen(override var prevMode: GameMode?): UIScreen(){

    override var activeButton: Actor? = null
    override var renderPrevGameMode = true

    override fun create() {

        super.create()

        val juniorTable = Table()
        val masterTable = Table()

        val backgroundColor = Color(1f, 1f, 1f, 1f) // Example color

        rootTable.background = createBackgroundDrawable(backgroundColor)
        rootTable.add(juniorTable).padRight(400f).padTop(200f).expand().right()
        rootTable.add(masterTable).padRight(200f).padTop(150f).expand().left()
        val skin = Skin(Gdx.files.internal("assets/ui/uiskin.json"))

        val labelStyle = Label.LabelStyle(FontManager.MediumFont, Color.BLACK)

        val juniorLabel = Label("Junior", labelStyle)
        val masterLabel = Label("Master", labelStyle)

        juniorTable.add(juniorLabel).size(50f).padBottom(20f).padRight(150f)
        masterTable.add(masterLabel).size(50f).expand().padBottom(20f).padRight(150f)
        juniorTable.row()
        masterTable.row()

        ANIMELIA_ENTITY.entries.forEach {
            val data = getAnimeliaData(it)
            val textureRegionDrawable = TextureRegionDrawable(data.gameTexture)
            textureRegionDrawable.setMinSize(64f,64f)
            val button = ImageButton(textureRegionDrawable)
            if(data.animeliaStage == ANIMELIA_STAGE.JUNIOR){
                juniorTable.add(button).width(64f).height(64f).padBottom(100f)
                juniorTable.row()
            }
            else {
                masterTable.add(button).width(100f).height(100f).padBottom(100f)
                masterTable.row()
            }
            buttons.add(button)
        }

        stage.addListener(object : InputListener() {
            override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                when (keycode) {
                    Input.Keys.ENTER -> {
                        changeMode(prevMode!!)
                        return true
                    }
                    else -> return false
                }
            }
        })




        //table.debug = true // This is optional, but enables debug lines for tables.

        /*juniorTable.add(button).center().width(100f).height(100f)
        juniorTable.row()
        juniorTable.add(button2).center().width(100f).height(100f)
        juniorTable.row()
        juniorTable.add(button3).center().width(100f).height(100f)

        buttons.addAll(listOf(button, button2, button3))*/

        //setActiveButton(buttons[0])

    }
}