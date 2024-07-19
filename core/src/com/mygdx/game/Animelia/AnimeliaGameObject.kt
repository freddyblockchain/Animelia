package com.mygdx.game.Animelia

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.CannotMoveStrategy.NoAction
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.GameObjects.GameObject.GameObject

enum class ANIMELIA_ENTITY {FIRE_ARMADILLO, ICE_PENGUIN, FIRE_DRAGON, ICE_DINASAUR, ICE_YETI, FIRE_HIPPO}

enum class ELEMENTAL_TYPE{ICE, FIRE}

enum class ANIMELIA_STAGE{JUNIOR, MASTER, GRANDMASTER}

abstract class FriendlyAnimelia(gameObjectData: GameObjectData): GameObject(gameObjectData, Vector2(32f,32f)) {
    abstract val animeliaEntity: ANIMELIA_ENTITY
    val animeliaData: AnimeliaData
        get() = getAnimeliaData(animeliaEntity)
}

abstract class EnemyAnimelia(gameObjectData: GameObjectData): FightableObject(gameObjectData, Vector2(32f,32f)) {
    abstract val animeliaEntity: ANIMELIA_ENTITY
    abstract val animeliaInfo: AnimeliaData

    override var speed = 1f
    override val cannotMoveStrategy = NoAction()
    override val layer = Layer.ONGROUND
    override var direction: Direction
        get() = TODO("Not yet implemented")
        set(value) {}
    override var canChangeDirection: Boolean
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun render(batch: SpriteBatch) {
        setAnimeliaSpriteTexture(this, animeliaInfo)
        super.render(batch)
    }

}

fun setAnimeliaSpriteTexture(animelia: FightableObject, animeliaInfo: AnimeliaData){
    if(animelia.isMoving){
        animeliaInfo.animeliaAnimation.setSpriteTextureBasedOnAnimation(animelia.sprite)
        animelia.isMoving = false
    } else {
        animelia.sprite.texture = animeliaInfo.gameTexture
        animeliaInfo.animeliaAnimation.reset()
    }
}

fun getAnimeliaEntity(animeliaType: String): ANIMELIA_ENTITY {
    return when(animeliaType){
        "Fire_Armadillo" -> ANIMELIA_ENTITY.FIRE_ARMADILLO
        else -> ANIMELIA_ENTITY.FIRE_DRAGON
    }
}