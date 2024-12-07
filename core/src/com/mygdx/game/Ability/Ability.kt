package com.mygdx.game.Ability

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.Ability.Abilities.Fighting.RockThrowAbility
import com.mygdx.game.Ability.Abilities.Fighting.TailSwipe
import com.mygdx.game.Ability.Abilities.Fire.Dash
import com.mygdx.game.Ability.Abilities.Fire.FireballAbility
import com.mygdx.game.Ability.Abilities.Flying.Fly
import com.mygdx.game.Ability.Abilities.Flying.Whirlwind
import com.mygdx.game.Ability.Abilities.Ice.IceCocoon
import com.mygdx.game.Ability.Abilities.Ice.IcicleAbility
import com.mygdx.game.Ability.Abilities.Sound.AmphibianLullaby
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.player

enum class ELEMENTAL_TYPE{FIRE,FIGHTING,ICE, SOUND, METAL, FLYING}

fun getIconFromType(ELEMENTALTYPES: ELEMENTAL_TYPE): Texture{
    return when(ELEMENTALTYPES){
        ELEMENTAL_TYPE.FIRE -> DefaultTextureHandler.getTexture("fireball-icon.png")
        ELEMENTAL_TYPE.FIGHTING -> DefaultTextureHandler.getTexture("fightingIcon.png")
        ELEMENTAL_TYPE.ICE -> DefaultTextureHandler.getTexture("SnowFlake.png")
        ELEMENTAL_TYPE.FLYING -> DefaultTextureHandler.getTexture("flying.png")
        ELEMENTAL_TYPE.SOUND -> DefaultTextureHandler.getTexture("SoundIcon.png")
        else -> DefaultTextureHandler.getTexture("EmptyDoor.png")
    }
}

enum class AbilityName{Fireball, TailSwipe, RockThrow, PlaceHolder, Icicle, Fly, IceCocoon,Dash, Whirlwind, AmphibianLullaby}
fun getAbilitiesFromType(ELEMENTALTYPES: ELEMENTAL_TYPE): List<AbilityName>{
    return when (ELEMENTALTYPES){
        ELEMENTAL_TYPE.FIRE -> listOf(AbilityName.Fireball, AbilityName.Dash, AbilityName.PlaceHolder, AbilityName.PlaceHolder)
        ELEMENTAL_TYPE.FIGHTING -> listOf(AbilityName.RockThrow, AbilityName.TailSwipe, AbilityName.PlaceHolder, AbilityName.PlaceHolder)
        ELEMENTAL_TYPE.ICE -> listOf(AbilityName.Icicle, AbilityName.IceCocoon, AbilityName.PlaceHolder, AbilityName.PlaceHolder)
        ELEMENTAL_TYPE.FLYING -> listOf(AbilityName.Fly, AbilityName.Whirlwind, AbilityName.PlaceHolder, AbilityName.PlaceHolder)
        ELEMENTAL_TYPE.SOUND -> listOf(AbilityName.AmphibianLullaby, AbilityName.PlaceHolder, AbilityName.PlaceHolder, AbilityName.PlaceHolder)
        else -> listOf(AbilityName.PlaceHolder, AbilityName.PlaceHolder, AbilityName.PlaceHolder, AbilityName.PlaceHolder)
    }
}

fun getDescriptionFromName(abilityName: AbilityName): String{
    return when(abilityName){
        AbilityName.Icicle -> "Shoot a sharp shard of ice"
        AbilityName.TailSwipe -> "A lounging tail attack! Use it to break rocks and enemies"
        AbilityName.RockThrow -> "Throw a deadly rock at enemies"
        AbilityName.Fireball -> "Shoot a fireball, that melts enemies and ice"
        AbilityName.IceCocoon -> "Envelop yourself in ice for protection"
        AbilityName.Dash -> "Do a fire dash, which grants a speed boost"
        AbilityName.Fly -> "Fly over obstacles in your way"
        AbilityName.Whirlwind -> "Perform a whirlwind dance reflecting projectiles"
        AbilityName.AmphibianLullaby -> "Perform a lullaby that puts listeners to sleep"
        else -> "Nothing"
    }
}

data class AbilityData(val abilityName: AbilityName, val ELEMENTALTYPES: ELEMENTAL_TYPE, val keyAbility: KeyAbility)

fun convertNameToAbility(abilityName: String): AbilityData{
    return when(abilityName){
        "Fireball" -> AbilityData(AbilityName.Fireball, ELEMENTAL_TYPE.FIRE, FireballAbility(player))
        "RockThrow" -> AbilityData(AbilityName.RockThrow, ELEMENTAL_TYPE.FIGHTING, RockThrowAbility(player))
        "Icicle" -> AbilityData(AbilityName.Icicle, ELEMENTAL_TYPE.ICE, IcicleAbility(player))
        "TailSwipe" -> AbilityData(AbilityName.TailSwipe, ELEMENTAL_TYPE.FIGHTING, TailSwipe(player))
        "IceCocoon" -> AbilityData(AbilityName.IceCocoon, ELEMENTAL_TYPE.ICE, IceCocoon(player))
        "Dash" -> AbilityData(AbilityName.Dash, ELEMENTAL_TYPE.FIRE, Dash(player))
        "Fly" -> AbilityData(AbilityName.Fly, ELEMENTAL_TYPE.FLYING, Fly(player))
        "Whirlwind" -> AbilityData(AbilityName.Whirlwind, ELEMENTAL_TYPE.FLYING, Whirlwind(player))
        "AmphibianLullaby" -> AbilityData(AbilityName.AmphibianLullaby, ELEMENTAL_TYPE.SOUND, AmphibianLullaby(player))
        else -> AbilityData(AbilityName.Fireball,ELEMENTAL_TYPE.FIRE, FireballAbility(player))
    }
}


interface Ability {
    fun onActivate()
    fun onDeactivate()
    val activeFrames: Int
    var currentFrame: Int
    fun frameAction()
    val attachedFightableObject: FightableObject
    val abilityName: AbilityName
    val ELEMENTALTYPES: ELEMENTAL_TYPE

}
