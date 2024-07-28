package com.mygdx.game.Managers

import com.mygdx.game.Event.DefaultEvent
import com.mygdx.game.Event.Event

class EventManager {
    companion object {
        val eventManager = mutableListOf<DefaultEvent>()

        fun executeEvents(){
            for(event in eventManager.toMutableList()){
                event.execute()
                if(!event.eternal){
                    event.currentFrame += 1
                }
                if(event.currentFrame >= event.framesToExecute ){
                    eventManager.remove(event)
                }
            }
        }
    }
}
