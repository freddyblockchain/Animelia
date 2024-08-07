package com.mygdx.game.UI

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.mygdx.game.Animelia.Egg
import com.mygdx.game.Animelia.getEggAnimelia

import com.mygdx.game.Animelia.getEggTexture
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.GameModes.AnivolutionMode
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.UIMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.Managers.PlayerStatus
import com.mygdx.game.Managers.Stats
import com.mygdx.game.currentGameMode
import com.mygdx.game.mainMode
import com.mygdx.game.player

class ReincarnationScreen(val prevMode: GameMode, val eggs: List<Egg>) : UIScreen() {
    override var activeButton: Button? = null
    lateinit var rootTable: Table

    override fun create() {
        rootTable = Table()

        Gdx.input.inputProcessor = stage
        rootTable.setFillParent(true)

        stage.addActor(rootTable)

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
                    player.animeliaInfo.gameTexture = texture
                    player.stats = Stats(PlayerStatus.baseOffence, PlayerStatus.baseDefence, PlayerStatus.baseSpeed, PlayerStatus.baseIntelligence)
                    changeMode(AnivolutionMode(prevMode, getEggAnimelia(egg), isReincarnating = true))
                }
            })
            buttons.add(eggButton)
        }
        activeButton = buttons[0]
        stage.addListener(
            object : InputListener() {
                override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                    when (keycode) {
                        Input.Keys.RIGHT -> {
                            moveUp()
                            return true
                        }

                        Input.Keys.LEFT -> {
                            moveDown()
                            return true
                        }

                        Input.Keys.ENTER -> {
                            pressEnter()
                            return true
                        }
                        else -> return false
                    }
                }
            })

    }
    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(Gdx.graphics.deltaTime)
        stage.isDebugAll = true
        stage.draw()
        activeButton?.let {
            val stageCoords = it.localToParentCoordinates(Vector2(it.parent.x, it.parent.y));

            shapeRenderer.projectionMatrix = stage.camera.combined
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
            shapeRenderer.setColor(0f, 1f, 0f, 1f)
            shapeRenderer.circle(stageCoords.x + it.width / 2, stageCoords.y + it.height / 2, it.width / 2 + 10)
            shapeRenderer.end()
        }

    }
}