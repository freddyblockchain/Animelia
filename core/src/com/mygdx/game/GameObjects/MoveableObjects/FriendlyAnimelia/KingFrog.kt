package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import AnimeliaRecruitedSignal
import ChangeVisibleSignal
import RemoveObjectSignal
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Ability.Abilities.Sound.LullabyProjectile
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.AnimeliaRecruitmendCondition
import com.mygdx.game.Animelia.FriendlyAnimeliaInCity
import com.mygdx.game.Animelia.FriendlyAnimeliaInWorld
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.Collition.OnlyProjectileCollisionMask
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjects.Door
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.Structures.House
import com.mygdx.game.Items.KeyItem
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.UI.Conversation.Conversation
import com.mygdx.game.UI.Conversation.SpeechData

class KingFrog(val gameObjectData: GameObjectData, cityPosEntityId: EntityRefData,val customData: List<EntityRefData>) : FriendlyAnimeliaInWorld(gameObjectData,
    cityPosEntityId
) {
    override val animeliaEntity = ANIMELIA_ENTITY.KingFrog
    val speech1 = SpeechData("Me", "Hello, who are you?")
    val speech2 = SpeechData("Wandering Frog", "I'm a wandering frog. I just walk from place to place")
    val speech3 = SpeechData("Me", "Wait a second... you are King Frog aren't you?")
    val speech4 = SpeechData("Me", "I've been looking for you! Help me restore the kingdom!")
    val speech5 = SpeechData("Wandering Frog", "How can you call me a king? I failed to protect the kingdom")
    val speech6 = SpeechData("Wandering Frog", "And also i don't have a crown? What's a king without a crown?")

    val gspeech1 = SpeechData("Me", "I found your crown! will you help me restore the kingdom now?")
    val gspeech2 = SpeechData("King Frog", "Okay, i will admit it. I am King Frog!")
    val gspeech3 = SpeechData("King Frog", "Or i was atleast")
    val gspeech4 = SpeechData("King Frog", "I failed my kingdom. Even if i wear the crown once again..")
    val gspeech5 = SpeechData("King Frog", "I would just fail once again. ")
    val gspeech6 = SpeechData("Me", "You won't fail with me by your side. Lets take back the kingdom!")
    val gspeech7 = SpeechData("King Frog", "You seem so sure of yourself. Okay, maybe we have a chance..")
    val gspeech8 = SpeechData("King Frog", "I will return to the city. However, I will demand much help from you")
    val gspeech9 = SpeechData("King Frog", "The kingdoms fate now also rests on your shoulders, you curious being.")

    lateinit var house: House

    lateinit var house2: House

    override val goingToCitySpeech: List<SpeechData> = listOf(gspeech1,gspeech2,gspeech3,gspeech4,gspeech5,gspeech6,gspeech7,gspeech8,gspeech9)

    override var speeches = listOf(speech1,speech2,speech3,speech4,speech5,speech6)

    override fun initObject() {
        super.initObject()

        val entityRef = customData.first()
        house = AreaManager.getObjectWithIid(
            entityRef.entityIid,
            entityRef.levelIid
        ) as House

        val entityRef2 = customData[1]
        house2 = AreaManager.getObjectWithIid(
            entityRef2.entityIid,
            entityRef2.levelIid
        ) as House
    }

    override fun goingToCitySignals() {
        SignalManager.emitSignal(RemoveObjectSignal(gameObjectIid))
        SignalManager.emitSignal(RemoveObjectSignal(house.gameObjectIid))
        SignalManager.emitSignal(RemoveObjectSignal(house.door.gameObjectIid))
        SignalManager.emitSignal(ChangeVisibleSignal(house2.gameObjectIid, house2.levelId), areaIdentifier = "World1")
        SignalManager.emitSignal(
            AnimeliaRecruitedSignal(animeliaEntity, cityPosition.x, cityPosition.y),
            areaIdentifier = "FrogHouse_City"
        )
    }

    init {
        this.animeliaRecruitmentConditions.add(KingFrogRecruitment())
    }

    override fun render(batch: SpriteBatch) {
        super.render(batch)
    }
}

class KingFrogRecruitment(): AnimeliaRecruitmendCondition {
    override fun isConditionFulfilled(): Boolean {
        return generalSaveState.inventory.keyItems.contains(KeyItem.CROWN)
    }
}

class KingFrogInCity(gameObjectData: GameObjectData): FriendlyAnimeliaInCity(gameObjectData){
    override val animeliaEntity = ANIMELIA_ENTITY.KingFrog

    val inCitySpeechOne = SpeechData("Metal Bird", "Here is the Map!")
    val crown = Sprite(DefaultTextureHandler.getTexture("crown.png"))

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

    override fun initObject() {
        crown.setSize(12f,8f)
        crown.setPosition(this.topleft.x + this.width / 2 - crown.width / 2 + 1f, this.topleft.y - crown.height / 2 - 2f)
        super.initObject()
    }

    override fun recruitmentAction() {

    }

    override fun render(batch: SpriteBatch) {
        super.render(batch)
        crown.draw(batch)
    }

}

