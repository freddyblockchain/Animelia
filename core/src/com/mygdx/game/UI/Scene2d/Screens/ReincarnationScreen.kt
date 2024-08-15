package com.mygdx.game.UI.Scene2d.Screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.mygdx.game.Animelia.Egg
import com.mygdx.game.Animelia.getEggAnimelia

import com.mygdx.game.Animelia.getEggTexture
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.GameModes.AnivolutionMode
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.Managers.MusicManager
import com.mygdx.game.Managers.PlayerStatus
import com.mygdx.game.Managers.Stats
import com.mygdx.game.player

class ReincarnationScreen(override var prevMode: GameMode?, val eggs: List<Egg>) : UIScreen() {
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

        rootTable.add(reincarnationText).center().right()
        rootTable.row()

        for (egg in eggs) {
            val textureString = getEggTexture(egg)
            val texture = (DefaultTextureHandler.getTexture(textureString))
            val buttonImage = TextureRegionDrawable(texture)
            buttonImage.setMinSize(200f, 200f)
            val eggButton = ImageButton(buttonImage)
            rootTable.add(eggButton)

            eggButton.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    // Define what should happen when the button is clicked
                    confirmSound.play()
                    player.animeliaInfo.gameTexture = texture
                    player.stats = Stats(PlayerStatus.baseOffence, PlayerStatus.baseDefence, PlayerStatus.baseSpeed, PlayerStatus.baseIntelligence)
                    changeMode(AnivolutionMode(prevMode!!, getEggAnimelia(egg), isReincarnating = true))
                }
            })
            buttons.add(eggButton)
        }

    }
    override fun render() {
        stage.act(Gdx.graphics.deltaTime)
        stage.isDebugAll = true
        stage.draw()
        buttons[activeButtonIndex].let {
            val stageCoords = it.localToParentCoordinates(Vector2(it.parent.x, it.parent.y));

            shapeRenderer.projectionMatrix = stage.camera.combined
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
            shapeRenderer.setColor(0f, 1f, 0f, 1f)
            shapeRenderer.circle(stageCoords.x + it.width / 2, stageCoords.y + it.height / 2, it.width / 2 + 10)
            shapeRenderer.end()
        }

    }
}