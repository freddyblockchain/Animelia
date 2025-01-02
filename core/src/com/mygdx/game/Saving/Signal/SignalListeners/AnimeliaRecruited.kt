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
    val cityAreas = listOf("World1", "FrogHouse_City", "Iglo_City")
    override fun triggerEvent(signal: Signal) {
        val area = AreaManager.getActiveArea()!!.areaIdentifier

        if(area in cityAreas){
            val animeliaRecruitedSignal = signal as AnimeliaRecruitedSignal
            val type = animeliaRecruitedSignal.animeliaEntity
            val correspondingAnimelia = createFriendlyAnimeliaInCity(type, Vector2(animeliaRecruitedSignal.posX, animeliaRecruitedSignal.posY))
            val firstArea = AreaManager.getArea(area)
            correspondingAnimelia.initObject()
            firstArea.gameObjects.add(correspondingAnimelia)
            correspondingAnimelia.recruitmentAction()
        }
    }
}