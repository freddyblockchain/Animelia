package com.mygdx.game.UI.Scene2d.PauseScreenComponents.AbilityTable

import FontManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.mygdx.game.Ability.AbilityName
import com.mygdx.game.player

enum class AbilityButtonOwnership {NotOwned, Owned}
enum class AbilityButtonLearnable {Learnable, NotLearnable}

class AbilityButton(drawable: TextureRegionDrawable, val abilityName: AbilityName, val iconTableList: List<IconTable>): ImageButton(drawable) {
    val abilityButtonOwnership = if(player.ownedAbilities.any { it.abilityName == abilityName  }) AbilityButtonOwnership.Owned else AbilityButtonOwnership.NotOwned
    val abilityButtonLearnable = if(player.animeliaInfo.availableAbilities.contains(abilityName)) AbilityButtonLearnable.Learnable else AbilityButtonLearnable.NotLearnable

    init {
        this.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                if(iconTableList.any { it.chosenAbility == abilityName}){
                    val correspondingIconTable = iconTableList.first { it.chosenAbility == abilityName }
                    correspondingIconTable.clearAbility()
                }else{
                    if(abilityButtonOwnership == AbilityButtonOwnership.Owned && abilityButtonLearnable == AbilityButtonLearnable.Learnable){
                        val firstNotSelected = iconTableList.firstOrNull { it.chosenAbility == null }
                        firstNotSelected?.addAbility(abilityName)
                    }
                }
            }
        })
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        if(abilityButtonOwnership == AbilityButtonOwnership.Owned){
            val color: Color = this.color
            val newAlpha = if(this.abilityButtonLearnable == AbilityButtonLearnable.Learnable) 1f else 0.5f
            this.setColor(color.r,color.g, color.b, newAlpha)
            super.draw(batch, parentAlpha)
            for (iconTable in iconTableList){
                if(iconTable.chosenAbility == abilityName){
                    FontManager.MediumFont.draw(batch, iconTable.num.toString(), x + width / 3.5f, y + height)
                }
            }
        }
    }
}