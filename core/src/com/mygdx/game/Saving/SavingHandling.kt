package com.mygdx.game.Saving

import com.mygdx.game.Ability.AbilityName
import com.mygdx.game.Ability.convertAbilityToName
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.getAnimeliaData
import com.mygdx.game.FileHandler
import com.mygdx.game.Inventory.Inventory
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.Managers.Stats
import com.mygdx.game.Signal.Signal
import com.mygdx.game.Signal.initSignalListeners
import com.mygdx.game.generalSaveState
import com.mygdx.game.player
import kotlinx.serialization.json.Json
import signalConvert

class SavingHandler {
    companion object {
        fun InitHandleSaving(){
            if (!FileHandler.SaveFileEmpty()) {
                val savedState: String = FileHandler.readPlayerFile()[0]
                val savedGeneralSaveState: PlayerSaveState = Json.decodeFromString(savedState)
                generalSaveState = PlayerSaveState(savedGeneralSaveState.inventory, savedGeneralSaveState.stats, savedGeneralSaveState.currentAnimelia)
                player.animeliaInfo = getAnimeliaData(generalSaveState.currentAnimelia)

            } else {
                generalSaveState = PlayerSaveState(Inventory(), Stats(), ANIMELIA_ENTITY.FireArmadillo)
                AreaManager.setActiveArea("World1")
            }
            initSignalListeners()
            val originalFile = FileHandler.readSignalFile()
            val savedSignals: List<Signal> = originalFile.map(::signalConvert)
            savedSignals.forEach {
                SignalManager.pastSignals.add(it)
            }
        }
    }
}