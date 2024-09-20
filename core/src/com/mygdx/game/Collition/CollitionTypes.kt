package com.mygdx.game.Collition

import FontManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Rendering.Renderable
import com.mygdx.game.Utils.Center
import com.mygdx.game.Utils.RenderGraph
import com.mygdx.game.mainCamera
import com.mygdx.game.zoomX
import com.mygdx.game.zoomY

abstract class MoveCollision: Collision {
    override val collitionType = CollisionType.MOVE

    abstract var canMoveAfterCollision: Boolean
}
abstract class InputCollision: Collision {
    override val collitionType = CollisionType.INPUT
    open val keyCode = Input.Keys.ENTER
    abstract val insideText: String

    open fun renderKeycodeToPress(){
        val keyInputRenderer = KeyInputRenderer(insideText)
        RenderGraph.addToSceneGraph(keyInputRenderer)
    }
}
class KeyInputRenderer(val text: String): Renderable{
    override val layer = Layer.FOREGROUND
    val font = FontManager.TextFont

    override fun render(batch: SpriteBatch) {
        val screenCoordinates = Vector3((Center.x) - 50f, (Center.y - 200), 0f)

        // Unproject the screen coordinates to get the world coordinates
        val worldCoordinates = mainCamera.unproject(screenCoordinates)
        font.draw(batch,text, worldCoordinates.x, worldCoordinates.y)
    }

}