package com.mygdx.game.Ability

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.Ability.Abilities.Fighting.RockThrowAbility
import com.mygdx.game.Ability.Abilities.Fire.FireballAbility
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.player

enum class AbilityName{Fireball, TailSwipe, RockThrow, PlaceHolder}

enum class AbilityType{Fire, Fighting,Ice, Sound}

data class AbilityData(val abilityName: AbilityName, val abilityType: AbilityType, val keyAbility: KeyAbility)

fun getAbilitiesFromType(abilityType: AbilityType): List<AbilityName>{
    return when (abilityType){
        AbilityType.Fire -> listOf(AbilityName.Fireball, AbilityName.PlaceHolder, AbilityName.PlaceHolder, AbilityName.PlaceHolder)
        AbilityType.Fighting -> listOf(AbilityName.RockThrow, AbilityName.TailSwipe, AbilityName.PlaceHolder, AbilityName.PlaceHolder)
        else -> listOf(AbilityName.PlaceHolder, AbilityName.PlaceHolder, AbilityName.PlaceHolder, AbilityName.PlaceHolder)
    }
}

fun getIconFromType(abilityType: AbilityType): Texture{
    return when(abilityType){
        AbilityType.Fire -> DefaultTextureHandler.getTexture("fireball-icon.png")
        AbilityType.Fighting -> DefaultTextureHandler.getTexture("TailSwipeIcon.png")
        else -> DefaultTextureHandler.getTexture("EmptyDoor.png")
    }
}

fun convertAbilityToName(ability:String): AbilityData{
    return when(ability){
        "Fireball" -> AbilityData(AbilityName.Fireball, AbilityType.Fire, FireballAbility(player))
        "RockThrow" -> AbilityData(AbilityName.RockThrow, AbilityType.Fighting, RockThrowAbility(player))
        else -> AbilityData(AbilityName.Fireball,AbilityType.Fire, FireballAbility(player))
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
    val abilityType: AbilityType
}
