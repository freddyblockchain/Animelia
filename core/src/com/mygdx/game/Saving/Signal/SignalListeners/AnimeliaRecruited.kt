package com.mygdx.game.Saving.Signal.SignalListeners

import AnimeliaRecruitedSignal
import com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia.friendlyAnimelias
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Signal.Signal
import com.mygdx.game.Signal.SignaledEventListener

class AnimeliaRecruited: SignaledEventListener {
    override val signaltype = SIGNALTYPE.ANIMELIA_RECRUITED
    override fun triggerEvent(signal: Signal) {
        val animeliaRecruitedSignal = signal as AnimeliaRecruitedSignal
        val type = animeliaRecruitedSignal.animeliaEntity
        val correspondingAnimelia = friendlyAnimelias.first{ it.animeliaEntity == type }
        correspondingAnimelia.isRecruited = true
        correspondingAnimelia.initObject()
        correspondingAnimelia.recruitmentAction()
    }
}