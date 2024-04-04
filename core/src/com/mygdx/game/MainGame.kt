package com.mygdx.game

import FontManager
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.GameModes.ChapterMode
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.MainMode
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.GameObjects.MoveableEntities.Characters.PlayerData
import com.mygdx.game.GameObjects.MoveableObjects.Butler
import com.mygdx.game.GameObjects.MoveableObjects.ButlerData
import com.mygdx.game.JsonParser.Companion.getArticyDraftEntries
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.DialogueManager
import com.mygdx.game.Saving.GeneralSaveState
import com.mygdx.game.Saving.updateAndSavePlayer
import com.mygdx.game.Utils.RenderGraph
import kotlinx.serialization.json.Json

lateinit var player: Player
lateinit var butler: Butler
lateinit var currentGameMode: GameMode
lateinit var mainMode: MainMode
lateinit var generalSaveState: GeneralSaveState

var camera: OrthographicCamera = OrthographicCamera()
class MainGame : ApplicationAdapter() {

    var img: Texture? = null
    lateinit var inputProcessor: MyInputProcessor
    lateinit var shapeRenderer: ShapeRenderer
    override fun create() {
        initMappings()
        initAreas()
        FontManager.initFonts()

        img = Texture("badlogic.jpg")
        inputProcessor = MyInputProcessor()
        Gdx.input.setInputProcessor(inputProcessor);
        camera = OrthographicCamera()
        camera.setToOrtho(false, Gdx.graphics.width.toFloat() / 3, Gdx.graphics.height.toFloat() / 3)
        player = Player(PlayerData("", 160, 128,0,0), Vector2(32f, 32f))
        butler = Butler(ButlerData("",0,0,0,0))
        mainMode = MainMode(inputProcessor)
        currentGameMode = mainMode
        shapeRenderer = ShapeRenderer()
        initObjects()
        DialogueManager.initSpeakableObjects()
        getArticyDraftEntries()

        if (!FileHandler.SaveFileEmpty()) {
            val savedState: String = FileHandler.readFromFile()[0]
            val savedGeneralSaveState: GeneralSaveState = Json.decodeFromString(savedState)
            AreaManager.setActiveArea(savedGeneralSaveState.areaIdentifier)
            generalSaveState = GeneralSaveState(
                savedGeneralSaveState.playerXPos, savedGeneralSaveState.playerYPos,
                savedGeneralSaveState.areaIdentifier, savedGeneralSaveState.butlerActive, player.entityId
            )
            player.setPosition(Vector2(generalSaveState.playerXPos, generalSaveState.playerYPos))
            if(generalSaveState.butlerActive){
                butler.setActive(Vector2(generalSaveState.playerXPos, generalSaveState.playerYPos))
            }

        } else {
            currentGameMode = ChapterMode()
            generalSaveState = GeneralSaveState(160f, 128f, AreaManager.areas[0].areaIdentifier, false, player.entityId)
            AreaManager.setActiveArea(AreaManager.areas[0].areaIdentifier)
            updateAndSavePlayer()
        }
        AreaManager.getActiveArea()!!.gameObjects.add(player)

    }

    override fun render() {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        currentGameMode.spriteBatch.projectionMatrix = camera.combined
        RenderGraph.render(currentGameMode.spriteBatch)
        AnimationManager.addAnimationsToRender()
        currentGameMode.FrameAction()
        drawrects()
        camera.position.set(player.sprite.x, player.sprite.y, 0f)
        camera.update()
    }

    override fun dispose() {
        currentGameMode.spriteBatch.dispose()
        img!!.dispose()
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
}