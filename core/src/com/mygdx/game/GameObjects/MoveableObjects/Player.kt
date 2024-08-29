package com.mygdx.game.GameObjects.MoveableEntities.Characters

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Ability.Abilities.Fighting.TailSwipe
import com.mygdx.game.Ability.AbilityType
import com.mygdx.game.Ability.KeyAbility
import com.mygdx.game.Animelia.*
import com.mygdx.game.Area.AreaType
import com.mygdx.game.Area.getAreaType
import com.mygdx.game.CannotMoveStrategy.NoAction
import com.mygdx.game.Collisions.CanMoveCollision
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameModes.UIMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.Stats
import com.mygdx.game.SaveHandling.SaveStateEntity
import com.mygdx.game.Saving.DefaultSaveStateHandler
import com.mygdx.game.UI.PlayerHealthStrategy
import com.mygdx.game.UI.Scene2d.Screens.ReincarnationScreen

class Player(gameObjectData: GameObjectData, size: Vector2)
    : FightableObject(gameObjectData, size), SaveStateEntity by DefaultSaveStateHandler() {
    override val texture = DefaultTextureHandler.getTexture("player.png")
    override var speed: Float = 3f
    override val cannotMoveStrategy = NoAction()
    override val layer = Layer.PERSON
    override var direction = Direction.RIGHT
    override var canChangeDirection = true
    override val collision = CanMoveCollision()
    override val maxHealth = 30f
    override var stats = Stats()
    val ownedAbilities: MutableList<KeyAbility> = mutableListOf(TailSwipe(this))
    val activeAbilities: MutableMap<Int, KeyAbility?> = mutableMapOf()
    var currentAnimelia: ANIMELIA_ENTITY = ANIMELIA_ENTITY.FireArmadillo
    var animeliaInfo = getAnimeliaData(currentAnimelia)
    val eggs: MutableList<Egg> = mutableListOf(Egg.FIRE)

    override val healthStrategy = PlayerHealthStrategy()

    init {
        currentHealth = maxHealth
        activeAbilities[1] = ownedAbilities[0]
    }

    override fun render(batch: SpriteBatch) {
        setAnimeliaSpriteTexture(this, animeliaInfo)
        super.render(batch)
    }

    override fun frameTask() {
        super.frameTask()
        val currentAreaIdentifier = AreaManager.getActiveArea()!!.areaIdentifier

        if(getAreaType(currentAreaIdentifier) == AreaType.Ice && (ELEMENTAL_TYPE.ICE !in animeliaInfo.elemental_types)){
            currentHealth -= 0.2f
        }else if((getAreaType(currentAreaIdentifier) == AreaType.Fire && (ELEMENTAL_TYPE.FIRE !in animeliaInfo.elemental_types))){
            currentHealth -= 0.2f
        }

        if(currentHealth <= 0){
            death()
        }
    }

    fun death(){
        changeArea(startPos, "World1")
        val reincarnationMode = UIMode(ReincarnationScreen(mainMode))
        changeMode(reincarnationMode)
        currentHealth = maxHealth
    }
}