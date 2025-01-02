package com.mygdx.game.Animelia

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.Ability.AbilityName
import com.mygdx.game.Ability.ELEMENTAL_TYPE
import com.mygdx.game.Animation.AnimeliaAnimation
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Items.Material
import com.mygdx.game.Items.MaterialItem
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.PlayerStatus
import com.mygdx.game.player

interface AnimeliaData {
    var textureName: String
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

class IntelligenceOver(val int: Int): AnivolutionCondition{
    override fun isConditionFulfilled(): Boolean {
        return player.stats.intelligence >= int
    }
    override val textDescription = "Intelligence atleast at $int"
}

class PickedUpItem(val materialItem: Material): AnivolutionCondition{
    override fun isConditionFulfilled(): Boolean {
        return materialItem in player.materialsPickedUp
    }

    override val textDescription = "find and collect a ${materialItem.name}"

}


class InArea(val areaIdentifer: String): AnivolutionCondition{
    override fun isConditionFulfilled(): Boolean {
        return AreaManager.getActiveArea()!!.areaIdentifier == areaIdentifer
    }
    override val textDescription = "Must be in $areaIdentifer"
}

class FireArmadilloData(): AnimeliaData {
    override var textureName = "Animelias/firearmadillo-straight.png"
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.FIRE, ELEMENTAL_TYPE.FIGHTING)
    override val animeliaStage = ANIMELIA_STAGE.JUNIOR
    override val animeliaAnimation = AnimeliaAnimation("Animelias/firearmadillo-straight.png","Animelias/firearmadillo-right.png","Animelias/firearmadillo-left.png")
    override val animeliaEvolutionConditions = listOf<AnivolutionCondition>()
    override val possibleAnivolutions = listOf<ANIMELIA_ENTITY>(ANIMELIA_ENTITY.FireHippo, ANIMELIA_ENTITY.FireDragon)
    override val availableAbilities = listOf<AbilityName>(AbilityName.TailSwipe, AbilityName.Fireball, AbilityName.Dash, AbilityName.FlameBreath)
    override val animeliaEntity = ANIMELIA_ENTITY.FireArmadillo
}
class IcePenguinData(): AnimeliaData {
    override var textureName = "Animelias/icepenguin-straight.png"
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.ICE)
    override val animeliaStage = ANIMELIA_STAGE.JUNIOR
    override val animeliaAnimation = AnimeliaAnimation("Animelias/icepenguin-straight.png","Animelias/icepenguin-right.png","Animelias/icepenguin-left.png")
    override val animeliaEvolutionConditions = listOf<AnivolutionCondition>()
    override val possibleAnivolutions = listOf<ANIMELIA_ENTITY>(ANIMELIA_ENTITY.IceYeti, ANIMELIA_ENTITY.IceBird)
    override val availableAbilities = listOf<AbilityName>(AbilityName.Icicle, AbilityName.IceCocoon)
    override val animeliaEntity = ANIMELIA_ENTITY.IcePenguin
}
class FireHippoData(): AnimeliaData {
    override var textureName = "Animelias/firehippo-straight.png"
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.FIRE)
    override val animeliaStage = ANIMELIA_STAGE.MASTER
    override val animeliaAnimation = AnimeliaAnimation("Animelias/firehippo-straight.png","Animelias/firehippo-right.png","Animelias/firehippo-left.png")
    override val animeliaEvolutionConditions = listOf<AnivolutionCondition>(OffenceOver(13), InArea("World3"))
    override val possibleAnivolutions = listOf<ANIMELIA_ENTITY>()
    override val availableAbilities = listOf<AbilityName>(AbilityName.RockThrow, AbilityName.Fireball, AbilityName.TailSwipe)
    override val animeliaEntity = ANIMELIA_ENTITY.FireHippo
}
class FireDragonData(): AnimeliaData {
    override var textureName = "Butler.png"
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.FIRE)
    override val animeliaStage = ANIMELIA_STAGE.MASTER
    override val animeliaAnimation = AnimeliaAnimation("Animelias/firearmadillo-straight.png","Animelias/firearmadillo-right.png","Animelias/firearmadillo-left.png")
    override val animeliaEvolutionConditions = listOf<AnivolutionCondition>(OffenceOver(25))
    override val possibleAnivolutions = listOf<ANIMELIA_ENTITY>()
    override val availableAbilities = listOf<AbilityName>()
    override val animeliaEntity = ANIMELIA_ENTITY.FireDragon
}
class IceDinosaurData(): AnimeliaData {
    override var textureName = "Butler.png"
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.ICE)
    override val animeliaStage = ANIMELIA_STAGE.MASTER
    override val animeliaAnimation = AnimeliaAnimation("Animelias/firearmadillo-straight.png","Animelias/firearmadillo-right.png","Animelias/firearmadillo-left.png")
    override val animeliaEvolutionConditions = listOf<AnivolutionCondition>()
    override val possibleAnivolutions = listOf<ANIMELIA_ENTITY>()
    override val availableAbilities = listOf<AbilityName>()
    override val animeliaEntity = ANIMELIA_ENTITY.IceDinasaur
}
class IceYetiData(): AnimeliaData {
    override var textureName = "Animelias/ice-yeti-straight.png"
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.ICE, ELEMENTAL_TYPE.METAL)
    override val animeliaStage = ANIMELIA_STAGE.MASTER
    override val animeliaAnimation = AnimeliaAnimation("Animelias/ice-yeti-straight.png","Animelias/ice-yet-right.png","Animelias/ice-yeti-left.png")
    override val animeliaEvolutionConditions = listOf<AnivolutionCondition>(IntelligenceOver(15))
    override val possibleAnivolutions = listOf<ANIMELIA_ENTITY>()
    override val availableAbilities = listOf<AbilityName>(AbilityName.Icicle, AbilityName.ScrewAttack, AbilityName.ScrapStorm, AbilityName.Missile)
    override val animeliaEntity = ANIMELIA_ENTITY.IceYeti
}

