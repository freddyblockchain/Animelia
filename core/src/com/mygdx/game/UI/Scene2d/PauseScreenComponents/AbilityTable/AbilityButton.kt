package com.mygdx.game.UI.Scene2d.PauseScreenComponents.AbilityTable

import FontManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.mygdx.game.Ability.AbilityName
import com.mygdx.game.Ability.getDescriptionFromName
import com.mygdx.game.generalSaveState
import com.mygdx.game.player

enum class AbilityButtonOwnership {NotOwned, Owned}
enum class AbilityButtonLearnable {Learnable, NotLearnable}

class AbilityButton(drawable: TextureRegionDrawable, val abilityName: AbilityName, val iconTableList: List<IconTable>, val abilityDescription: Label): ImageButton(drawable) {
    val abilityButtonOwnership = if(generalSaveState.inventory.ownedAbilities.any { it == abilityName  }) AbilityButtonOwnership.Owned else AbilityButtonOwnership.NotOwned
    val abilityButtonLearnable = if(player.animeliaInfo.availableAbilities.contains(abilityName)) AbilityButtonLearnable.Learnable else AbilityButtonLearnable.NotLearnable
    private val shapeRenderer: ShapeRenderer = ShapeRenderer()

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

            override fun enter(event: InputEvent?, x: Float, y: Float, pointer: Int, fromActor: Actor?) {
                if(abilityButtonOwnership == AbilityButtonOwnership.Owned){
                    abilityDescription.setText(getDescriptionFromName(abilityName))
                }
                super.enter(event, x, y, pointer, fromActor)
            }

            override fun exit(event: InputEvent?, x: Float, y: Float, pointer: Int, toActor: Actor?) {
                abilityDescription.setText("")
                super.exit(event, x, y, pointer, toActor)
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

        //Pretty bad but we are trying it out.

        batch?.end() // End the current batch before using ShapeRenderer

        val stageCoords = this.localToStageCoordinates(Vector2(0f, 0f))
        val color = Color.WHITE
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.setColor(color)
        shapeRenderer.rect(stageCoords.x, stageCoords.y, this.width, this.height)
        shapeRenderer.end()

        batch?.begin() // Restart the batch after using ShapeRenderer
    }
}