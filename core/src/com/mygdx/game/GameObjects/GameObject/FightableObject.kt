package com.mygdx.game.GameObjects.GameObject

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Ability.KeyAbility
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia.GuardFrogState
import com.mygdx.game.Managers.AbilityManager
import com.mygdx.game.Managers.Stats
import com.mygdx.game.UI.HealthStrategy

enum class FlyingState{FLYING, NOTFLYING}
enum class ReflectingState{REFLECTING, NOTREFLECTING}
enum class State{NORMAL, STUNNED,SHIELDED, ASLEEP}

abstract class FightableObject(gameObjectData: GameObjectData, size: Vector2) : MoveableObject(gameObjectData, size) {
    var cannotMoveCount = 0
    var isMoving = false
    abstract val maxHealth: Float
    var currentHealth = 0f
    open val stats = Stats()
    abstract val healthStrategy: HealthStrategy
    var flyingState = FlyingState.NOTFLYING
    var state = State.NORMAL
    var reflectState = ReflectingState.NOTREFLECTING

    var asleepSprite: Sprite = Sprite()

    var sleepCounter = 0
    val sleepDuration = 60

    override fun frameTask() {
        super.frameTask()

        if(this.state == State.ASLEEP){
            sleepCounter += 1
            if(sleepCounter >= sleepDuration){
                this.cannotMoveCount -= 1
                sleepCounter = 0
                this.state = State.NORMAL
            }
        }
    }


    override fun move(newUnitVector: Vector2, speed: Float): Boolean {
        if (cannotMoveCount == 0) {
            isMoving = super.move(newUnitVector, speed)
            return isMoving
        }
        return false
    }

    fun forceMove(speed: Float) {
        super.move(this.currentUnitVector, speed)
    }

    fun fallAsleep(){
        if(this.state != State.ASLEEP){
            asleepSprite = Sprite(DefaultTextureHandler.getTexture("zzz.png"))
            asleepSprite.setPosition(this.currentPosition().x, currentPosition().y + this.height)
            this.state = State.ASLEEP
            this.cannotMoveCount += 1
        }
    }

    fun tryToUseAbility(ability: KeyAbility){
        if(state == State.NORMAL){
            AbilityManager.abilities.add(ability)
        }
    }

    override fun render(batch: SpriteBatch) {
        super.render(batch)
        if(this.state == State.ASLEEP){
            asleepSprite.draw(batch)
        }
    }
}