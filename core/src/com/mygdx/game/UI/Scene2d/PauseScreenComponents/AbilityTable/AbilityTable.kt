package com.mygdx.game.UI.Scene2d.PauseScreenComponents.AbilityTable
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.mygdx.game.UI.Scene2d.createBackgroundDrawable
import com.mygdx.game.UI.Scene2d.bigLabel
import com.mygdx.game.player

class AbilityTable(color: Color): Table() {
    init {
        val abilityText = Label("Abilities", bigLabel)
        this.add(abilityText).top()
        this.background = createBackgroundDrawable(color)

        this.row()
        val abilitiesAvailable = (player.stats.intelligence / 5)
        val iconRootTable = Table()
        val iconTableList = mutableListOf<IconTable>()
        for(i in 1..abilitiesAvailable){
            val iconTable = IconTable(i)
            iconRootTable.add(iconTable).expand().top()
            iconTableList.add(iconTable)
        }
        this.add(iconRootTable).expandY().top()

        this.row()
        this.add(AbilityPickTable(iconTableList)).expandY().top()

    }
}