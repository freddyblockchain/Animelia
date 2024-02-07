package com.mygdx.game.Utils

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.Rendering.Renderable

class RenderGraph {
    companion object{
        private val RenderList = mutableListOf<Renderable>()
        fun addToSceneGraph(renderable: Renderable){
            RenderList.add(renderable)
        }
        fun render(batch: SpriteBatch){
            batch.begin()
            RenderList.sortBy {it.layer}
            RenderList.forEach{it.render(batch)}
            RenderList.clear()
            batch.end()
        }
    }
}