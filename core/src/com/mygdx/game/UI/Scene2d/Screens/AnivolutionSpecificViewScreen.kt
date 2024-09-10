package com.mygdx.game.UI.Scene2d.Screens

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.ANIMELIA_STAGE
import com.mygdx.game.Animelia.getAnimeliaData
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.UIMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.UI.Scene2d.mediumLabel
import com.mygdx.game.UI.Scene2d.smallToMediumLabel
import com.mygdx.game.generalSaveState


class AnivolutionSpecificViewScreen(override var prevMode: GameMode?, val selectedAnimelia: ANIMELIA_ENTITY): UIScreen(){

    override var activeButton: Actor? = null
    override var renderPrevGameMode = true


    fun addEntity(entity: ANIMELIA_ENTITY, table: Table){
        val data = getAnimeliaData(entity)
        val textureRegionDrawable = TextureRegionDrawable(data.gameTexture)
        textureRegionDrawable.setMinSize(64f,64f)
        val button = ImageButton(textureRegionDrawable)
        if(data.animeliaStage == ANIMELIA_STAGE.JUNIOR){
            table.add(button).width(64f).height(64f).padBottom(100f)
        }
        else {
            table.add(button).width(100f).height(100f).padBottom(100f)
        }

        button.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                changeMode(UIMode(AnivolutionSpecificViewScreen(prevMode, entity)))
            }
        })
        buttons.add(button)
        if(data.animeliaStage == ANIMELIA_STAGE.MASTER || data.animeliaStage == ANIMELIA_STAGE.GRANDMASTER){
            val textureRegionDrawable = TextureRegionDrawable(DefaultTextureHandler.getTexture("book.png"))
            textureRegionDrawable.setMinSize(32f,32f)

            val image = Image(textureRegionDrawable)

            val alpha = if(data.animeliaEntity in generalSaveState.inventory.entityBooks) 1f else 0.3f
            image.color = Color(1f, 1f, 1f, alpha) // RGB = white, Alpha = 0.5 (50% transparency)

            table.add(image).top().padLeft(-32f)
        }
        table.row()
    }

    override fun create() {

        super.create()

        var juniors = mutableListOf<ANIMELIA_ENTITY>()
        var masters = mutableListOf<ANIMELIA_ENTITY>()
        var grandmasters = mutableListOf<ANIMELIA_ENTITY>()

        val combinedTable = Table()

        val juniorTable = Table()
        val masterTable = Table()
        val grandmasterTable = Table()
        val animeliaData = getAnimeliaData(selectedAnimelia)

        var mainTable = juniorTable
        var rightTable = masterTable
        var leftTable = grandmasterTable

        var leftEntities = listOf<ANIMELIA_ENTITY>()
        var rightentites = listOf<ANIMELIA_ENTITY>()
        if(animeliaData.animeliaStage == ANIMELIA_STAGE.JUNIOR){
            juniors.add(selectedAnimelia)
            masters.addAll(animeliaData.possibleAnivolutions)
            rightTable = masterTable
            rightentites = masters
        }
        else if(animeliaData.animeliaStage == ANIMELIA_STAGE.MASTER){
            masters.add(selectedAnimelia)

            ANIMELIA_ENTITY.entries.forEach {
                val data = getAnimeliaData(it)
                if(selectedAnimelia in data.possibleAnivolutions){
                    juniors.add(data.animeliaEntity)
                }
            }
            grandmasters.addAll(animeliaData.possibleAnivolutions)

            mainTable = masterTable
            rightTable = grandmasterTable
            leftTable = juniorTable
            leftEntities = juniors
            rightentites = grandmasters

        }

        else {
            grandmasters.add(selectedAnimelia)
            ANIMELIA_ENTITY.entries.forEach {
                val data = getAnimeliaData(it)
                if(selectedAnimelia in data.possibleAnivolutions){
                    masters.add(data.animeliaEntity)
                }
            }
            mainTable = grandmasterTable
            leftTable = masterTable

            leftEntities = masters
        }

        combinedTable.add(leftTable).expand().left()
        combinedTable.add(mainTable).expand().center()
        combinedTable.add(rightTable).expand().right()

        rootTable.add(combinedTable).expand().fill()

        addEntity(selectedAnimelia, mainTable)
        //Setting active to middle button.
        changeActive(0)
        leftEntities.forEach { addEntity(it, leftTable) }
        rightentites.forEach { addEntity(it, rightTable) }

        val conditionsTable = Table()


        rootTable.row()
        rootTable.add(conditionsTable)

        val anivolutionConditionLabel = Label("Anivolution Conditions: ", mediumLabel)
        conditionsTable.add(anivolutionConditionLabel)
        conditionsTable.row()

        if(selectedAnimelia in generalSaveState.inventory.entityBooks){
            animeliaData.animeliaEvolutionConditions.forEach {
                val label = Label(it.textDescription, smallToMediumLabel)
                if(it.isConditionFulfilled()){
                    label.color = Color.GREEN
                }
                conditionsTable.add(label)
                conditionsTable.row()
            }
        } else{
            val label = Label("Unknown", smallToMediumLabel)
            conditionsTable.add(label)
        }

        stage.addListener(object : InputListener() {
            override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                when (keycode) {
                    Input.Keys.ESCAPE -> {
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

    override fun render() {
        super.render()

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.color = Color.WHITE

        val stageCoordsCurrent = buttons[0].localToStageCoordinates(Vector2(0f, 0f))
        for(button in buttons.drop(1)){
            val stageCoordsNew = button.localToStageCoordinates(Vector2(0f, 0f))
            shapeRenderer.line(
                stageCoordsCurrent.x + buttons[0].width / 2,
                stageCoordsCurrent.y + buttons[0].height / 2,
                stageCoordsNew.x + button.width / 2,
                stageCoordsNew.y + button.height / 2
            );
        }

        shapeRenderer.end()
    }
}