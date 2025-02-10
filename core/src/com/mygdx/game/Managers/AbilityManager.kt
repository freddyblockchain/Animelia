package com.mygdx.game.Managers

import com.mygdx.game.Ability.Ability
import com.mygdx.game.Ability.AbilityName
import com.mygdx.game.Ability.convertNameToAbility
import com.mygdx.game.Inventory.AbilityPreference
import com.mygdx.game.generalSaveState
import com.mygdx.game.mainMode
import com.mygdx.game.player

class AbilityManager {
    companion object{
        val abilities = mutableListOf<Ability>()

        fun processAbilities(){
            for(ability in abilities.toMutableList()){
                ability.currentFrame += 1
                if(ability.currentFrame == 1){
                    ability.activated = true
                    ability.onActivate()
                    ability.attachedFightableObject.cannotMoveCount += 1
                }else if(ability.currentFrame >= ability.activeFrames){
                    ability.onDeactivate()
                    ability.activated = false
                    ability.currentFrame = 0
                    abilities.remove(ability)
                    ability.attachedFightableObject.cannotMoveCount -= 1
                } else {
                    ability.frameAction()
                }
            }
        }
        fun addToActiveAbilities(num: Int, abilityName: AbilityName){
            val ability = generalSaveState.inventory.ownedAbilities.first { it == abilityName }
            if(ability != null){
                player.activeAbilities[num] = convertNameToAbility(ability.name).keyAbility
            }
            val abilityList = generalSaveState.abilityPrefMap.getOrPut(player.animeliaInfo.animeliaEntity) { mutableListOf() }
            abilityList.add(AbilityPreference(num, abilityName))
            generalSaveState.updateSaveState()
            mainMode.abilityRowUi.updateToolTips()
        }
        fun removeFromActiveAbilities(num: Int){
            val ability = player.activeAbilities.getOrDefault(num,null)
            if(ability != null){
                player.activeAbilities[num] = null
            }
            val abilityList = generalSaveState.abilityPrefMap.getOrPut(player.animeliaInfo.animeliaEntity) { mutableListOf() }
            val prefToRemove = abilityList.find { it.numkey == num }
            if(prefToRemove != null ){
                abilityList.remove(prefToRemove)
                generalSaveState.updateSaveState()
            }
            mainMode.abilityRowUi.updateToolTips()
        }

    }
}