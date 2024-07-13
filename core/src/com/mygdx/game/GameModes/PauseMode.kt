
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.currentGameMode


private var stage: Stage = Stage(ScreenViewport())

class PauseMode(val prevMode: GameMode): GameMode{
    override val spriteBatch = SpriteBatch()
    override val inputProcessor = stage
    val pauseScreen = PauseScreen(prevMode, stage)

    init {
        pauseScreen.create()
    }

    override fun FrameAction() {
        pauseScreen.render()
    }
}