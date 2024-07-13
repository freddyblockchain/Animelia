package com.mygdx.game.GameObjects.Animelia

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject

enum class ANIMELIA_ENTITY {FIRE_ARMADILLO, ICE_PENGUIN, FIRE_DRAGON, ICE_DINASAUR, ICE_YETI, FIRE_HIPPO}

enum class ELEMENTAL_TYPE{ICE, FIRE}

enum class ANIMELIA_STAGE{JUNIOR, MASTER, GRANDMASTER}

abstract class Animelia(gameObjectData: GameObjectData): GameObject(gameObjectData, Vector2(32f,32f)) {
    abstract val animeliaEntity: ANIMELIA_ENTITY
    val animeliaData: AnimeliaData
        get() = getAnimeliaData(animeliaEntity)
}