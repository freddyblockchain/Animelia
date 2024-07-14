package com.mygdx.game.GameObjects.MoveableObjects.AnimeliaEnemies

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.EnemyAnimelia
import com.mygdx.game.Animelia.FriendlyAnimelia
import com.mygdx.game.DefaultTextureHandler

class FireArmadilloEnemy(gameObjectData: GameObjectData) : EnemyAnimelia(gameObjectData) {
    override val animeliaEntity = ANIMELIA_ENTITY.FIRE_ARMADILLO
    override val texture = DefaultTextureHandler.getTexture("Box.png")
    override val layer = Layer.ONGROUND
}