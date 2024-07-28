package com.mygdx.game.Input

interface InputAction {
    val keycodes: List<Int>
    fun action(keycode: Int): Unit
}