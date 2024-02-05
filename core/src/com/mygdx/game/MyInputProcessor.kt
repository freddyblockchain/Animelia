package com.mygdx.game

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.getDirectionUnitVector





class MyInputProcessor : InputProcessor {
    override fun keyDown(keycode: Int): Boolean {
        val scale = 5f
        val cameradir = when (keycode) {
            Input.Keys.LEFT -> getDirectionUnitVector(Direction.LEFT)
            Input.Keys.RIGHT -> getDirectionUnitVector(Direction.RIGHT)
            Input.Keys.UP -> getDirectionUnitVector(Direction.UP)
            Input.Keys.DOWN -> getDirectionUnitVector(Direction.DOWN)
            else -> Vector2(0f,0f)
        }
        cameraDirection = cameradir * scale
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.LEFT, Input.Keys.RIGHT,  Input.Keys.UP, Input.Keys.DOWN -> cameraDirection = Vector2(0f,0f)
        }
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun touchDown(x: Int, y: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchUp(x: Int, y: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchCancelled(p0: Int, p1: Int, p2: Int, p3: Int): Boolean {
        return false
    }

    override fun touchDragged(x: Int, y: Int, pointer: Int): Boolean {
        return false
    }

    override fun mouseMoved(x: Int, y: Int): Boolean {
        return false
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        return false
    }
}