package com.mygdx.game

import FontManager
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.FPSLogger
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.MainMode
import com.mygdx.game.GameModes.UIMode
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.Managers.*
import com.mygdx.game.Saving.PlayerSaveState
import com.mygdx.game.UI.Scene2d.Screens.StartScreen

lateinit var player: Player
lateinit var currentGameMode: GameMode
lateinit var mainMode: MainMode
lateinit var generalSaveState: PlayerSaveState

var mainCamera: OrthographicCamera = OrthographicCamera()
val zoomX = 4
val zoomY = 4
val defaultLineWidth = 2f
val startPos = Vector2(200f, -270f)
val fpsLogger = FPSLogger()
var amountOfLevels = 0

var baseWidth = 1920f // Base width

var baseHeight = 1080f // Base height

// has to hard code later
class MainGame : ApplicationAdapter() {

    lateinit var inputProcessor: InGameInputProcessor
    lateinit var shapeRenderer: ShapeRenderer
    lateinit var startingStage: Stage
    override fun create() {
        val fileHandle = Gdx.files.local("assets/levels")
        amountOfLevels = fileHandle.list().size
        FileHandler.initSaveFiles()
        initMappings()
        initAreas()
        AreaManager.setActiveArea("World1")
        FontManager.initFonts()

        inputProcessor = InGameInputProcessor()
        Gdx.input.inputProcessor = inputProcessor
        mainCamera = OrthographicCamera()
        mainCamera.setToOrtho(false, (baseWidth / zoomX) * (Gdx.graphics.getWidth() / baseWidth),
            (baseHeight / zoomY) * (Gdx.graphics.getHeight() / baseHeight))
        player = Player(GameObjectData(x = 120, y = -200), Vector2(32f, 32f))
        mainMode = MainMode(inputProcessor)
        //AnivolutionMode(mainMode,ANIMELIA_ENTITY.FIRE_HIPPO)
        shapeRenderer = ShapeRenderer()
        DialogueManager.initSpeakableObjects()
        currentGameMode = UIMode(StartScreen(mainMode), playConfirmationSound = false)

       // player.activeAbilities[1] = Fly(player)
    }

    override fun render() {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        currentGameMode.spriteBatch.projectionMatrix = mainCamera.combined
        currentGameMode.render()
        AnimationManager.addAnimationsToRender()
        //drawrects()
        AbilityManager.processAbilities()
        currentGameMode.FrameAction()
        SignalManager.executeSignals()
        currentGameMode.cameraAction()
        mainCamera.update()
        fpsLogger.log()
    }

    override fun dispose() {
        currentGameMode.spriteBatch.dispose()
    }

    fun drawrects() {
        AreaManager.getActiveArea()!!.gameObjects.forEach { x -> drawPolygonShape(x.polygon, shapeRenderer) }
    }

    fun drawPolygonShape(polygon: Polygon, shapeRenderer: ShapeRenderer){
        shapeRenderer.projectionMatrix = mainCamera.combined
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.polygon(polygon.transformedVertices)
        shapeRenderer.end()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        //scene 2d weird bug otherwise
        (currentGameMode as? UIMode)?.stage?.viewport?.update(width, height, true)
    }
}