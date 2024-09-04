package com.mygdx.game.UI.MainGameUi

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Ability.getIconFromType
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Rendering.Renderable
import com.mygdx.game.player

class AbilityTooltipRow():Renderable {
    override val layer = Layer.FOREGROUND
    val abilityToolTips = mutableListOf<AbilityTooltip>()

    fun updateToolTips(){
        abilityToolTips.clear()
        val numberOfAbilities = (player.stats.intelligence / 5)
        for (i in 1 .. numberOfAbilities){
            val ability = player.activeAbilities.getOrDefault(i, null)
            var texture = DefaultTextureHandler.getTexture("EmptyDoor.png")
            if(ability != null){
                texture = getIconFromType(ability.ELEMENTALTYPES)
            }
            abilityToolTips.add(AbilityTooltip(i,texture))
        }
    }

    override fun render(batch: SpriteBatch) {
        var screenSize = Vector2(Gdx.graphics.width.toFloat(),Gdx.graphics.height.toFloat())
        val xOffset = 96f
        val startingPos = Vector2(64f, screenSize.y - (screenSize.y / 10f))
        var counter = 0
        for(toolTip in abilityToolTips){
            toolTip.sprite.setPosition(startingPos.x + (xOffset * counter), startingPos.y)
            counter += 1
            toolTip.render(batch)
        }
    }

}