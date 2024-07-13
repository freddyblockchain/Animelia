package com.mygdx.game.GameObjects.Animelia.Animelias

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.GameObjects.Animelia.ANIMELIA_STAGE
import com.mygdx.game.GameObjects.Animelia.Animelia
import com.mygdx.game.GameObjects.Animelia.ELEMENTAL_TYPE

class IcePenguin(gameObjectData: GameObjectData) : Animelia(gameObjectData) {
    override val animeliaEntity = ANIMELIA_ENTITY.ICE_PENGUIN
    override val texture: Texture
        get() = TODO("Not yet implemented")
    override val layer: Layer
        get() = TODO("Not yet implemented")
}