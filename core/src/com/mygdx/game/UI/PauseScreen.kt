package com.mygdx.game.UI

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.mygdx.game.Animelia.Egg
import com.mygdx.game.Animelia.getEggAnimelia
import com.mygdx.game.Animelia.getEggTexture
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.GameModes.AnivolutionMode
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.Managers.PlayerStatus
import com.mygdx.game.Managers.Stats
import com.mygdx.game.player

class PauseScreen(val prevMode: GameMode) : UIScreen() {
    override var activeButton: Button? = null

    override fun create() {

        val labelStyle = Label.LabelStyle(FontManager.ChapterFont, Color.WHITE)
        val reincarnationText = Label("Status", labelStyle)

        rootTable.add(reincarnationText).center()
    }
    override fun render() {
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