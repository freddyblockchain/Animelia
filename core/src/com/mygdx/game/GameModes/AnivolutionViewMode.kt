
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.mygdx.game.GameModes.GameMode


class AnivolutionViewMode(val prevMode: GameMode): GameMode{
    val stage: Stage = Stage(ScreenViewport())
    override val spriteBatch = SpriteBatch()
    override val inputProcessor = stage
    val pauseScreen = AnivolutionViewScreen(prevMode, stage)

    init {
        pauseScreen.create()
    }

    override fun FrameAction() {
        pauseScreen.render()
    }
}