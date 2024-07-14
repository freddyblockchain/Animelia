package com.mygdx.game.GameObjects.MoveableObjects.Animelias

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.FriendlyAnimelia

class IceDinasaur(gameObjectData: GameObjectData) : FriendlyAnimelia(gameObjectData) {
    override val animeliaEntity = ANIMELIA_ENTITY.ICE_DINASAUR
    override val texture: Texture
        get() = TODO("Not yet implemented")
    override val layer: Layer
        get() = TODO("Not yet implemented")
}