package com.mygdx.game

import FontManager
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.mygdx.game.Ability.AbilityName
import com.mygdx.game.Ability.convertAbilityToName
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.getAnimeliaData
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.MainMode
import com.mygdx.game.GameModes.UIMode
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.Inventory.Inventory
import com.mygdx.game.Managers.*
import com.mygdx.game.Saving.PlayerSaveState
import com.mygdx.game.Signal.Signal
import com.mygdx.game.Signal.initSignalListeners
import com.mygdx.game.UI.Scene2d.Screens.StartScreen
import kotlinx.serialization.json.Json
import signalConvert

lateinit var player: Player
lateinit var currentGameMode: GameMode
lateinit var mainMode: MainMode
lateinit var generalSaveState: PlayerSaveState

var mainCamera: OrthographicCamera = OrthographicCamera()
val zoomX = 4
val zoomY = 4
val defaultLineWidth = 2f
val startPos = Vector2(200f, -270f)
class MainGame : ApplicationAdapter() {

    lateinit var inputProcessor: InGameInputProcessor
    lateinit var shapeRenderer: ShapeRenderer
    lateinit var startingStage: Stage
    override fun create() {
        initMappings()
        initAreas()
        AreaManager.setActiveArea("World1")
        FontManager.initFonts()

        inputProcessor = InGameInputProcessor()
        Gdx.input.inputProcessor = inputProcessor
        mainCamera = OrthographicCamera()
        mainCamera.setToOrtho(false, Gdx.graphics.width.toFloat() / zoomX, Gdx.graphics.height.toFloat() / zoomY)
        player = Player(GameObjectData(x = 120, y = -200), Vector2(32f, 32f))
        mainMode = MainMode(inputProcessor)
        //AnivolutionMode(mainMode,ANIMELIA_ENTITY.FIRE_HIPPO)
        shapeRenderer = ShapeRenderer()
        DialogueManager.initSpeakableObjects()
       // getArticyDraftEntries()
        if (!FileHandler.SaveFileEmpty()) {
            val savedState: String = FileHandler.readPlayerFile()[0]
            val savedGeneralSaveState: PlayerSaveState = Json.decodeFromString(savedState)
            generalSaveState = PlayerSaveState(savedGeneralSaveState.inventory, savedGeneralSaveState.stats, savedGeneralSaveState.currentAnimelia)
            player.animeliaInfo = getAnimeliaData(generalSaveState.currentAnimelia)

        } else {
            generalSaveState = PlayerSaveState(Inventory(), Stats(), ANIMELIA_ENTITY.FireArmadillo)
            AreaManager.setActiveArea("World1")

            if(generalSaveState.inventory.ownedAbilities.size == 0){
                generalSaveState.inventory.ownedAbilities.add(AbilityName.TailSwipe)
            }
            val firstAbility = generalSaveState.inventory.ownedAbilities[0]
            player.activeAbilities[1] = convertAbilityToName(firstAbility.name).keyAbility
        }
        initSignalListeners()
        val originalFile = FileHandler.readSignalFile()
        val savedSignals: List<Signal> = originalFile.map(::signalConvert)
        savedSignals.forEach {
            SignalManager.pastSignals.add(it)
        }
        changeArea(startPos, "World1")
        mainMode.abilityRowUi.updateToolTips()
        currentGameMode = UIMode(StartScreen(mainMode))
    }

    override fun render() {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        currentGameMode.spriteBatch.projectionMatrix = mainCamera.combined
        currentGameMode.render()
        AnimationManager.addAnimationsToRender()
        //drawrects()
        AbilityManager.processAbilities()
        currentGameMode.FrameAction()
        EventManager.executeEvents()
        SignalManager.executeSignals()
        drawHealthBars()
        mainCamera.position.set(player.sprite.x, player.sprite.y, 0f)
        mainCamera.update()
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

    fun drawHealthBars(){
        for (fightableObject in AreaManager.getActiveArea()!!.gameObjects.filterIsInstance<FightableObject>()){
            fightableObject.healthStrategy.showHealth(fightableObject.sprite, fightableObject.currentHealth, fightableObject.maxHealth)
        }
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        //scene 2d weird bug otherwise
        (currentGameMode as? UIMode)?.stage?.viewport?.update(width, height, true)
    }
}