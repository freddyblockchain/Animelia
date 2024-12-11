package com.mygdx.game.GameModes.AnimationModes.CrystalActivation

import CrystalActivatedSignal
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.GameModes.DefaultInputProcessor
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.GameObject.State
import com.mygdx.game.GameObjects.Other.Crystals.Crystal
import com.mygdx.game.GameObjects.Structures.House
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.Utils.RandomManager

class BuildingRemoveAnimation(
    val prevMode: GameMode,
    override val spriteBatch: SpriteBatch = mainMode.spriteBatch,
    val house: House
) :
    GameMode {
    var currentFrame = 0
    val from = -1.5f
    var to = 1.5f

    val endFrame = 180

    override val inputProcessor = DefaultInputProcessor()

    var widthInc = 0f
    var heightInc = 0f

    override fun modeInit() {
        super.modeInit()
        currentFrame = 0

        widthInc = house.width / endFrame
        heightInc =  house.height / endFrame
    }

    override fun FrameAction() {
        house.sprite.setSize(house.sprite.width - widthInc, house.sprite.height - heightInc)
        house.sprite.setPosition(house.sprite.x + widthInc / 2, house.sprite.y + heightInc / 2)
        currentFrame += 1
        prevMode.OnlyRenderFrameAction()
        if(currentFrame == endFrame){
            changeMode(prevMode)
        }
    }

    override fun cameraAction() {
        var randx = RandomManager.randomNumber(from,to)
        var randy = RandomManager.randomNumber(from,to)
        mainCamera.position.set(player.sprite.x + randx, player.sprite.y + randy, 0f)
    }

    override fun render() {
        prevMode.render()
        spriteBatch.begin()
        house.render(spriteBatch)
        spriteBatch.end()
    }
}