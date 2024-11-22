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
        // Fix dis.

        // We should have 2 objects, instead of 1 friendly animelia object. Its too confusing
        //Redesign animelia recruitment
        val animeliaRecruitedSignal = signal as AnimeliaRecruitedSignal
        val type = animeliaRecruitedSignal.animeliaEntity
        val correspondingAnimelia = createFriendlyAnimeliaInCity(type, Vector2(animeliaRecruitedSignal.posX, animeliaRecruitedSignal.posY))
        val firstArea = AreaManager.getArea("World1")
        correspondingAnimelia.initObject()
        firstArea.gameObjects.add(correspondingAnimelia)
        correspondingAnimelia.recruitmentAction()
    }
}