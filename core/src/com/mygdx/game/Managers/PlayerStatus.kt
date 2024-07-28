package com.mygdx.game.Managers


data class Stats(var offence: Int = 10,
                 var defence: Int = 10,
                 var speed: Int = 10,
                 var intelligence: Int = 10
){

}
class PlayerStatus {


    companion object {
        var age = 5
        var maxAge = 10
        var exp = 5

        val playerStats = Stats()

        var animeliaClonesKilled = 0
    }
}