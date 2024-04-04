package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.mygdx.game.Abilities.KeyAbility
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.getDirectionUnitVector


class MyInputProcessor : InputProcessor {
    override fun keyDown(keycode: Int): Boolean {

        for (keyAbility in player.abilities.filterIsInstance<KeyAbility>()) {
            if (keycode == keyAbility.triggerKey) {
                keyAbility.onActivate()
            }
        }
        return false
    }

    fun handleInput() {
        var directionUnitVector = Vector2(0f,0f)
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            directionUnitVector = getDirectionUnitVector(Direction.LEFT)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            directionUnitVector = getDirectionUnitVector(Direction.RIGHT)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            directionUnitVector = getDirectionUnitVector(Direction.UP)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            directionUnitVector = getDirectionUnitVector(Direction.DOWN)
        }
        if(directionUnitVector != Vector2(0f,0f)){
            player.move(directionUnitVector)
            player.setRotation(directionUnitVector, player, 90f)
        }

        val clickPosition = camera.unproject(Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f))
            val unitVectorTowardsPoint = getUnitVectorTowardsPoint(
                Vector2(butler.sprite.x, butler.sprite.y),
                Vector2(clickPosition.x, clickPosition.y)
            )
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                if(butler.active) {
                    butler.move((unitVectorTowardsPoint))
                }
            }
        }

    override fun keyUp(keycode: Int): Boolean {
        for (keyAbility in player.abilities.filterIsInstance<KeyAbility>()) {
            if (keycode == keyAbility.triggerKey) {
                keyAbility.onDeactivate()
            }
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