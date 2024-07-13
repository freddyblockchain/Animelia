
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.changeMode

lateinit var rootTable: Table

class PauseScreen(val prevMode: GameMode, val stage: Stage){

    private var activeButton: Button? = null
    private val buttons = mutableListOf<Button>()
    private var activeButtonIndex: Int = 0

    private val shapeRenderer: ShapeRenderer = ShapeRenderer()

    fun create() {
        rootTable = Table()
        Gdx.input.inputProcessor = stage
        rootTable.setFillParent(true)

        val juniorTable = Table()

        stage.addActor(rootTable)
        rootTable.add(juniorTable)


        //table.debug = true // This is optional, but enables debug lines for tables.

        val skin = Skin(Gdx.files.internal("assets/ui/uiskin.json"))
        val button = Button(skin)
        val button2 = Button(skin)
        val button3 = Button(skin)

        stage.addListener(object : InputListener() {
            override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                when (keycode) {
                    Input.Keys.ESCAPE -> {
                        changeMode(prevMode)
                        return true
                    }
                    Input.Keys.UP -> {
                        moveUp()
                        return true
                    }
                    Input.Keys.DOWN -> {
                        moveDown()
                        return true
                    }
                    Input.Keys.ENTER -> {
                        pressEnter()
                        return true
                    }
                    else -> return false
                }
                return false
            }
        })

        juniorTable.add(button).center().width(100f).height(100f)
        juniorTable.row()
        juniorTable.add(button2).center().width(100f).height(100f)
        juniorTable.row()
        juniorTable.add(button3).center().width(100f).height(100f)

        buttons.addAll(listOf(button, button2, button3))

        setActiveButton(buttons[0])


        // Add widgets to the table here.
    }

    fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    fun render() {
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

    fun dispose() {
        stage.dispose()
    }

    private fun setActiveButton(button: Button) {
        activeButton = button
    }

    private fun moveUp() {
        activeButtonIndex = (activeButtonIndex - 1 + buttons.size) % buttons.size
        setActiveButton(buttons[activeButtonIndex])
    }

    private fun moveDown() {
        activeButtonIndex = (activeButtonIndex + 1) % buttons.size
        setActiveButton(buttons[activeButtonIndex])
    }

    private fun pressEnter() {
        activeButton?.let { simulateClick(it) }
    }

    private fun simulateClick(button: Button) {
        val event = InputEvent()
        event.type = InputEvent.Type.touchDown
        button.fire(event)
        event.type = InputEvent.Type.touchUp
        button.fire(event)
    }
}