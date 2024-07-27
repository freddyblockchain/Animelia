package com.mygdx.game.Animelia

enum class Egg {FIRE, ICE}

fun getEggTexture(egg: Egg): String{
    return when (egg){
        Egg.ICE -> "iceegg.png"
        else -> "fireegg.png"
    }
}

fun getEggAnimelia(egg: Egg): ANIMELIA_ENTITY{
    return when (egg){
        Egg.ICE -> ANIMELIA_ENTITY.ICE_PENGUIN
        else -> ANIMELIA_ENTITY.FIRE_ARMADILLO
    }
}