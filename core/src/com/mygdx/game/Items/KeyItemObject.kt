package com.mygdx.game.Items

import RemoveObjectSignal
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.Animelia.anivolutionCheck
import com.mygdx.game.GameObjectData
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.generalSaveState
import com.mygdx.game.player
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class KeyItemObject(gameObjectData: GameObjectData) : WorldItem(gameObjectData) {

    val customFields = Json.decodeFromJsonElement<ItemCustomFields>(gameObjectData.customFields)
    override val itemAquiredText = "You found the ${customFields.Item}!"
    val keyItem = KeyItem.values().find { it.name == customFields.Item }
    override val texture = getKeyItemTextures(keyItem!!)

    override fun itemGained() {
        generalSaveState.inventory.keyItems.add(keyItem!!)
        generalSaveState.updateSaveState()
        SignalManager.emitSignal(RemoveObjectSignal(this.gameObjectIid))
        anivolutionCheck()
    }

    override fun render(batch: SpriteBatch) {
        super.render(batch)
    }
}
