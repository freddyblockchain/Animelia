package com.mygdx.game

import GameObjectFactory.initMappings
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Utils.RenderGraph


var cameraDirection = Vector2(0f,0f)

class MainGame : ApplicationAdapter() {

    var worldBatch: SpriteBatch? = null
    var img: Texture? = null
    var camera: OrthographicCamera = OrthographicCamera()
    lateinit var shapeRenderer: ShapeRenderer
    override fun create() {
        initMappings()
        worldBatch = SpriteBatch()
        img = Texture("badlogic.jpg")
        Gdx.input.setInputProcessor(MyInputProcessor());
        camera = OrthographicCamera()
        camera.setToOrtho(false, Gdx.graphics.width.toFloat() / 3, Gdx.graphics.height.toFloat() / 3)
        camera.position.set(128f,128f,0f)
        val newArea = createArea("levels/Level_0")
        AreaManager.areas.add(newArea)
        AreaManager.activeArea = newArea
        shapeRenderer = ShapeRenderer()
    }

    override fun render() {

        camera.position.set(camera.position.x + cameraDirection.x, camera.position.y + cameraDirection.y, 0f)
        camera.update()

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        worldBatch?.projectionMatrix = camera.combined
        worldBatch!!.begin()
        for(gameObject in AreaManager.activeArea!!.gameObjects){
            gameObject.frameTask()
        }
        RenderGraph.render(worldBatch!!)
        worldBatch!!.end()
        drawrects()
        Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
    }

    override fun dispose() {
        worldBatch!!.dispose()
        img!!.dispose()
    }

    fun drawrects() {
        AreaManager.activeArea!!.gameObjects.forEach { x -> drawPolygonShape(x.polygon, shapeRenderer) }
    }

    fun drawPolygonShape(polygon: Polygon, shapeRenderer: ShapeRenderer){
        shapeRenderer.projectionMatrix = camera.combined
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.polygon(polygon.transformedVertices)
        shapeRenderer.end()
    }
}