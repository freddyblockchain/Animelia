package com.mygdx.game.Animation

abstract class DefaultAnimation: Animation {
    override var currentFrame = 1
    override fun reset() {
        currentFrame = 1
    }

    override val animationAction = {}

    override var actionFrame = 0


}