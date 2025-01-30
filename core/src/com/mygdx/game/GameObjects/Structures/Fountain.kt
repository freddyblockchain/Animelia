package com.mygdx.game.GameObjects.Structures

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Animelia.Egg
import com.mygdx.game.Collition.InputCollision
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameModes.UIMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.UI.Scene2d.Screens.DialogScreen
import com.mygdx.game.UI.Scene2d.Screens.ReincarnationScreen

class Fountain(gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())) {
    override val texture = DefaultTextureHandler.getTexture("toomstone.png")
    override val layer = Layer.ONGROUND
    override val collision = FountainCollision()
}

class FountainCollision(): InputCollision(){
    override val keyCode = Input.Keys.ENTER
    override val insideText = "REINCARNATE"

    override fun collisionHappened(collidedObject: GameObject) {
        if(generalSaveState.inventory.eggs.size > 0){
            val reincarnationMode = UIMode(ReincarnationScreen(mainMode))
            val dialogMode = UIMode(DialogScreen(currentGameMode, reincarnationMode,"Do you want to Reincarnate?"), playConfirmationSound = false)
            changeMode(dialogMode)
        }
    }

}