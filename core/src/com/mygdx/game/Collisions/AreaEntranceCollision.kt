package com.mygdx.game.Collisions

import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.GameObjects.GameObject.GameObject


abstract class AreaEntranceCollition: MoveCollision() {
   abstract var insideCollition: MutableMap<GameObject, Boolean>
   abstract fun movedInside(objectEntered: GameObject)
    abstract fun movedOutside(objectLeaved: GameObject)
    abstract fun movedInsideAction(objectEntered: GameObject)
    abstract fun movedOutsideAction(objectLeaved: GameObject)
}

abstract class DefaultAreaEntranceCollition(): AreaEntranceCollition(){
    override var insideCollition: MutableMap<GameObject, Boolean> = mutableMapOf()

    override fun movedOutside(objectLeaved: GameObject){
        if(insideCollition.getOrDefault(objectLeaved,true)){
            insideCollition[objectLeaved] = false
            movedOutsideAction(objectLeaved)
        }
    }
    override fun movedInside(objectEntered: GameObject){
        if(!(insideCollition.getOrDefault(objectEntered,false))){
            insideCollition[objectEntered] = true
            movedInsideAction(objectEntered)
        }
    }

    override fun collisionHappened(collidedObject: GameObject) {
        movedInside(collidedObject)
    }
}