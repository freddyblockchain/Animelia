package com.mygdx.game.Animelia

import com.badlogic.gdx.graphics.Texture

enum class Egg {FIRE, ICE, SOUND,FLYING}

fun getEggTexture(egg: Egg): String {
    return when (egg){
        Egg.ICE -> "iceegg.png"
        Egg.SOUND -> "forestegg.png"
        Egg.FLYING -> "canyonegg.png"
        else -> "fireegg.png"
    }
}

fun getEggAnimelia(egg: Egg): ANIMELIA_ENTITY{
    return when (egg){
        Egg.ICE -> ANIMELIA_ENTITY.IcePenguin
        Egg.SOUND -> ANIMELIA_ENTITY.Frog
        Egg.FLYING-> ANIMELIA_ENTITY.Bird
        else -> ANIMELIA_ENTITY.FireArmadillo
    }
}