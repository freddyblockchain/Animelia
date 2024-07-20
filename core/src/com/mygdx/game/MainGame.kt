package com.mygdx.game

import FontManager
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.MainMode
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.Managers.*
import com.mygdx.game.Saving.GeneralSaveState
import com.mygdx.game.Saving.updateAndSavePlayer
import com.mygdx.game.Signal.Signal
import com.mygdx.game.Signal.initSignalListeners
import com.mygdx.game.Signal.signalConvert
import com.mygdx.game.Utils.RenderGraph
import kotlinx.serialization.json.Json

lateinit var player: Player
lateinit var currentGameMode: GameMode
lateinit var mainMode: MainMode
lateinit var generalSaveState: GeneralSaveState

var camera: OrthographicCamera = OrthographicCamera()
val zoomX = 3
val zoomY = 3
class MainGame : ApplicationAdapter() {

    lateinit var inputProcessor: InGameProcessor
    lateinit var shapeRenderer: ShapeRenderer
    override fun create() {
        initMappings()
        initAreas()
        FontManager.initFonts()

        inputProcessor = InGameProcessor()
        Gdx.input.inputProcessor = inputProcessor
        camera = OrthographicCamera()
        camera.setToOrtho(false, Gdx.graphics.width.toFloat() / zoomX, Gdx.graphics.height.toFloat() / zoomY)
        player = Player(GameObjectData(x = 120, y = -200), Vector2(32f, 32f))
        mainMode = MainMode(inputProcessor)
        currentGameMode = mainMode
        shapeRenderer = ShapeRenderer()
        initObjects()
        DialogueManager.initSpeakableObjects()
       // getArticyDraftEntries()
        if (!FileHandler.SaveFileEmpty()) {
            val savedState: String = FileHandler.readFromFile()[0]
            val savedGeneralSaveState: GeneralSaveState = Json.decodeFromString(savedState)
            AreaManager.setActiveArea(savedGeneralSaveState.areaIdentifier)
            generalSaveState = GeneralSaveState(
                savedGeneralSaveState.playerXPos, savedGeneralSaveState.playerYPos,
                savedGeneralSaveState.areaIdentifier, player.entityId
            )
            player.setPosition(Vector2(generalSaveState.playerXPos, generalSaveState.playerYPos))

        } else {
            generalSaveState = GeneralSaveState(160f, 128f, AreaManager.areas[0].areaIdentifier, player.entityId)
            AreaManager.setActiveArea("World1")
            updateAndSavePlayer()
        }
        initSignalListeners()
        val originalFile = FileHandler.readFromFile()
        val saves = originalFile.subList(1, originalFile.size)
        val savedSignals: List<Signal> = saves.map(::signalConvert)
        savedSignals.forEach { SignalManager.emitSignal(it, false)
            SignalManager.pastSignals.add(it)
        }
        AreaManager.getActiveArea()!!.gameObjects.add(player)

    }

    override fun render() {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        currentGameMode.spriteBatch.projectionMatrix = camera.combined
        RenderGraph.render(currentGameMode.spriteBatch)
        AnimationManager.addAnimationsToRender()
        AbilityManager.processAbilities()
        currentGameMode.FrameAction()
        SignalManager.executeSignals()
        drawHealthBars()
        drawrects()
        camera.position.set(player.sprite.x, player.sprite.y, 0f)
        camera.update()
    }

    override fun dispose() {
        currentGameMode.spriteBatch.dispose()
    }

    fun drawrects() {
        AreaManager.getActiveArea()!!.gameObjects.forEach { x -> drawPolygonShape(x.polygon, shapeRenderer) }
    }

    fun drawPolygonShape(polygon: Polygon, shapeRenderer: ShapeRenderer){
        shapeRenderer.projectionMatrix = camera.combined
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.polygon(polygon.transformedVertices)
        shapeRenderer.end()
    }

    fun drawHealthBars(){
        for (fightableObject in AreaManager.getActiveArea()!!.gameObjects.filterIsInstance<FightableObject>()){
            fightableObject.healthStrategy.showHealth(fightableObject.sprite, fightableObject.currentHealth, fightableObject.maxHealth)
        }
    }
}