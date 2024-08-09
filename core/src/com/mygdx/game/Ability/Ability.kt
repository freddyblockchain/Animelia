package com.mygdx.game.Ability

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.Ability.Abilities.Fighting.FireballData
import com.mygdx.game.Ability.Abilities.Fighting.TailSwipeData
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.GameObjects.GameObject.FightableObject

enum class AbilityName{Fireball, TailSwipe, PlaceHolder}

enum class AbilityType{Fire, Fighting,Ice, Sound}

fun getAbilitiesFromType(abilityType: AbilityType): List<AbilityName>{
    return when (abilityType){
        AbilityType.Fire -> listOf(AbilityName.Fireball, AbilityName.PlaceHolder, AbilityName.PlaceHolder, AbilityName.PlaceHolder)
        AbilityType.Fighting -> listOf(AbilityName.PlaceHolder, AbilityName.TailSwipe, AbilityName.PlaceHolder, AbilityName.PlaceHolder)
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

fun convertAbilityToType(ability:String): AbilityName{
    return when(ability){
        "Fireball" -> AbilityName.Fireball
        else -> AbilityName.TailSwipe
    }
}
fun convertAbilityTypeToData(ability: AbilityName): AbilityData{
    return when(ability){
        AbilityName.Fireball -> FireballData()
        else -> TailSwipeData()
    }
}

interface AbilityData{
    val imageIcon: String
}
interface Ability {
    fun onActivate()
    fun onDeactivate()
    val activeFrames: Int
    var currentFrame: Int
    fun frameAction()
    val attachedFightableObject: FightableObject

}
