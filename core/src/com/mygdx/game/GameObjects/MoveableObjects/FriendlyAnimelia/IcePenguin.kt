package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.GameObjectData
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.AnimeliaRecruitmendCondition
import com.mygdx.game.Animelia.FriendlyAnimelia
import com.mygdx.game.EntityRefData
import com.mygdx.game.GameObjects.Structures.Library
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Inventory.Inventory
import com.mygdx.game.UI.Conversation.Conversation
import com.mygdx.game.UI.Conversation.SpeechData
import com.mygdx.game.generalSaveState
import com.mygdx.game.plus

class IcePenguin(gameObjectData: GameObjectData, cityPosEntityId: EntityRefData) : FriendlyAnimelia(gameObjectData,
    cityPosEntityId
) {
    override val animeliaEntity = ANIMELIA_ENTITY.IcePenguin

    val speech1 = SpeechData("Me", "Do you wanna come to the city?")
    val speech2 = SpeechData("Ice Penguin", "I'm a researcher! I came out here to research hidden books")
    val speech3 = SpeechData("Ice Penguin", "And I haven't found any yet! Can you help me find one?")
    val speech4 = SpeechData("Me", "Sure thing!")

    val citySpeech1 = SpeechData("Ice Penguin", "You did it!")
    val citySpeech2 = SpeechData("Ice Penguin", "You found a book!")
    val citySpeech3 = SpeechData("Ice Penguin", "I'm going to the city. Please continue finding books")

    override val speeches = listOf(speech1,speech2,speech3,speech4)

    override val goingToCitySpeech = listOf(citySpeech1, citySpeech2, citySpeech3)

    val inCitySpeech = SpeechData("Ice Penguin", "I've recreated the library!")
    val inCitySpeech2 = SpeechData("Ice Penguin", "I've really missed this place, and the scent of books")
    val inCitySpeech3 = SpeechData("Ice Penguin", "I get so excited talking about books... bring me more!")
    val inCitySpeech4 = SpeechData("Me", "Books really gets you going huh?")
    val inCitySpeech5 = SpeechData("Ice Penguin", "Yes! Talk to me again to learn more about the library")
    override val inCitySpeeches = listOf(inCitySpeech, inCitySpeech2, inCitySpeech3, inCitySpeech4, inCitySpeech5)

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
        val firstArea = AreaManager.getArea("World1")
        this.setPosition(cityPosition.currentPosition())
        firstArea.gameObjects.add(this)
        val library = Library(GameObjectData(x = cityPosition.x.toInt() + this.width.toInt(), y=cityPosition.y.toInt(), width = 64, height = 64))
        library.setPosition(cityPosition.currentPosition() + Vector2(this.width * 1.5f, 0f))
        firstArea.gameObjects.add(library)
    }

    init {
        this.rotateByAmount(180f)
        animeliaRecruitmentConditions.add(AmountOfBooksGotten(1))
    }
}

class AmountOfBooksGotten(val amount: Int): AnimeliaRecruitmendCondition {
    override fun isConditionFulfilled(): Boolean {
        return generalSaveState.inventory.entityBooks.size >= amount
    }
}