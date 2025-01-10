package com.mygdx.game.Ability

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.Ability.Abilities.Fighting.RockThrowAbility
import com.mygdx.game.Ability.Abilities.Fighting.TailSwipe
import com.mygdx.game.Ability.Abilities.Fire.Dash
import com.mygdx.game.Ability.Abilities.Fire.FireballAbility
import com.mygdx.game.Ability.Abilities.Fire.FlameBreath
import com.mygdx.game.Ability.Abilities.Flying.AerialDeath
import com.mygdx.game.Ability.Abilities.Flying.Fly
import com.mygdx.game.Ability.Abilities.Flying.Whirlwind
import com.mygdx.game.Ability.Abilities.Ice.IceBreath
import com.mygdx.game.Ability.Abilities.Ice.IceCocoon
import com.mygdx.game.Ability.Abilities.Ice.IcicleAbility
import com.mygdx.game.Ability.Abilities.Metal.MissileAbility
import com.mygdx.game.Ability.Abilities.Metal.ScrapStorm
import com.mygdx.game.Ability.Abilities.Metal.ScrewAttack
import com.mygdx.game.Ability.Abilities.Sound.AmphibianLullaby
import com.mygdx.game.Ability.Abilities.Sound.SoundGunAbiltiy
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

enum class AbilityName{Fireball, TailSwipe, RockThrow, PlaceHolder, Icicle, Fly, IceCocoon,Dash, Whirlwind, AmphibianLullaby,SoundGun, Missile, ScrapStorm, ScrewAttack, FlameBreath, IceBreath, AerialDeath }
fun getAbilitiesFromType(ELEMENTALTYPES: ELEMENTAL_TYPE): List<AbilityName>{
    return when (ELEMENTALTYPES){
        ELEMENTAL_TYPE.FIRE -> listOf(AbilityName.Fireball, AbilityName.Dash, AbilityName.FlameBreath, AbilityName.PlaceHolder)
        ELEMENTAL_TYPE.FIGHTING -> listOf(AbilityName.RockThrow, AbilityName.TailSwipe, AbilityName.PlaceHolder, AbilityName.PlaceHolder)
        ELEMENTAL_TYPE.ICE -> listOf(AbilityName.Icicle, AbilityName.IceCocoon, AbilityName.IceBreath, AbilityName.PlaceHolder)
        ELEMENTAL_TYPE.FLYING -> listOf(AbilityName.Fly, AbilityName.Whirlwind, AbilityName.AerialDeath, AbilityName.PlaceHolder)
        ELEMENTAL_TYPE.SOUND -> listOf(AbilityName.SoundGun, AbilityName.AmphibianLullaby, AbilityName.PlaceHolder, AbilityName.PlaceHolder)
        ELEMENTAL_TYPE.METAL -> listOf(AbilityName.ScrewAttack, AbilityName.Missile, AbilityName.ScrapStorm, AbilityName.PlaceHolder)
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
        AbilityName.Fly -> "Fly over obstacles and projectiles in your way"
        AbilityName.Whirlwind -> "Perform a whirlwind dance reflecting projectiles"
        AbilityName.AmphibianLullaby -> "Perform a lullaby that puts listeners to sleep"
        AbilityName.SoundGun -> "Shoot a sharp sound towards foes"
        AbilityName.ScrapStorm -> "Create a storm of metal scraps around you"
        AbilityName.ScrewAttack -> "Shoot a screw at enemies and buildings"
        AbilityName.Missile -> "Shoot a missile that tracks enemies and objects"
        AbilityName.FlameBreath -> "Breathe fire in an arc in front of you"
        AbilityName.IceBreath -> "Breathe ice in an arc in front of you"
        AbilityName.AerialDeath -> "Fly high up above and come crashing down"
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
        "SoundGun" -> AbilityData(AbilityName.SoundGun, ELEMENTAL_TYPE.SOUND, SoundGunAbiltiy(player))
        "Missile" -> AbilityData(AbilityName.Missile, ELEMENTAL_TYPE.METAL, MissileAbility(player))
        "ScrewAttack" -> AbilityData(AbilityName.ScrewAttack, ELEMENTAL_TYPE.METAL, ScrewAttack(player))
        "ScrapStorm" -> AbilityData(AbilityName.ScrapStorm, ELEMENTAL_TYPE.METAL, ScrapStorm(player))
        "FlameBreath" -> AbilityData(AbilityName.FlameBreath, ELEMENTAL_TYPE.FIRE, FlameBreath(player))
        "IceBreath" -> AbilityData(AbilityName.IceBreath, ELEMENTAL_TYPE.ICE, IceBreath(player))
        "AerialDeath" -> AbilityData(AbilityName.AerialDeath, ELEMENTAL_TYPE.FLYING, AerialDeath(player))
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
    var activated: Boolean
}
