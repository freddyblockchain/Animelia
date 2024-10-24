package com.mygdx.game.GameObjects.MoveableEntities.Characters

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Ability.KeyAbility
import com.mygdx.game.Animation.SpeechBubble
import com.mygdx.game.Animelia.*
import com.mygdx.game.CannotMoveStrategy.NoAction
import com.mygdx.game.Collisions.CanMoveCollision
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameModes.AnimationModes.DeathMode
import com.mygdx.game.GameModes.UIMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.GameObject.FightableObject
import com.mygdx.game.GameObjects.GameObject.State
import com.mygdx.game.SaveHandling.SaveStateEntity
import com.mygdx.game.Saving.DefaultSaveStateHandler
import com.mygdx.game.Timer.CooldownTimer
import com.mygdx.game.UI.PlayerHealthStrategy
import com.mygdx.game.UI.Scene2d.Screens.ReincarnationScreen

enum class PlayerEnvironmentState {HOT, COLD, NORMAL}

class Player(gameObjectData: GameObjectData, size: Vector2)
    : FightableObject(gameObjectData, size), SaveStateEntity by DefaultSaveStateHandler() {
    override val texture = DefaultTextureHandler.getTexture("player.png")
    override var speed: Float = 3f
    override val cannotMoveStrategy = NoAction()
    override var layer = Layer.PERSON
    override var direction = Direction.RIGHT
    override var canChangeDirection = true
    override val collision = CanMoveCollision()
    override val maxHealth = 50f
    val abilityCooldown = CooldownTimer(1f)
    val activeAbilities: MutableMap<Int, KeyAbility?> = mutableMapOf()
    var animeliaInfo = getAnimeliaData(ANIMELIA_ENTITY.FireArmadillo)
    var playerEnvironmentState = PlayerEnvironmentState.NORMAL

    val coldSpeech = SpeechBubble("This area is too cold for me!", this, 0)
    val hotSpeech = SpeechBubble("This area is too hot for me!",this,0)

    override val stats
        get() = generalSaveState.stats

    override val healthStrategy = PlayerHealthStrategy()

    init {
        currentHealth = maxHealth
    }

    override fun render(batch: SpriteBatch) {
        if(this.state == State.NORMAL){
            setAnimeliaSpriteTexture(this, animeliaInfo)
        }
        super.render(batch)

        if(playerEnvironmentState == PlayerEnvironmentState.COLD){
            coldSpeech.render(batch)
        }
        else if(playerEnvironmentState == PlayerEnvironmentState.HOT){
            hotSpeech.render(batch)
        }
    }

    override fun frameTask() {
        super.frameTask()
        if(playerEnvironmentState == PlayerEnvironmentState.HOT || playerEnvironmentState == PlayerEnvironmentState.COLD){
            currentHealth -= 0.2f
        }

        if(currentHealth <= 0){
            death()
        }
    }

    fun death(){
        val reincarnationMode = UIMode(ReincarnationScreen(mainMode), playConfirmationSound = false)
        val deathMode = DeathMode(mainMode, reincarnationMode)
        changeMode(deathMode)
    }
}