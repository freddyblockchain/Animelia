package com.mygdx.game.Event


interface Event {
    fun execute()
}
abstract class DefaultEvent(val eternal: Boolean = false ,val framesToExecute: Int = 60): Event {
    var currentFrame = 0
}