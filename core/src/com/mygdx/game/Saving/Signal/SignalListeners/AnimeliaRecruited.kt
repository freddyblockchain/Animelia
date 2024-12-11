package com.mygdx.game.Saving.Signal.SignalListeners

import AnimeliaRecruitedSignal
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia.createFriendlyAnimelia
import com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia.createFriendlyAnimeliaInCity
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Signal.Signal
import com.mygdx.game.Signal.SignaledEventListener

class AnimeliaRecruited: SignaledEventListener {
    override val signaltype = SIGNALTYPE.ANIMELIA_RECRUITED
    override fun triggerEvent(signal: Signal) {

        if(AreaManager.getActiveArea()!!.areaIdentifier == "World1"){
            val animeliaRecruitedSignal = signal as AnimeliaRecruitedSignal
            val type = animeliaRecruitedSignal.animeliaEntity
            val correspondingAnimelia = createFriendlyAnimeliaInCity(type, Vector2(animeliaRecruitedSignal.posX, animeliaRecruitedSignal.posY))
            val firstArea = AreaManager.getArea("World1")
            correspondingAnimelia.initObject()
            firstArea.gameObjects.add(correspondingAnimelia)
            correspondingAnimelia.recruitmentAction()
        }
    }
}