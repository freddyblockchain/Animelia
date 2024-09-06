package com.mygdx.game.Ability

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.Ability.Abilities.Fighting.RockThrowAbility
import com.mygdx.game.Ability.Abilities.Fighting.TailSwipe
import com.mygdx.game.Ability.Abilities.Fire.FireballAbility
import com.mygdx.game.Ability.Abilities.Ice.IcicleAbility
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.player

enum class AbilityName{Fireball, TailSwipe, RockThrow, PlaceHolder, Icicle}

enum class ELEMENTAL_TYPE{FIRE,FIGHTING,ICE, SOUND}

data class AbilityData(val abilityName: AbilityName, val ELEMENTALTYPES: ELEMENTAL_TYPE, val keyAbility: KeyAbility)

fun getAbilitiesFromType(ELEMENTALTYPES: ELEMENTAL_TYPE): List<AbilityName>{
    return when (ELEMENTALTYPES){
        ELEMENTAL_TYPE.FIRE -> listOf(AbilityName.Fireball, AbilityName.PlaceHolder, AbilityName.PlaceHolder, AbilityName.PlaceHolder)
        ELEMENTAL_TYPE.FIGHTING -> listOf(AbilityName.RockThrow, AbilityName.TailSwipe, AbilityName.PlaceHolder, AbilityName.PlaceHolder)
        ELEMENTAL_TYPE.ICE -> listOf(AbilityName.Icicle, AbilityName.PlaceHolder, AbilityName.PlaceHolder, AbilityName.PlaceHolder)
        else -> listOf(AbilityName.PlaceHolder, AbilityName.PlaceHolder, AbilityName.PlaceHolder, AbilityName.PlaceHolder)
    }
}

fun getIconFromType(ELEMENTALTYPES: ELEMENTAL_TYPE): Texture{
    return when(ELEMENTALTYPES){
        ELEMENTAL_TYPE.FIRE -> DefaultTextureHandler.getTexture("fireball-icon.png")
        ELEMENTAL_TYPE.FIGHTING -> DefaultTextureHandler.getTexture("fightingIcon.png")
        ELEMENTAL_TYPE.ICE -> DefaultTextureHandler.getTexture("SnowFlake.png")
        else -> DefaultTextureHandler.getTexture("EmptyDoor.png")
    }
}

fun convertAbilityToName(ability:String): AbilityData{
    return when(ability){
        "Fireball" -> AbilityData(AbilityName.Fireball, ELEMENTAL_TYPE.FIRE, FireballAbility(player))
        "RockThrow" -> AbilityData(AbilityName.RockThrow, ELEMENTAL_TYPE.FIGHTING, RockThrowAbility(player))
        "Icicle" -> AbilityData(AbilityName.Icicle, ELEMENTAL_TYPE.ICE, IcicleAbility(player))
        "TailSwipe" -> AbilityData(AbilityName.TailSwipe, ELEMENTAL_TYPE.FIGHTING, TailSwipe(player))
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
