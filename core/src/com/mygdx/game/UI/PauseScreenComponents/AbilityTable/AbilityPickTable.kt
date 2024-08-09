package com.mygdx.game.UI.PauseScreenComponents.AbilityTable

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.mygdx.game.Ability.AbilityType
import com.mygdx.game.Ability.getAbilitiesFromType
import com.mygdx.game.Ability.getIconFromType
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.UI.smallLabel

class AbilityPickTable(val iconTableList: List<IconTable>): Table() {
    init {
        val allTypes = AbilityType.values()

        allTypes.forEach {
            val name = it.name
            val label = Label(name, smallLabel)

            this.add(label)
            val abilities = getAbilitiesFromType(it)
            val iconTexture = getIconFromType(it)
            for(ability in abilities){
                val imageIcon = iconTexture
                val textureRegionDrawable = TextureRegionDrawable(imageIcon)
                textureRegionDrawable.setMinSize(64f,64f)
                this.add(AbilityButton(textureRegionDrawable, ability, iconTableList))
            }
            this.row()
        }

    }
}