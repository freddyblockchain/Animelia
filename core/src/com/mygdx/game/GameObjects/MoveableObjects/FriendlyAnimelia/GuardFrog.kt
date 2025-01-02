package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import AnimeliaRecruitedSignal
import FireplaceLitSignal
import RemoveObjectSignal
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Ability.Abilities.Sound.LullabyProjectile
import com.mygdx.game.Animelia.*
import com.mygdx.game.Collition.CollisionMask
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.Collition.OnlyProjectileCollisionMask
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.Door
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.SoundProjectile
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.UI.Conversation.Conversation
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

    override fun goingToCitySignals(){
        SignalManager.emitSignal(RemoveObjectSignal(gameObjectIid))
        SignalManager.emitSignal(
            AnimeliaRecruitedSignal(animeliaEntity, cityPosition.x, cityPosition.y),
            areaIdentifier = "FrogHouse_City"
        )
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
        return SignalManager.pastSignals.any{it is AnimeliaRecruitedSignal && it.animeliaEntity == ANIMELIA_ENTITY.KingFrog}
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
        if(collidedObject is SoundProjectile){
            collidedObject.remove()
            guardFrog.frogState = GuardFrogState.AWAKE
            guardFrog.entrance.active = false
        }
    }
}


class GuardFrogInCity(gameObjectData: GameObjectData): FriendlyAnimeliaInCity(gameObjectData){
    override val animeliaEntity = ANIMELIA_ENTITY.GuardFrog

    val inCitySpeechOne = SpeechData("Metal Bird", "Here is the Map!")

    override val inCitySpeeches = listOf(inCitySpeechOne)

    val bone = SpeechData("Me", "Can you explain about books?")
    val bbone = SpeechData("Ice Penguin", "Certainly! I oversaw the library in this city before the clones invaded")
    val btwo = SpeechData("Ice Penguin", "When they did, all the books were scattered across the kingdom")
    val bthree = SpeechData("Ice Penguin", "Each book contains the encyclopedic knowledge of an animelia")
    val bfour = SpeechData("Ice Penguin", "I want you to find these books in the world, so my library can be restored")

    val bookConversation = Conversation(listOf( bone,bbone, btwo,bthree, bfour))

    val cone = SpeechData("Me", "Can you explain about anivolution conditions!")
    val ctwo = SpeechData("Ice Penguin", "Certainly! You can view the conditions to anivolve in the library")
    val cthree = SpeechData("Ice Penguin", "The condition will only be visible if you have the corresponding book")
    val cfour = SpeechData("Me", "Can i anivolve to any animelia, that fulfills its condition?")
    val cfive = SpeechData("Ice Penguin", "No, there must also be a direct line between the two animelias")
    val csix = SpeechData("Ice Penguin", "Check the library for anivolution information about each animelia")

    val conditionConversation = Conversation(listOf(cone, ctwo, cthree, cfour, cfive, csix))

    override val conversationOptions = mapOf("Books" to bookConversation, "Conditions" to conditionConversation)

    override fun recruitmentAction() {

    }

}

