package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.GameObjectData
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.AnimeliaRecruitmendCondition
import com.mygdx.game.Animelia.FriendlyAnimelia
import com.mygdx.game.EntityRefData
import com.mygdx.game.GameObjects.Structures.Library
import com.mygdx.game.GameObjects.Structures.TrainingStation
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.Inventory
import com.mygdx.game.UI.Conversation.SpeechData
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
    override fun recruitmentAction() {
        this.remove()
        val firstArea = AreaManager.getArea("World1")
        this.setPosition(cityPosition.currentPosition())
        firstArea.gameObjects.add(this)

        val library = Library(GameObjectData(x = cityPosition.x.toInt() + this.width.toInt(), y=cityPosition.y.toInt(), width = 64, height = 64))
        library.setPosition(cityPosition.currentPosition() + Vector2(this.width, 0f))
        firstArea.gameObjects.add(library)
    }

    init {
        this.rotateByAmount(180f)
        animeliaRecruitmentConditions.add(AmountOfBooksGotten(1))
    }
}

class AmountOfBooksGotten(val amount: Int): AnimeliaRecruitmendCondition {
    override fun isConditionFulfilled(): Boolean {
        return Inventory.entityBooks.size >= amount
    }
}