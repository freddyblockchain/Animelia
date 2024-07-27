package com.mygdx.game.Animelia

import com.badlogic.gdx.graphics.Texture
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
}

interface AnivolutionCondition{
    fun isConditionFulfilled(): Boolean
}

class OffenceOver(val offence: Int): AnivolutionCondition{
    override fun isConditionFulfilled(): Boolean {
        return PlayerStatus.playerStats.offence >= offence
    }
}

class InArea(val areaIdentifer: String): AnivolutionCondition{
    override fun isConditionFulfilled(): Boolean {
        return AreaManager.getActiveArea()!!.areaIdentifier == areaIdentifer
    }
}

class FireArmadilloData(): AnimeliaData {
    override var gameTexture = DefaultTextureHandler.getTexture("Animelias/firearmadillo-straight.png")
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.FIRE)
    override val animeliaStage = ANIMELIA_STAGE.JUNIOR
    override val animeliaAnimation = AnimeliaAnimation("Animelias/firearmadillo-straight.png","Animelias/firearmadillo-right.png","Animelias/firearmadillo-left.png")
    override val animeliaEvolutionConditions = listOf<AnivolutionCondition>()
    override val possibleAnivolutions = listOf<ANIMELIA_ENTITY>(ANIMELIA_ENTITY.FIRE_HIPPO)
}
class IcePenguinData(): AnimeliaData {
    override var gameTexture = DefaultTextureHandler.getTexture("Animelias/icepenguin-straight.png")
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.ICE)
    override val animeliaStage = ANIMELIA_STAGE.JUNIOR
    override val animeliaAnimation = AnimeliaAnimation("Animelias/icepenguin-straight.png","Animelias/icepenguin-right.png","Animelias/icepenguin-left.png")
    override val animeliaEvolutionConditions = listOf<AnivolutionCondition>()
    override val possibleAnivolutions = listOf<ANIMELIA_ENTITY>()
}
class FireHippoData(): AnimeliaData {
    override var gameTexture = DefaultTextureHandler.getTexture("Animelias/firehippo-straight.png")
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.FIRE)
    override val animeliaStage = ANIMELIA_STAGE.MASTER
    override val animeliaAnimation = AnimeliaAnimation("Animelias/firehippo-straight.png","Animelias/firehippo-right.png","Animelias/firehippo-left.png")
    override val animeliaEvolutionConditions = listOf<AnivolutionCondition>(OffenceOver(13), InArea("World3"))
    override val possibleAnivolutions = listOf<ANIMELIA_ENTITY>()
}
class FireDragonData(): AnimeliaData {
    override var gameTexture = DefaultTextureHandler.getTexture("Butler.png")
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.FIRE)
    override val animeliaStage = ANIMELIA_STAGE.MASTER
    override val animeliaAnimation = AnimeliaAnimation("Animelias/firearmadillo-straight.png","Animelias/firearmadillo-right.png","Animelias/firearmadillo-left.png")
    override val animeliaEvolutionConditions = listOf<AnivolutionCondition>()
    override val possibleAnivolutions = listOf<ANIMELIA_ENTITY>()
}
class IceDinosaurData(): AnimeliaData {
    override var gameTexture = DefaultTextureHandler.getTexture("Butler.png")
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.ICE)
    override val animeliaStage = ANIMELIA_STAGE.MASTER
    override val animeliaAnimation = AnimeliaAnimation("Animelias/firearmadillo-straight.png","Animelias/firearmadillo-right.png","Animelias/firearmadillo-left.png")
    override val animeliaEvolutionConditions = listOf<AnivolutionCondition>()
    override val possibleAnivolutions = listOf<ANIMELIA_ENTITY>()
}
class IceYetiData(): AnimeliaData {
    override var gameTexture = DefaultTextureHandler.getTexture("Butler.png")
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.ICE)
    override val animeliaStage = ANIMELIA_STAGE.MASTER
    override val animeliaAnimation = AnimeliaAnimation("Animelias/firearmadillo-straight.png","Animelias/firearmadillo-right.png","Animelias/firearmadillo-left.png")
    override val animeliaEvolutionConditions = listOf<AnivolutionCondition>()
    override val possibleAnivolutions = listOf<ANIMELIA_ENTITY>()
}

fun getAnimeliaData(animeliaEntity: ANIMELIA_ENTITY): AnimeliaData {
    return when(animeliaEntity){
        ANIMELIA_ENTITY.FIRE_ARMADILLO ->  {
            FireArmadilloData()
        }
        ANIMELIA_ENTITY.FIRE_DRAGON -> {
            FireDragonData()
        }
        ANIMELIA_ENTITY.ICE_YETI -> {
            IceYetiData()
        }
        ANIMELIA_ENTITY.FIRE_HIPPO -> {
            FireHippoData()
        }
        ANIMELIA_ENTITY.ICE_PENGUIN -> {
            IcePenguinData()
        }
        ANIMELIA_ENTITY.ICE_DINASAUR -> {
            IceDinosaurData()
        }
    }
}