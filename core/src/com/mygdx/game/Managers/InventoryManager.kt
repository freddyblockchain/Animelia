package com.mygdx.game.Managers

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Ability.Ability
import com.mygdx.game.Animation.TextAnimation

class InventoryManager {
    companion object{
        private var gold = 0


        fun goldReceived(amount: Int,pos: Vector2){
            gold += amount
            val textAnimation = TextAnimation(Color.YELLOW, "+ $amount gold", pos)

            AnimationManager.animationManager.add(textAnimation)
        }
    }
}