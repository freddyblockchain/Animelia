package com.mygdx.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.GameObjects.MoveableEntities.Characters.PlayerData
import com.mygdx.game.GameObjects.MoveableObjects.Butler
import com.mygdx.game.GameObjects.MoveableObjects.ButlerData
import com.mygdx.game.JsonParser.Companion.getArticyDraftEntries
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Utils.RenderGraph

lateinit var player: Player
lateinit var butler: Butler
var camera: OrthographicCamera = OrthographicCamera()
class MainGame : ApplicationAdapter() {

    var worldBatch: SpriteBatch? = null
    var img: Texture? = null
    lateinit var inputProcessor: MyInputProcessor
    lateinit var shapeRenderer: ShapeRenderer
    override fun create() {
        initMappings()
        initAreas()
        worldBatch = SpriteBatch()
        img = Texture("badlogic.jpg")
        inputProcessor = MyInputProcessor()
        Gdx.input.setInputProcessor(inputProcessor);
        camera = OrthographicCamera()
        camera.setToOrtho(false, Gdx.graphics.width.toFloat() / 3, Gdx.graphics.height.toFloat() / 3)
        player = Player(PlayerData("", 160, 128,0,0), Vector2(32f, 32f))
        butler = Butler(ButlerData("",0,0,0,0))
        AreaManager.getActiveArea()!!.gameObjects.add(player)
        shapeRenderer = ShapeRenderer()
        initObjects()
        getArticyDraftEntries()
    }

    override fun render() {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        worldBatch?.projectionMatrix = camera.combined
        for(gameObject in AreaManager.getActiveArea()!!.gameObjects){
            gameObject.frameTask()
        }
        RenderGraph.render(worldBatch!!)
        inputProcessor.handleInput()
        drawrects()
        Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
        camera.position.set(player.sprite.x, player.sprite.y, 0f)
        camera.update()
    }

    override fun dispose() {
        worldBatch!!.dispose()
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