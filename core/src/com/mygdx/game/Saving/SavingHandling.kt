package com.mygdx.game.Saving

import com.badlogic.gdx.math.Vector2
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
                generalSaveState = PlayerSaveState(savedGeneralSaveState.inventory, savedGeneralSaveState.stats, savedGeneralSaveState.currentAnimelia, savedGeneralSaveState.pos, savedGeneralSaveState.areaIdentifier)
                player.animeliaInfo = getAnimeliaData(generalSaveState.currentAnimelia)

            } else {
                generalSaveState = PlayerSaveState(Inventory(), Stats(), ANIMELIA_ENTITY.FireArmadillo, SVector2(120f, -200f), "World1")
            }
            AreaManager.setActiveArea(generalSaveState.areaIdentifier)
            player.setPosition(Vector2(generalSaveState.pos.x, generalSaveState.pos.y))
        }
    }
}