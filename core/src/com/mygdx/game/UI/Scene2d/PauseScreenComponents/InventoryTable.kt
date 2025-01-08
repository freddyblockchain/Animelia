package com.mygdx.game.UI.Scene2d.PauseScreenComponents
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.mygdx.game.Ability.getDescriptionFromName
import com.mygdx.game.Items.getItemDescription
import com.mygdx.game.Items.getKeyItemTextures
import com.mygdx.game.Items.getMaterialTexture
import com.mygdx.game.UI.Scene2d.createBackgroundDrawable
import com.mygdx.game.UI.Scene2d.mediumLabel
import com.mygdx.game.UI.Scene2d.smallToMediumLabel
import com.mygdx.game.generalSaveState
import com.mygdx.game.player

class InventoryTable(color: Color): Table() {
    init {
        val itemDescription = Label("", smallToMediumLabel)

        val itemsText = Label("Items", mediumLabel)
        val keyItemsText = Label("KeyItems", mediumLabel)
        this.background = createBackgroundDrawable(color)

        this.row()

        /*val labelTable = Table()
        labelTable.add(itemsText)
        labelTable.add(keyItemsText)*/

        val combinedTable = Table()
        val itemTable = Table()
        val keyItemTable = Table()

        combinedTable.add(itemsText).padRight(80f)
        combinedTable.add(keyItemsText).expand().center()
        combinedTable.row()
        combinedTable.add(itemTable).expand().padRight(100f)
        combinedTable.add(keyItemTable).expand().padTop(50f)

        this.add(combinedTable).expand().top()

        itemDescription.setAlignment(Align.center)
        itemDescription.setWrap(true)// Align text in the center
        this.row()
        this.add(itemDescription).fillX().height(100f)

        val presentMaterials = generalSaveState.inventory.materialItems.filter { it.value > 0 }
        println(presentMaterials.size)
        for((i,material) in presentMaterials.entries.withIndex()){
            val numLabel = Label(material.value.toString(), mediumLabel)
            val numAndButtonTable = Table()

            val material = material.key
            val texture = getMaterialTexture(material)
            val textureRegionDrawable = TextureRegionDrawable(texture)
            textureRegionDrawable.setMinSize(128f,128f)

            numAndButtonTable.add(numLabel)
            numAndButtonTable.row()
            numAndButtonTable.add(InventoryItem(textureRegionDrawable, itemDescription, material.name))
            itemTable.add(numAndButtonTable).expand().pad(20f)
            if( (i + 1) % 3 == 0){
                itemTable.row()
            }
        }

        for(keyItem in generalSaveState.inventory.keyItems){
            val texture = getKeyItemTextures(keyItem)
            val textureRegionDrawable = TextureRegionDrawable(texture)
            textureRegionDrawable.setMinSize(128f,128f)
            keyItemTable.add(InventoryItem(textureRegionDrawable, itemDescription, keyItem.name)).expand().pad(20f)
            keyItemTable.row()
        }
    }
}

class InventoryItem(drawable: TextureRegionDrawable, itemDescription: Label, itemType: String): ImageButton(drawable){
    private val shapeRenderer: ShapeRenderer = ShapeRenderer()

    init {
        this.addListener(object : ClickListener() {

            override fun enter(event: InputEvent?, x: Float, y: Float, pointer: Int, fromActor: Actor?) {
                itemDescription.setText(getItemDescription(itemType))
                super.enter(event, x, y, pointer, fromActor)
            }

            override fun exit(event: InputEvent?, x: Float, y: Float, pointer: Int, toActor: Actor?) {
                itemDescription.setText("")
                super.exit(event, x, y, pointer, toActor)
            }
        })
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        batch?.end() // End the current batch before using ShapeRenderer

        val stageCoords = this.localToStageCoordinates(Vector2(0f, 0f))
        val color = Color.WHITE
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.color = color
        shapeRenderer.rect(stageCoords.x, stageCoords.y, this.width, this.height)
        shapeRenderer.end()

        batch?.begin() // Restart the batch after using ShapeRenderer
    }
}