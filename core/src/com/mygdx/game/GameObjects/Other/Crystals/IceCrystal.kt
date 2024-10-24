package com.mygdx.game.GameObjects.Other.Crystals

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.Hazards.BoxingGloveCustomFields
import com.mygdx.game.Managers.AreaManager
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class IceCrystal(gameObjectData: GameObjectData) : Crystal(gameObjectData) {
    override val texture = DefaultTextureHandler.getTexture("IceCrystal.png")
}

