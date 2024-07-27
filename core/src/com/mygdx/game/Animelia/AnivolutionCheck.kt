package com.mygdx.game.Animelia

import com.mygdx.game.GameModes.AnivolutionMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.currentGameMode
import com.mygdx.game.player

fun anivolutionCheck(){
    for(animelia in player.animeliaInfo.possibleAnivolutions){
        val animeliaData = getAnimeliaData(animelia)
        if(animeliaData.animeliaEvolutionConditions.all { it.isConditionFulfilled() }){
            changeMode(AnivolutionMode(currentGameMode, animelia))
            break
        }
    }
}