package com.mygdx.game.Managers

import SIGNALTYPE
import com.mygdx.game.FileHandler.Companion.writeSignalToFile
import com.mygdx.game.Signal.Signal
import com.mygdx.game.Signal.SignaledEventListener

class SignalManager {
        companion object {
            val listenerMap = mutableMapOf<SIGNALTYPE,MutableList<SignaledEventListener>>()
            val signalManager = mutableListOf<Signal>()
            val pastSignals = mutableListOf<Signal>()
            init {
                for (signalType in SIGNALTYPE.values()){
                    listenerMap[signalType] = mutableListOf()
                }
            }
            fun addSignalListeners(listeners: List<SignaledEventListener>){
                listeners.forEach {
                    listenerMap[it.signaltype]!!.add(it)
                }
            }
            fun emitSignal(signal: Signal, saveSignal: Boolean = true){
                if(saveSignal){
                    writeSignalToFile(signal)
                }
                signalManager.add(signal)
            }
            fun executeSignals(){
                for(signal in signalManager.toList()){
                    listenerMap[signal.signaltype]!!.forEach {
                        it.triggerEvent(signal)
                    }
                    signalManager.remove(signal)
                }
            }
        }
}