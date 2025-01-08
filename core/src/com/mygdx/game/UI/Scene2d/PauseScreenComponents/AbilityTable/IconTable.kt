package com.mygdx.game.UI.Scene2d.PauseScreenComponents.AbilityTable
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.mygdx.game.Ability.*
import com.mygdx.game.Managers.AbilityManager
import com.mygdx.game.UI.Scene2d.bigLabel
import com.mygdx.game.UI.Scene2d.smallFontLabel
import com.mygdx.game.UI.Scene2d.smallToMediumLabel
import com.mygdx.game.player


class IconTable(val num: Int, abilityDescription: Label): Table() {
    var abilityCell: Cell<Label>
    var chosenAbility: AbilityName? = null
    val emptyLabel = Label("Empty Ability", bigLabel)

    fun getImage(ability: KeyAbility): Image{
        val iconTexture = getIconFromType(ability.ELEMENTALTYPES)
        val imageIcon = iconTexture
        val textureRegionDrawable = TextureRegionDrawable(imageIcon)
        textureRegionDrawable.setMinSize(64f,64f)
        return Image(textureRegionDrawable)
    }
    var  numTable: Table = Table()

    init {
        val numText = Label(num.toString(), bigLabel)
        numText.setFontScale(0.5f)
        numTable.add(numText).expand().center().padRight(20f)
        this.add(numTable).expand().top()
        this.row()

        emptyLabel.setFontScale(0.35f)
        abilityCell = this.add(emptyLabel).expand().top()

        //Init config
        val ability = player.activeAbilities.getOrDefault(num,null)
        if(ability != null){
            addAbilityToUi(ability.abilityName)
        }

        this.addListener(object : ClickListener() {

            override fun enter(event: InputEvent?, x: Float, y: Float, pointer: Int, fromActor: Actor?) {
                val ability = player.activeAbilities.getOrDefault(num,null)
                if(ability != null){
                    abilityDescription.setText(getDescriptionFromName(ability.abilityName))
                }
                super.enter(event, x, y, pointer, fromActor)
            }

            override fun exit(event: InputEvent?, x: Float, y: Float, pointer: Int, toActor: Actor?) {
                abilityDescription.setText("")
                super.exit(event, x, y, pointer, toActor)
            }
        })
    }

    fun addAbility(abilityName: AbilityName){
        addAbilityToUi(abilityName)
        AbilityManager.addToActiveAbilities(num, abilityName)
    }
    fun addAbilityToUi(abilityName: AbilityName){
        abilityCell.clearActor()
        val abilityText = Label(abilityName.toString(), bigLabel)
        abilityText.setFontScale(0.35f)
        abilityCell.setActor(abilityText)
        chosenAbility = abilityName

        val ability = convertNameToAbility(abilityName.name).keyAbility
        val image = getImage(ability)
        numTable.add(image).right()

    }
    fun clearAbility(){
        abilityCell.clearActor()
        chosenAbility = null
        abilityCell.setActor(emptyLabel)
        numTable.removeActorAt(1, true)

        AbilityManager.removeFromActiveAbilities(num)
    }
}
/*
val cellToChange = pauseScreen.rootTable.getCell(pauseScreen.activeTable)
                cellToChange.clearActor()
                cellToChange.setActor(newTable)
                pauseScreen.activeTable = newTable
 */