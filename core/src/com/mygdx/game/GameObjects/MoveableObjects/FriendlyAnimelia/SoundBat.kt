package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import AnimeliaRecruitedSignal
import com.mygdx.game.*
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.AnimeliaRecruitmendCondition
import com.mygdx.game.Animelia.FriendlyAnimeliaInCity
import com.mygdx.game.Animelia.FriendlyAnimeliaInWorld
import com.mygdx.game.Items.Material
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.UI.Conversation.SpeechData

class SoundBat(gameObjectData: GameObjectData, cityPosEntityId: EntityRefData) : FriendlyAnimeliaInWorld(gameObjectData,
    cityPosEntityId
) {
    override val animeliaEntity = ANIMELIA_ENTITY.SoundBat
    val speech1 = SpeechData("SoundBat", "I am the warden of Air")
    val speech2 = SpeechData("SoundBat", "You have proven yourself worthy by reaching me")
    val speech3 = SpeechData("Me", "Do you want to come to the city?")
    val speech4 = SpeechData("SoundBat", "Yes, we must fight back to reclaim our kingdom")
    val speech5 = SpeechData("SoundBat", "Meet me in the city. I will make you stronger")

    override val goingToCitySpeech = listOf(speech1, speech2, speech3, speech4, speech5)
    override var speeches = listOf<SpeechData>()
    init {
        this.animeliaRecruitmentConditions.add(SoundBatRecruitment())
    }

    override fun goingToCityAction() {
        super.goingToCityAction()
        SignalManager.emitSignal(
            AnimeliaRecruitedSignal(animeliaEntity, cityPosition.x, cityPosition.y),
            areaIdentifier = "World2"
        )
    }
}
class SoundBatRecruitment(): AnimeliaRecruitmendCondition {
    override fun isConditionFulfilled(): Boolean {
        return true
    }
}

class SoundBatInCity(gameObjectData: GameObjectData): FriendlyAnimeliaInCity(gameObjectData){
    override val animeliaEntity = ANIMELIA_ENTITY.SoundBat

    val inCitySpeechOne = SpeechData("SoundBat", "Its good to be back!")
    val inCitySpeechTwo= SpeechData("SoundBat", "I found some dusty old books in the abandoned house")
    val inCitySpeechThree= SpeechData("SoundBat", "Do you want them? For a price ofcourse")

    override val inCitySpeeches = listOf(inCitySpeechOne, inCitySpeechTwo, inCitySpeechThree)

    override fun recruitmentAction() {
        val texture = DefaultTextureHandler.getTexture("book.png")
        if(ANIMELIA_ENTITY.KingFrog !in generalSaveState.inventory.entityBooks){
            val shopItem = ShopItem(texture = texture, costItems = listOf(Pair(2, Material.FORESTFRUIT),Pair(1, Material.TROPICALFRUIT)), gameObjectData = GameObjectData(x=this.sprite.x.toInt() - 32, y = (this.sprite.y - 32f).toInt(), width = 32, height = 32), text = "King Frog") {
                generalSaveState.inventory.entityBooks.add(ANIMELIA_ENTITY.KingFrog)
                generalSaveState.updateSaveState()
            }
            shopItem.add()
        }
        if(ANIMELIA_ENTITY.FrostFireDragon !in generalSaveState.inventory.entityBooks){
            val shopItem = ShopItem(texture = texture, costItems = listOf(Pair(1, Material.CANYONFRUIT),Pair(1, Material.FIREFRUIT), Pair(1,Material.ICEFRUIT)), gameObjectData = GameObjectData(x=this.sprite.x.toInt() + 32, y = (this.sprite.y - 32f).toInt(), width = 32, height = 32), text = "Frostfire Dragon") {
                generalSaveState.inventory.entityBooks.add(ANIMELIA_ENTITY.FrostFireDragon)
                generalSaveState.updateSaveState()
            }
            shopItem.add()
        }
    }

}