package com.mygdx.game.GameObjects.Hazards.Generator

import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.generateMissile

class MissileGenerator(gameObjectData: GameObjectData): Generator(gameObjectData, 0f,6f, generateMissile(gameObjectData,2f)) {

}