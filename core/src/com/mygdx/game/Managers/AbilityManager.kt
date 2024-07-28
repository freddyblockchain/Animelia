package com.mygdx.game.Managers

import com.mygdx.game.Ability.Ability

class AbilityManager {
    companion object{
        val abilities = mutableListOf<Ability>()

        fun processAbilities(){
            for(ability in abilities.toMutableList()){
                ability.currentFrame += 1
                if(ability.currentFrame == 1){
                    ability.onActivate()
                    ability.attachedFightableObject.cannotMoveCount += 1
                }else if(ability.currentFrame >= ability.activeFrames){
                    ability.onDeactivate()
                    ability.currentFrame = 0
                    abilities.remove(ability)
                    ability.attachedFightableObject.cannotMoveCount -= 1
                } else {
                    ability.frameAction()
                }
            }
        }
    }
}