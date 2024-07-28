package com.mygdx.game.Collisions

import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.Event.DefaultEvent
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Managers.EventManager


abstract class AreaEntranceCollition: MoveCollision() {
   abstract var insideCollition: MutableMap<GameObject, Boolean>
   abstract fun movedInside(objectEntered: GameObject)
    abstract fun movedOutside(objectLeaved: GameObject)
    abstract fun movedInsideAction(objectEntered: GameObject)
    abstract fun movedOutsideAction(objectLeaved: GameObject)
}

abstract class DefaultAreaEntranceCollition(): AreaEntranceCollition(){
    override var insideCollition: MutableMap<GameObject, Boolean> = mutableMapOf()

    abstract fun actionWhileInside()

    val insideEvent = object : DefaultEvent(eternal = true) {
        override fun execute() {
            actionWhileInside()
        }
    }

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

    override fun movedInsideAction(objectEntered: GameObject) {
        EventManager.eventManager.add(insideEvent)
    }

    override fun movedOutsideAction(objectLeaved: GameObject) {
        EventManager.eventManager.remove(insideEvent)
    }
}