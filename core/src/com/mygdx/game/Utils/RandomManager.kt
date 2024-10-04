package com.mygdx.game.Utils

import kotlin.random.Random

class RandomManager{
    companion object {
        fun roll(percentageChance: Int): Boolean{
            require(percentageChance in 1..100) { "Percentage chance must be between 1 and 100." }

            val random = Random.nextInt(1, 101)  // Random number between 1 and 100
            return random <= percentageChance  // Return true if random is less than or equal to percentageChance
        }
    }
}