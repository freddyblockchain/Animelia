package com.mygdx.game.Animelia

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.Animation.AnimeliaAnimation
import com.mygdx.game.DefaultTextureHandler

interface AnimeliaData {
    val gameTexture: Texture
    val elemental_types: List<ELEMENTAL_TYPE>
    val animeliaStage: ANIMELIA_STAGE
    val animeliaAnimation: AnimeliaAnimation
}

class FireArmadilloData(): AnimeliaData {
    override val gameTexture = DefaultTextureHandler.getTexture("Animelias/firearmadillo-straight.png")
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.FIRE)
    override val animeliaStage = ANIMELIA_STAGE.JUNIOR
    override val animeliaAnimation = AnimeliaAnimation("Animelias/firearmadillo-straight.png","Animelias/firearmadillo-right.png","Animelias/firearmadillo-left.png")
}
class IcePenguinData(): AnimeliaData {
    override val gameTexture = DefaultTextureHandler.getTexture("Brick.png")
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.ICE)
    override val animeliaStage = ANIMELIA_STAGE.JUNIOR
    override val animeliaAnimation = AnimeliaAnimation("Animelias/firearmadillo-straight.png","Animelias/firearmadillo-right.png","Animelias/firearmadillo-left.png")
}
class FireHippoData(): AnimeliaData {
    override val gameTexture = DefaultTextureHandler.getTexture("Butler.png")
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.FIRE)
    override val animeliaStage = ANIMELIA_STAGE.MASTER
    override val animeliaAnimation = AnimeliaAnimation("Animelias/firearmadillo-straight.png","Animelias/firearmadillo-right.png","Animelias/firearmadillo-left.png")
}
class FireDragonData(): AnimeliaData {
    override val gameTexture = DefaultTextureHandler.getTexture("Butler.png")
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.FIRE)
    override val animeliaStage = ANIMELIA_STAGE.MASTER
    override val animeliaAnimation = AnimeliaAnimation("Animelias/firearmadillo-straight.png","Animelias/firearmadillo-right.png","Animelias/firearmadillo-left.png")
}
class IceDinasaurData(): AnimeliaData {
    override val gameTexture = DefaultTextureHandler.getTexture("Butler.png")
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.ICE)
    override val animeliaStage = ANIMELIA_STAGE.MASTER
    override val animeliaAnimation = AnimeliaAnimation("Animelias/firearmadillo-straight.png","Animelias/firearmadillo-right.png","Animelias/firearmadillo-left.png")
}
class IceYetiData(): AnimeliaData {
    override val gameTexture = DefaultTextureHandler.getTexture("Butler.png")
    override val elemental_types: List<ELEMENTAL_TYPE> = listOf(ELEMENTAL_TYPE.ICE)
    override val animeliaStage = ANIMELIA_STAGE.MASTER
    override val animeliaAnimation = AnimeliaAnimation("Animelias/firearmadillo-straight.png","Animelias/firearmadillo-right.png","Animelias/firearmadillo-left.png")
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
            IceDinasaurData()
        }
    }
}