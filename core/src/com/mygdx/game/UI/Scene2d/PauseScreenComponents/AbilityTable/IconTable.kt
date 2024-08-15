package com.mygdx.game.UI.Scene2d.PauseScreenComponents.AbilityTable
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.mygdx.game.Ability.AbilityName
import com.mygdx.game.Managers.AbilityManager
import com.mygdx.game.UI.Scene2d.bigLabel
import com.mygdx.game.player


class IconTable(val num: Int): Table() {
    var abilityCell: Cell<Label>
    var chosenAbility: AbilityName? = null
    val emptyLabel = Label("Empty Ability", bigLabel)
    init {
        val numText = Label(num.toString(), bigLabel)
        numText.setFontScale(0.5f)
        this.add(numText).expand().top()
        this.row()

        emptyLabel.setFontScale(0.5f)
        abilityCell = this.add(emptyLabel).expand().top()

        //Init config
        val ability = player.activeAbilities.getOrDefault(num,null)
        if(ability != null){
            addAbilityToUi(ability.abilityName)
        }
    }

    fun addAbility(abilityName: AbilityName){
        addAbilityToUi(abilityName)

        AbilityManager.addToActiveAbilities(num, abilityName)
    }
    fun addAbilityToUi(abilityName: AbilityName){
        abilityCell.clearActor()
        val abilityText = Label(abilityName.toString(), bigLabel)
        abilityText.setFontScale(0.5f)
        abilityCell.setActor(abilityText)
        chosenAbility = abilityName
    }
    fun clearAbility(){
        abilityCell.clearActor()
        chosenAbility = null
        abilityCell.setActor(emptyLabel)

       AbilityManager.removeFromActiveAbilities(num)
    }
}
/*
val cellToChange = pauseScreen.rootTable.getCell(pauseScreen.activeTable)
                cellToChange.clearActor()
                cellToChange.setActor(newTable)
                pauseScreen.activeTable = newTable
 */