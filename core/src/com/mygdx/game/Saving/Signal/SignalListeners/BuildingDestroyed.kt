package com.mygdx.game.Saving.Signal.SignalListeners

import AnimeliaRecruitedSignal
import RemoveObjectSignal
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.GameModes.AnimationModes.CrystalActivation.BuildingRemoveAnimation
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia.createFriendlyAnimeliaInCity
import com.mygdx.game.GameObjects.Structures.House
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.Signal.Signal
import com.mygdx.game.Signal.SignaledEventListener
import com.mygdx.game.currentGameMode

class BuildingDestroyed: SignaledEventListener {
    override val signaltype = SIGNALTYPE.ANIMELIA_RECRUITED
    override fun triggerEvent(signal: Signal) {
        val animeliaRecruitedSignal = signal as AnimeliaRecruitedSignal
        if(AreaManager.getActiveArea()!!.areaIdentifier == "World4" && animeliaRecruitedSignal.animeliaEntity == ANIMELIA_ENTITY.IceBird){
            val area = AreaManager.getArea("World4")
            val iceCastle = area.gameObjects.firstOrNull { it is House && it.textureName == "IceCastle.png"}
            val pastRemoveSignals = SignalManager.pastSignals.filterIsInstance<RemoveObjectSignal>()
            if(iceCastle != null && pastRemoveSignals.none { it.entityIid == iceCastle.gameObjectIid }){
                changeMode(BuildingRemoveAnimation(currentGameMode, house = iceCastle as House))
                SignalManager.emitSignal(RemoveObjectSignal(iceCastle.gameObjectIid))
                SignalManager.emitSignal(RemoveObjectSignal(iceCastle.door.gameObjectIid))
            }
        }

        if(AreaManager.getActiveArea()!!.areaIdentifier == "World3" && animeliaRecruitedSignal.animeliaEntity == ANIMELIA_ENTITY.FireLion){
            val area = AreaManager.getArea("World3")
            val iceCastle = area.gameObjects.firstOrNull { it is House && it.textureName == "vulcano.png"}
            val pastRemoveSignals = SignalManager.pastSignals.filterIsInstance<RemoveObjectSignal>()
            if(iceCastle != null && pastRemoveSignals.none { it.entityIid == iceCastle.gameObjectIid }){
                changeMode(BuildingRemoveAnimation(currentGameMode, house = iceCastle as House))
                SignalManager.emitSignal(RemoveObjectSignal(iceCastle.gameObjectIid))
                SignalManager.emitSignal(RemoveObjectSignal(iceCastle.door.gameObjectIid))
            }
        }

        if(AreaManager.getActiveArea()!!.areaIdentifier == "World2" && animeliaRecruitedSignal.animeliaEntity == ANIMELIA_ENTITY.SoundBat){
            val area = AreaManager.getArea("World2")
            val iceCastle = area.gameObjects.firstOrNull { it is House && it.textureName == "AbandonedHouse.png"}
            val pastRemoveSignals = SignalManager.pastSignals.filterIsInstance<RemoveObjectSignal>()
            if(iceCastle != null && pastRemoveSignals.none { it.entityIid == iceCastle.gameObjectIid }){
                changeMode(BuildingRemoveAnimation(currentGameMode, house = iceCastle as House))
                SignalManager.emitSignal(RemoveObjectSignal(iceCastle.gameObjectIid))
                SignalManager.emitSignal(RemoveObjectSignal(iceCastle.door.gameObjectIid))
            }
        }
    }
}