class BirdData(): AnimeliaData {
    override var textureName = "Animelias/bird-straight.png"
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.FIGHTING)
    override val animeliaStage = ANIMELIA_STAGE.JUNIOR
    override val animeliaAnimation = AnimeliaAnimation("Animelias/bird-straight.png","Animelias/bird-right.png","Animelias/bird-left.png")
    override val animeliaEvolutionConditions = listOf<AnivolutionCondition>()
    override val possibleAnivolutions = listOf<ANIMELIA_ENTITY>(ANIMELIA_ENTITY.MetalBird)
    override val availableAbilities = listOf<AbilityName>(AbilityName.Fly)
    override val animeliaEntity = ANIMELIA_ENTITY.Bird
}

class IceBirdData(): AnimeliaData {
    override var textureName = "Animelias/IceBird-straight.png"
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.ICE, ELEMENTAL_TYPE.FLYING)
    override val animeliaStage = ANIMELIA_STAGE.MASTER
    override val animeliaAnimation = AnimeliaAnimation("Animelias/IceBird-straight.png","Animelias/IceBird-right.png","Animelias/IceBird-left.png")
    override val animeliaEvolutionConditions = listOf<AnivolutionCondition>(DefenceOver(15), PickedUpItem(Material.IceFruit))
    override val possibleAnivolutions = listOf<ANIMELIA_ENTITY>()
    override val availableAbilities = listOf<AbilityName>(AbilityName.Fly, AbilityName.IceCocoon, AbilityName.Icicle, AbilityName.Whirlwind)
    override val animeliaEntity = ANIMELIA_ENTITY.IceBird
}

class MetalBirdData(): AnimeliaData {
    override var textureName = "Animelias/IceBird-straight.png"
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.FLYING, ELEMENTAL_TYPE.METAL)
    override val animeliaStage = ANIMELIA_STAGE.MASTER
    override val animeliaAnimation = AnimeliaAnimation("Animelias/IceBird-straight.png","Animelias/IceBird-right.png","Animelias/IceBird-left.png")
    override val animeliaEvolutionConditions = listOf<AnivolutionCondition>(DefenceOver(15), PickedUpItem(Material.IceFruit))
    override val possibleAnivolutions = listOf<ANIMELIA_ENTITY>()
    override val availableAbilities = listOf<AbilityName>(AbilityName.Fly, AbilityName.Whirlwind)
    override val animeliaEntity = ANIMELIA_ENTITY.MetalBird
}

class FrogData(): AnimeliaData {
    override var textureName = "Animelias/frog.png"
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.SOUND)
    override val animeliaStage = ANIMELIA_STAGE.JUNIOR
    override val animeliaAnimation = AnimeliaAnimation("Animelias/frog.png","Animelias/frog.png","Animelias/frog.png")
    override val animeliaEvolutionConditions = listOf<AnivolutionCondition>()
    override val possibleAnivolutions = listOf<ANIMELIA_ENTITY>(ANIMELIA_ENTITY.GuardFrog)
    override val availableAbilities = listOf<AbilityName>(AbilityName.AmphibianLullaby, AbilityName.SoundGun)
    override val animeliaEntity = ANIMELIA_ENTITY.Frog
}

class GuardFrogData(): AnimeliaData {
    override var textureName = "Animelias/GuardianFrog-straight.png"
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.SOUND, ELEMENTAL_TYPE.FIGHTING)
    override val animeliaStage = ANIMELIA_STAGE.MASTER
    override val animeliaAnimation = AnimeliaAnimation("Animelias/GuardianFrog-straight.png","Animelias/GuardianFrog-right.png","Animelias/GuardianFrog-left.png")
    override val animeliaEvolutionConditions = listOf<AnivolutionCondition>(OffenceOver(15))
    override val possibleAnivolutions = listOf<ANIMELIA_ENTITY>()
    override val availableAbilities = listOf<AbilityName>(AbilityName.AmphibianLullaby, AbilityName.SoundGun)
    override val animeliaEntity = ANIMELIA_ENTITY.GuardFrog
}
class KingFrogData(): AnimeliaData {
    override var textureName = "Animelias/kingfrog-straight.png"
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.SOUND, ELEMENTAL_TYPE.FIGHTING, ELEMENTAL_TYPE.METAL)
    override val animeliaStage = ANIMELIA_STAGE.GRANDMASTER
    override val animeliaAnimation = AnimeliaAnimation("Animelias/kingfrog-straight.png","Animelias/kingfrog-straight.png","Animelias/kingfrog-straight.png")
    override val animeliaEvolutionConditions = listOf<AnivolutionCondition>(OffenceOver(15), DefenceOver(15))
    override val possibleAnivolutions = listOf<ANIMELIA_ENTITY>()
    override val availableAbilities = listOf<AbilityName>(AbilityName.AmphibianLullaby, AbilityName.SoundGun)
    override val animeliaEntity = ANIMELIA_ENTITY.KingFrog
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
        ANIMELIA_ENTITY.Bird -> {
            BirdData()
        }
        ANIMELIA_ENTITY.IceBird -> {
            IceBirdData()
        }
        ANIMELIA_ENTITY.MetalBird -> {
            MetalBirdData()
        }
        ANIMELIA_ENTITY.Frog -> {
            FrogData()
        }
        ANIMELIA_ENTITY.GuardFrog -> {
            GuardFrogData()
        }
        ANIMELIA_ENTITY.KingFrog -> {
            KingFrogData()
        }
    }
}