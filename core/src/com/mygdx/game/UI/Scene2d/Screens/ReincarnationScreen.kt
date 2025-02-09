package com.mygdx.game.UI.Scene2d.Screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.mygdx.game.Animelia.getEggAnimelia

import com.mygdx.game.Animelia.getEggTexture
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.GameModes.AnivolutionMode
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.Managers.PlayerStatus
import com.mygdx.game.Managers.Stats
import com.mygdx.game.generalSaveState
import com.mygdx.game.player

class ReincarnationScreen(override var prevMode: GameMode?) : UIScreen() {
    override var activeButton: Actor? = null

    override var renderPrevGameMode = false

    override fun changeActive(activeIndex: Int) {
        activeButton = buttons[activeIndex]
        super.changeActive(activeIndex)
    }
    override fun create() {
        super.create()
        val labelStyle = Label.LabelStyle(FontManager.ChapterFont, Color.WHITE)
        val reincarnationText = Label("Reincarnation", labelStyle)

        rootTable.add(reincarnationText).center()
        rootTable.row()
        val eggTable = Table()
        rootTable.add(eggTable).expand().center()

        for (egg in generalSaveState.inventory.eggs) {
            val textureString = getEggTexture(egg)
            val texture = DefaultTextureHandler.getTexture(textureString)
            val buttonImage = TextureRegionDrawable(texture)
            buttonImage.setMinSize(200f, 200f)
            val eggButton = ImageButton(buttonImage)

            eggTable.add(eggButton).expand().left().pad(20f)

            eggButton.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    // Define what should happen when the button is clicked
                    val id = confirmSound.play()
                    confirmSound.setVolume(id,0.2f)
                    player.animeliaInfo.textureName = textureString
                    generalSaveState.stats = Stats(PlayerStatus.baseOffence, PlayerStatus.baseDefence, PlayerStatus.baseIntelligence)
                    generalSaveState.stats.tp = PlayerStatus.baseTp
                    generalSaveState.updateSaveState()
                    player.materialsPickedUp.clear()
                    changeMode(AnivolutionMode(prevMode!!, getEggAnimelia(egg), isReincarnating = true))
                    player.currentHealth = player.maxHealth
                }
            })



            buttons.add(eggButton)
        }

    }
    override fun render() {
        stage.act(Gdx.graphics.deltaTime)
        //stage.isDebugAll = true
        stage.draw()
        buttons[activeButtonIndex].let {
            val selectedButton = it

            // Convert local coordinates of the selected button to stage coordinates
            val buttonCoords = selectedButton.localToStageCoordinates(Vector2(selectedButton.width / 2, selectedButton.height / 2))

            // Prepare the ShapeRenderer for drawing
            shapeRenderer.projectionMatrix = stage.camera.combined
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
            shapeRenderer.color = Color.GREEN // Set the circle color

            // Draw a circle around the selected button
            val circleRadius = (selectedButton.width.coerceAtLeast(selectedButton.height) / 2) + 10f // Add padding
            shapeRenderer.circle(buttonCoords.x, buttonCoords.y, circleRadius)

            shapeRenderer.end()
        }

    }
}