package com.mygdx.game.GameObjects.Structures

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collition.InputCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameModes.UIMode
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.UI.Scene2d.Screens.TrainingScreen
import com.mygdx.game.currentGameMode
import com.mygdx.game.mainMode

class Library(gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())) {
    override val texture = DefaultTextureHandler.getTexture("books.png")
    override val layer = Layer.ONGROUND
    override val collision = LibraryCollision()
}

class LibraryCollision(): InputCollision(){
    override val keyCode = Input.Keys.SPACE

    override fun collisionHappened(collidedObject: GameObject) {
        currentGameMode = UIMode(TrainingScreen(mainMode))
    }

}