package com.mygdx.game.GameObjects.MoveableObjects.Animelias

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.FriendlyAnimelia
import com.mygdx.game.DefaultTextureHandler

class FireArmadillo(gameObjectData: GameObjectData) : FriendlyAnimelia(gameObjectData) {
    override val animeliaEntity = ANIMELIA_ENTITY.FIRE_ARMADILLO
    override val texture = DefaultTextureHandler.getTexture("Rocket.png")
    override val layer = Layer.ONGROUND
}