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
import com.mygdx.game.Managers.SignalManager

class CrystalActivationMode(
    val prevMode: GameMode,
    override val spriteBatch: SpriteBatch = mainMode.spriteBatch,
    val crystal: Crystal
) :
    GameMode {
    var currentFrame = 0

    val endFrame = 60

    val extendedEndFrames = 30

    var shouldExtend = false

    override val inputProcessor = DefaultInputProcessor()

    var prevRotation: Float = 0f

    var rotationInc = 0f

    override fun modeInit() {
        super.modeInit()
        prevRotation = player.sprite.rotation

    }

    override fun FrameAction() {

        spriteBatch.begin()
        prevMode.OnlyRenderFrameAction()
        spriteBatch.end()
        if (!shouldExtend) {
            if (currentFrame <= endFrame / 2) {
                rotationInc += 1f
            } else {
                rotationInc -= 1f
            }
            player.sprite.rotate(rotationInc)

            if (currentFrame == endFrame / 2) {
                player.setPosition(crystal.goToPosition.currentPosition())
            }

            if (currentFrame == endFrame) {
                val crystalPastSignals = SignalManager.pastSignals.filterIsInstance<CrystalActivatedSignal>()
                if (crystalPastSignals.none { it.crystalEntityIId == crystal.gameObjectIid }) {
                    shouldExtend = true
                } else {
                    changeMode(prevMode)
                }
            }
        } else {
            if(currentFrame < 120){
                crystal.statue.animateAlpha(crystal.index)
            } else{
                SignalManager.emitSignal(CrystalActivatedSignal(crystal.gameObjectIid, crystal.statue.gameObjectIid))
                changeMode(prevMode)
            }
        }
        currentFrame += 1
    }

    override fun render() {
        prevMode.render()
    }
}