package com.mygdx.game.UI

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.mygdx.game.camera
import com.mygdx.game.player
import com.mygdx.game.zoomX
import com.mygdx.game.zoomY

val healthShapeRenderer = ShapeRenderer()
interface HealthStrategy {
    fun showHealth(sprite:Sprite, health: Float, maxHealth: Float)
}

class PlayerHealthStrategy: HealthStrategy {
    override fun showHealth(sprite: Sprite, health: Float, maxHealth: Float) {
        drawHealthBar(Vector2(Gdx.graphics.width.toFloat() - 300f, Gdx.graphics.height.toFloat() - 100f), Vector2(150f,50f),health,maxHealth)
    }
}

class EnemyHealthStrategy: HealthStrategy{

    override fun showHealth(sprite:Sprite, health: Float, maxHealth: Float) {
        val pos = Vector3(sprite.x,sprite.y,0f)
        camera.project(pos)
        drawHealthBar(Vector2(pos.x,pos.y + sprite.height * zoomY),Vector2(sprite.width * zoomX,5f * zoomY),health,maxHealth)
    }
}
fun drawHealthBar(pos: Vector2, size: Vector2, health: Float, maxHealth: Float){

    healthShapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    healthShapeRenderer.color = Color.WHITE;
    healthShapeRenderer.rect(pos.x, pos.y, size.x, size.y);
    healthShapeRenderer.end();
    healthShapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
    healthShapeRenderer.color = Color.GREEN
    healthShapeRenderer.rect(pos.x,pos.y, health / maxHealth * size.x,size.y)
    healthShapeRenderer.end()
}