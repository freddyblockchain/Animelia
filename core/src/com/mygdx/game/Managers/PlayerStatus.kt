package com.mygdx.game.Managers


data class Stats(var offence: Int = 10,
                 var defence: Int = 10,
                 var speed: Int = 10,
                 var intelligence: Int = 10
){

}
class PlayerStatus {

    companion object {
        var baseTp = 5
        var tp = 5
        var baseOffence = 10
        var baseDefence = 10
        var baseSpeed = 10
        var baseIntelligence = 10
        var animeliaClonesKilled = 0
    }
}