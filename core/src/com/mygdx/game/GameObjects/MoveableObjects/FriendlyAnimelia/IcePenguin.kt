package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.FriendlyAnimelia

class IcePenguin(gameObjectData: GameObjectData) : FriendlyAnimelia(gameObjectData) {
    override val animeliaEntity = ANIMELIA_ENTITY.ICE_PENGUIN
    override fun recruitmentAction() {
        TODO("Not yet implemented")
    }

    override val texture: Texture
        get() = TODO("Not yet implemented")
    override val layer: Layer
        get() = TODO("Not yet implemented")
}