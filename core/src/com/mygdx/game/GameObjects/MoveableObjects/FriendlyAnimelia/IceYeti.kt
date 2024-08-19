package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.FriendlyAnimelia
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.EntityRefData

class IceYeti(gameObjectData: GameObjectData, cityPosEntityId: EntityRefData) : FriendlyAnimelia(gameObjectData,
    cityPosEntityId
) {
    override val animeliaEntity = ANIMELIA_ENTITY.IceYeti
    override fun recruitmentAction() {
    }
}