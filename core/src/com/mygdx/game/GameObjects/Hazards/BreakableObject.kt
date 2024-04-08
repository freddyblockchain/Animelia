package com.mygdx.game.GameObjects.Hazards

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.Projectile
import com.mygdx.game.Managers.AreaManager

class BreakableObject(gameObjectData: GameObjectData): GameObject(gameObjectData,
    Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())
) {
    override val texture = DefaultTextureHandler.getTexture("FireGate.png")
    override val layer = Layer.AIR
    override val collision = BreakableObjectCollision(this)
}

class BreakableObjectCollision(val breakableObject: BreakableObject): MoveCollision(){
    override var canMoveAfterCollision = false

    override fun collisionHappened(collidedObject: GameObject) {
        if(collidedObject is Projectile){
            AreaManager.getActiveArea()!!.gameObjects.remove(breakableObject)
        }
    }

}