package com.mygdx.game.Animelia

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.Ability.AbilityName
import com.mygdx.game.Ability.ELEMENTAL_TYPE
import com.mygdx.game.Animation.AnimeliaAnimation
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.PlayerStatus
import com.mygdx.game.player

interface AnimeliaData {
    var gameTexture: Texture
    val elemental_types: List<ELEMENTAL_TYPE>
    val animeliaStage: ANIMELIA_STAGE
    val animeliaAnimation: AnimeliaAnimation
    val animeliaEvolutionConditions: List<AnivolutionCondition>
    val possibleAnivolutions: List<ANIMELIA_ENTITY>
    val availableAbilities: List<AbilityName>
    val animeliaEntity: ANIMELIA_ENTITY
}

interface AnivolutionCondition{
    fun isConditionFulfilled(): Boolean
    val textDescription: String
}

class OffenceOver(val offence: Int): AnivolutionCondition{
    override fun isConditionFulfilled(): Boolean {
        return player.stats.offence >= offence
    }
    override val textDescription = "Offence atleast at $offence"
}

class DefenceOver(val defence: Int): AnivolutionCondition{
    override fun isConditionFulfilled(): Boolean {
        return player.stats.defence >= defence
    }
    override val textDescription = "Defence atleast at $defence"
}


class InArea(val areaIdentifer: String): AnivolutionCondition{
    override fun isConditionFulfilled(): Boolean {
        return AreaManager.getActiveArea()!!.areaIdentifier == areaIdentifer
    }
    override val textDescription = "Must be in $areaIdentifer"
}

class FireArmadilloData(): AnimeliaData {
    override var gameTexture = DefaultTextureHandler.getTexture("Animelias/firearmadillo-straight.png")
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.FIRE, ELEMENTAL_TYPE.FIGHTING)
    override val animeliaStage = ANIMELIA_STAGE.JUNIOR
    override val animeliaAnimation = AnimeliaAnimation("Animelias/firearmadillo-straight.png","Animelias/firearmadillo-right.png","Animelias/firearmadillo-left.png")
    override val animeliaEvolutionConditions = listOf<AnivolutionCondition>()
    override val possibleAnivolutions = listOf<ANIMELIA_ENTITY>(ANIMELIA_ENTITY.FireHippo, ANIMELIA_ENTITY.FireDragon)
    override val availableAbilities = listOf<AbilityName>(AbilityName.TailSwipe, AbilityName.Fireball)
    override val animeliaEntity = ANIMELIA_ENTITY.FireArmadillo
}
class IcePenguinData(): AnimeliaData {
    override var gameTexture = DefaultTextureHandler.getTexture("Animelias/icepenguin-straight.png")
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.ICE)
    override val animeliaStage = ANIMELIA_STAGE.JUNIOR
    override val animeliaAnimation = AnimeliaAnimation("Animelias/icepenguin-straight.png","Animelias/icepenguin-right.png","Animelias/icepenguin-left.png")
    override val animeliaEvolutionConditions = listOf<AnivolutionCondition>()
    override val possibleAnivolutions = listOf<ANIMELIA_ENTITY>(ANIMELIA_ENTITY.IceYeti)
    override val availableAbilities = listOf<AbilityName>(AbilityName.Icicle)
    override val animeliaEntity = ANIMELIA_ENTITY.IcePenguin
}
class FireHippoData(): AnimeliaData {
    override var gameTexture = DefaultTextureHandler.getTexture("Animelias/firehippo-straight.png")
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.FIRE)
    override val animeliaStage = ANIMELIA_STAGE.MASTER
    override val animeliaAnimation = AnimeliaAnimation("Animelias/firehippo-straight.png","Animelias/firehippo-right.png","Animelias/firehippo-left.png")
    override val animeliaEvolutionConditions = listOf<AnivolutionCondition>(OffenceOver(13), InArea("World3"))
    override val possibleAnivolutions = listOf<ANIMELIA_ENTITY>()
    override val availableAbilities = listOf<AbilityName>(AbilityName.RockThrow, AbilityName.Fireball)
    override val animeliaEntity = ANIMELIA_ENTITY.FireHippo
}
class FireDragonData(): AnimeliaData {
    override var gameTexture = DefaultTextureHandler.getTexture("Butler.png")
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.FIRE)
    override val animeliaStage = ANIMELIA_STAGE.MASTER
    override val animeliaAnimation = AnimeliaAnimation("Animelias/firearmadillo-straight.png","Animelias/firearmadillo-right.png","Animelias/firearmadillo-left.png")
    override val animeliaEvolutionConditions = listOf<AnivolutionCondition>(OffenceOver(25))
    override val possibleAnivolutions = listOf<ANIMELIA_ENTITY>()
    override val availableAbilities = listOf<AbilityName>()
    override val animeliaEntity = ANIMELIA_ENTITY.FireDragon
}
class IceDinosaurData(): AnimeliaData {
    override var gameTexture = DefaultTextureHandler.getTexture("Butler.png")
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.ICE)
    override val animeliaStage = ANIMELIA_STAGE.MASTER
    override val animeliaAnimation = AnimeliaAnimation("Animelias/firearmadillo-straight.png","Animelias/firearmadillo-right.png","Animelias/firearmadillo-left.png")
    override val animeliaEvolutionConditions = listOf<AnivolutionCondition>()
    override val possibleAnivolutions = listOf<ANIMELIA_ENTITY>()
    override val availableAbilities = listOf<AbilityName>()
    override val animeliaEntity = ANIMELIA_ENTITY.IceDinasaur
}
class IceYetiData(): AnimeliaData {
    override var gameTexture = DefaultTextureHandler.getTexture("Animelias/ice-yeti-straight.png")
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.ICE)
    override val animeliaStage = ANIMELIA_STAGE.MASTER
    override val animeliaAnimation = AnimeliaAnimation("Animelias/ice-yeti-straight.png","Animelias/ice-yet-right.png","Animelias/ice-yeti-left.png")
    override val animeliaEvolutionConditions = listOf<AnivolutionCondition>(DefenceOver(15))
    override val possibleAnivolutions = listOf<ANIMELIA_ENTITY>()
    override val availableAbilities = listOf<AbilityName>(AbilityName.Icicle)
    override val animeliaEntity = ANIMELIA_ENTITY.IceYeti
}

fun getAnimeliaData(animeliaEntity: ANIMELIA_ENTITY): AnimeliaData {
    return when(animeliaEntity){
        ANIMELIA_ENTITY.FireArmadillo->  {
            FireArmadilloData()
        }
        ANIMELIA_ENTITY.FireDragon -> {
            FireDragonData()
        }
        ANIMELIA_ENTITY.IceYeti -> {
            IceYetiData()
        }
        ANIMELIA_ENTITY.FireHippo -> {
            FireHippoData()
        }
        ANIMELIA_ENTITY.IcePenguin -> {
            IcePenguinData()
        }
        ANIMELIA_ENTITY.IceDinasaur -> {
            IceDinosaurData()
        }
    }
}