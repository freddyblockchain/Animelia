package com.mygdx.game.UI.PauseScreenComponents.AbilityTable
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.mygdx.game.UI.createBackgroundDrawable
import com.mygdx.game.UI.bigLabel

class AbilityTable(color: Color): Table() {
    init {
        val abilityText = Label("Abilities", bigLabel)
        this.add(abilityText).top()
        this.background = createBackgroundDrawable(color)

        this.row()
        val iconRootTable = Table()
        val firstIconTable = IconTable(1)
        val secondIconTable = IconTable(2)
        iconRootTable.add(firstIconTable).expand().top()
        iconRootTable.add(secondIconTable).expand().top()
        this.add(iconRootTable).expandY().top()

        this.row()
        val iconTableList = listOf(firstIconTable, secondIconTable)
        this.add(AbilityPickTable(iconTableList)).expandY().top()

    }
}