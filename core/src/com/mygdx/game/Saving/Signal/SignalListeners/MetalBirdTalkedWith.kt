package com.mygdx.game.Saving.Signal.SignalListeners

import MetalBirdTalkingSignal
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia.MetalBird
import com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia.MetalBirdPos
import com.mygdx.game.GameObjects.Other.Crystals.Statue
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Signal.Signal
import com.mygdx.game.Signal.SignaledEventListener
import java.util.Vector

class MetalBirdTalkedWith: SignaledEventListener {
    override val signaltype = SIGNALTYPE.METAL_BIRD_TALKING
    override fun triggerEvent(signal: Signal) {
        val metalBirdTalkingSignal = signal as MetalBirdTalkingSignal
        val areaIdentifer = signal.areaIdentifer
        val area = AreaManager.getArea(areaIdentifer)
        val metalBird = AreaManager.getObjectWithIid(entityId = metalBirdTalkingSignal.entityIid, metalBirdTalkingSignal.levelId) as MetalBird
        metalBird.initObject()
        // get new metal bird position based on previous
        metalBird.metalBirdPos = when(signal.metalBirdPos){
            MetalBirdPos.ICE -> MetalBirdPos.FIRE
            MetalBirdPos.FIRE -> MetalBirdPos.CANYON
            MetalBirdPos.CANYON -> MetalBirdPos.SWAMP
            MetalBirdPos.SWAMP -> MetalBirdPos.ICE
        }
        metalBird.speeches = when(signal.metalBirdPos){
            MetalBirdPos.ICE -> listOf(metalBird.fireSpeech1)
            MetalBirdPos.FIRE -> listOf(metalBird.canyonSpeech1)
            MetalBirdPos.CANYON -> listOf(metalBird.swampSpeech1)
            MetalBirdPos.SWAMP -> listOf(metalBird.fireSpeech1)
        }
        // get new position for metal bird
        val newPos = when(metalBird.metalBirdPos){
            MetalBirdPos.ICE -> metalBird.positionList.find { it.name == "VulcanoPos"}
            MetalBirdPos.FIRE -> metalBird.positionList.find { it.name == "VulcanoPos"}
            MetalBirdPos.CANYON -> metalBird.positionList.find { it.name == "CanyonPos"}
            MetalBirdPos.SWAMP -> metalBird.positionList.find { it.name == "SwampPos"}
        }!!
        metalBird.setPosition(newPos.currentPosition())
        metalBird.areaIdentifier = area.areaIdentifier
        area.gameObjects.add(metalBird)
    }
}