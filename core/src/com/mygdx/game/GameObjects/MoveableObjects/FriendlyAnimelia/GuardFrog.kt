package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import FireplaceLitSignal
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Ability.Abilities.Sound.LullabyProjectile
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.AnimeliaRecruitmendCondition
import com.mygdx.game.Animelia.FriendlyAnimeliaInWorld
import com.mygdx.game.Animelia.FriendlyAnimeliaInWorldCollision
import com.mygdx.game.Collition.CollisionMask
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.Collition.OnlyProjectileCollisionMask
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.Door
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.UI.Conversation.SpeechData
import java.util.Vector

enum class GuardFrogState{ASLEEP,AWAKE}
class GuardFrog(val gameObjectData: GameObjectData, cityPosEntityId: EntityRefData, val customData: List<EntityRefData>) : FriendlyAnimeliaInWorld(gameObjectData,
    cityPosEntityId
) {
    override val animeliaEntity = ANIMELIA_ENTITY.GuardFrog
    val speech1 = SpeechData("", "Oh my, its so so cold here!")

    val gspeech1 = SpeechData("", "Yes, i will go to the city!")

    val spear = Sprite(DefaultTextureHandler.getTexture("spear.png"))

    override val goingToCitySpeech: List<SpeechData> = listOf(gspeech1)

    override var speeches = listOf(speech1)

    var frogState = GuardFrogState.AWAKE

    val asleepSprite = Sprite(DefaultTextureHandler.getTexture("zzz.png"))

    lateinit var spearRightPos: Vector2
    lateinit var spearStraightPos: Vector2

    lateinit var entrance : Door

    override fun initObject() {
        spear.setSize(8f,32f)
        this.setPosition(this.currentPosition() + Vector2(8f,-16f))
        spearRightPos = this.currentPosition() - Vector2(14f,-6f)
        spear.setPosition(spearRightPos.x, spearRightPos.y)

        spearStraightPos = Vector2(spearRightPos.x + 12f, spearRightPos.y)

        val entityRef = customData.first()
        entrance = AreaManager.getObjectWithIid(
            entityRef.entityIid,
            entityRef.levelIid
        ) as Door
        entrance.active = false

        asleepSprite.setPosition(this.currentPosition().x, currentPosition().y + this.height)


        //Hack to have several collisions
        val guardFrogSleepSensor = GuardFrogSleepSensor(this)
        guardFrogSleepSensor.setPosition(this.currentPosition())
        guardFrogSleepSensor.add()

        super.initObject()

    }

    init {
        this.animeliaRecruitmentConditions.add(GuardFrogRecruitment())
    }

    override fun render(batch: SpriteBatch) {
        super.render(batch)
        spear.draw(batch)

        if(this.frogState == GuardFrogState.ASLEEP){
            asleepSprite.draw(batch)
        }

        if(this.frogState == GuardFrogState.AWAKE){
            spear.rotation = 90f
            spear.setPosition(spearRightPos.x, spearRightPos.y)
        } else {
            spear.rotation = 0f
            spear.setPosition(spearStraightPos.x, spearStraightPos.y)
        }

    }
}
class GuardFrogRecruitment(): AnimeliaRecruitmendCondition {
    override fun isConditionFulfilled(): Boolean {
        return false
    }
}

class GuardFrogSleepSensor(frog: GuardFrog): GameObject(frog.gameObjectData) {
    override val layer = Layer.ONGROUND

    override fun render(batch: SpriteBatch) {

    }
    override val collisionMask = OnlyProjectileCollisionMask
    override val collision = GuardFrogCollision(frog)
}

class GuardFrogCollision(val guardFrog: GuardFrog): MoveCollision(){
    override var canMoveAfterCollision = true
    override fun collisionHappened(collidedObject: GameObject) {

        if(collidedObject is LullabyProjectile){
            collidedObject.remove()
            guardFrog.frogState = GuardFrogState.ASLEEP
            guardFrog.entrance.active = true
        }
    }
}

