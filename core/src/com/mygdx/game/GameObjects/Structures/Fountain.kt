package com.mygdx.game.GameObjects.Structures

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Animelia.Egg
import com.mygdx.game.Collition.InputCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameModes.UIMode
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.UI.Screens.ReincarnationScreen
import com.mygdx.game.currentGameMode
import com.mygdx.game.mainMode

class Fountain(gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())) {
    override val texture = DefaultTextureHandler.getTexture("TrainingStatue.png")
    override val layer = Layer.ONGROUND
    override val collision = FountainCollision()
    override fun render(batch: SpriteBatch) {
    }
}

class FountainCollision(): InputCollision(){
    override val keyCode = Input.Keys.SPACE

    override fun collisionHappened(collidedObject: GameObject) {
        currentGameMode = UIMode(ReincarnationScreen(mainMode, listOf(Egg.FIRE, Egg.ICE)))
    }

}