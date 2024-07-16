package com.mygdx.game

import PauseMode
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Ability.KeyAbility
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.getDirectionUnitVector
import com.mygdx.game.GameModes.TrainingMode
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.Managers.AbilityManager


class InGameProcessor : InputProcessor {
    override fun keyDown(keycode: Int): Boolean {

        /*for (keyAbility in player.abilities.filterIsInstance<KeyAbility>()) {
            if (keycode == keyAbility.triggerKey) {
                keyAbility.onActivate()
            }
        }*/
        if(keycode == Input.Keys.ESCAPE){
            currentGameMode = PauseMode(currentGameMode)
        }
        if(keycode == Input.Keys.SPACE){
            currentGameMode = TrainingMode(currentGameMode)
        }
        for(ability in player.abilities){
            if(ability.triggerKey == keycode){
                AbilityManager.abilities.add(ability)
            }
        }
        return false
    }

    fun handleInput() {
        var directionUnitVector = Vector2(0f,0f)
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            directionUnitVector = getDirectionUnitVector(Direction.LEFT)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            directionUnitVector = getDirectionUnitVector(Direction.RIGHT)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            directionUnitVector = getDirectionUnitVector(Direction.UP)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            directionUnitVector = getDirectionUnitVector(Direction.DOWN)
        }
        if(directionUnitVector != Vector2(0f,0f)){
            if (player.move(directionUnitVector))
            {
                player.setRotation(directionUnitVector, player, 90f)
            }
        }
    }

    override fun keyUp(keycode: Int): Boolean {
        /*
        for (keyAbility in player.abilities.filterIsInstance<KeyAbility>()) {
            if (keycode == keyAbility.triggerKey) {
                keyAbility.onDeactivate()
            }
        }
        return false
     */
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