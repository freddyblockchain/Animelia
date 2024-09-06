package com.mygdx.game.UI.Scene2d.PauseScreenComponents.AbilityTable

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.mygdx.game.Ability.ELEMENTAL_TYPE
import com.mygdx.game.Ability.getAbilitiesFromType
import com.mygdx.game.Ability.getIconFromType
import com.mygdx.game.UI.Scene2d.smallLabel
import com.mygdx.game.UI.Scene2d.smallToMediumLabel

class AbilityPickTable(val iconTableList: List<IconTable>, abilityDescription: Label): Table() {
    init {
        val allTypes = ELEMENTAL_TYPE.values()

        allTypes.forEach {
            val name = it.name
            val label = Label(name, smallToMediumLabel)

            this.add(label)
            val abilities = getAbilitiesFromType(it)
            val iconTexture = getIconFromType(it)
            for(ability in abilities){
                val imageIcon = iconTexture
                val textureRegionDrawable = TextureRegionDrawable(imageIcon)
                textureRegionDrawable.setMinSize(64f,64f)
                this.add(AbilityButton(textureRegionDrawable, ability, iconTableList, abilityDescription))
            }
            this.row()
        }

    }
}