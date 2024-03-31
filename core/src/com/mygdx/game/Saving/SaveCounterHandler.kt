package com.mygdx.game.Saving

import com.mygdx.game.SaveHandling.SaveStateEntity

class SaveCounterHandler {
    companion object{
        private var saveCounter = 0

        fun getSaveCounter():Int{
            return saveCounter++
        }
    }
}

open class DefaultSaveStateHandler: SaveStateEntity {
    override val entityId = SaveCounterHandler.getSaveCounter()
}
