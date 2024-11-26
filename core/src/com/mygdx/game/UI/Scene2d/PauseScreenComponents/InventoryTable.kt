package com.mygdx.game.UI.Scene2d.PauseScreenComponents
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.mygdx.game.Ability.getAbilitiesFromType
import com.mygdx.game.Ability.getIconFromType
import com.mygdx.game.Items.getKeyItemTextures
import com.mygdx.game.Items.getMaterialTexture
import com.mygdx.game.UI.Scene2d.PauseScreenComponents.AbilityTable.AbilityButton
import com.mygdx.game.UI.Scene2d.createBackgroundDrawable
import com.mygdx.game.generalSaveState

class InventoryTable(color: Color): Table() {
    init {
        val labelStyle = Label.LabelStyle(FontManager.ChapterFont, Color.WHITE)
        val reincarnationText = Label("Inventory", labelStyle)
        this.add(reincarnationText).top()
        this.background = createBackgroundDrawable(color)

        this.row()

        val combinedTable = Table()
        val itemTable = Table()
        val keyItemTable = Table()

        combinedTable.add(itemTable).expand()
        combinedTable.add(keyItemTable).expand()

        this.add(combinedTable).expand()

        val presentMaterials = generalSaveState.inventory.materialItems.filter { it.value > 0 }
        println(presentMaterials.size)
        for((i,material) in presentMaterials.keys.withIndex()){
            val texture = getMaterialTexture(material)
            val textureRegionDrawable = TextureRegionDrawable(texture)
            textureRegionDrawable.setMinSize(128f,128f)
            itemTable.add(ImageButton(textureRegionDrawable)).expand()
            if( (i + 1) % 3 == 0){
                itemTable.row()
            }
        }

        for(keyItem in generalSaveState.inventory.keyItems){
            val texture = getKeyItemTextures(keyItem)
            val textureRegionDrawable = TextureRegionDrawable(texture)
            textureRegionDrawable.setMinSize(128f,128f)
            keyItemTable.add(ImageButton(textureRegionDrawable)).expand()
            keyItemTable.row()
        }
    }
}