package com.mygdx.game.GameObjects.MoveableObjects.FriendlyAnimelia

import AnimeliaRecruitedSignal
import FireplaceLitSignal
import RemoveObjectSignal
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Ability.Abilities.Sound.LullabyProjectile
import com.mygdx.game.Ability.AbilityName
import com.mygdx.game.Ability.ELEMENTAL_TYPE
import com.mygdx.game.Ability.getAbilitiesFromType
import com.mygdx.game.Ability.getIconFromType
import com.mygdx.game.Animelia.*
import com.mygdx.game.Collition.CollisionMask
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.Collition.OnlyProjectileCollisionMask
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.Door
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.SoundProjectile
import com.mygdx.game.Items.Material
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
    val speech1 = SpeechData("Guard Frog", "I've been guarding this throne room since the clones attacked")
    val speech2 = SpeechData("Me", "Can I go inside?")
    val speech3 = SpeechData("Guard Frog", "No, this is the king's room.")
    val speech4 = SpeechData("Guard Frog", "Even if the king does not regard himself as such, i will guard this place")
    val speech5 = SpeechData("Guard Frog", "I grow tired ..i have not slept since the clones attacked.")

    val gspeech1 = SpeechData("Guard Frog", "You... you convinced the king to take up his crown once again?")
    val gspeech2 = SpeechData("Me", "Yep, that was me!")
    val gspeech3 = SpeechData("Guard Frog", "... Can i come to the city too?")
    val gspeech4 = SpeechData("Guard Frog", "I can now completely fulfill my duty as a guard")
    val gspeech5 = SpeechData("Me", "Alright, i'll let you come")

    val spear = Sprite(DefaultTextureHandler.getTexture("spear.png"))

    override var speeches = listOf(speech1, speech2, speech3, speech4, speech5)

    override val goingToCitySpeech: List<SpeechData> = listOf(gspeech1,gspeech2, gspeech3, gspeech4, gspeech5)

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

    override fun goingToCityAction(){
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
    val spear = Sprite(DefaultTextureHandler.getTexture("spear.png"))
    override val animeliaEntity = ANIMELIA_ENTITY.GuardFrog

    val inCitySpeech1= SpeechData("Guard Frog", "Its good to be back here!")
    val inCitySpeech2= SpeechData("Guard Frog", "As a guard, I know how to fight!")
    val inCitySpeech3= SpeechData("Guard Frog", "I've collected these secret battle abilities")
    val inCitySpeech4= SpeechData("Guard Frog", "Please consider buying them!")

    override val inCitySpeeches = listOf(inCitySpeech1,inCitySpeech2, inCitySpeech3, inCitySpeech4)

    override fun initObject() {
        super.initObject()
        spear.setSize(8f,32f)
        spear.setOriginCenter()
        spear.rotation = 180f
        spear.setPosition(this.bottomright.x - 8f, this.bottomright.y)
    }

    override fun recruitmentAction() {
        if(AbilityName.ScrapStorm !in generalSaveState.inventory.ownedAbilities){
            val shopItem = ShopItem(texture= getIconFromType(ELEMENTAL_TYPE.METAL), costItems = listOf(Pair(2, Material.TROPICALFRUIT),Pair(1, Material.ANIMELIABONE)), gameObjectData = GameObjectData(x=this.sprite.x.toInt() - 64, y = (this.sprite.y - 32f).toInt(), width = 32, height = 32), text = "Scrap Storm") {
                generalSaveState.inventory.ownedAbilities.add(AbilityName.ScrapStorm)
                generalSaveState.updateSaveState()
            }
            shopItem.add()
        }
        if(AbilityName.AerialDeath !in generalSaveState.inventory.ownedAbilities){
            val shopItem = ShopItem(texture= getIconFromType(ELEMENTAL_TYPE.FLYING), costItems = listOf(Pair(2, Material.CANYONFRUIT),Pair(1, Material.ICEFRUIT),Pair(1, Material.ANIMELIABONE)), gameObjectData = GameObjectData(x=this.sprite.x.toInt() - 16, y = (this.sprite.y - 32f).toInt(), width = 32, height = 32), text = "Aerial Death") {
                generalSaveState.inventory.ownedAbilities.add(AbilityName.AerialDeath)
                generalSaveState.updateSaveState()
            }
            shopItem.add()
        }

        if(AbilityName.LionRoar !in generalSaveState.inventory.ownedAbilities){
            val shopItem = ShopItem(texture= getIconFromType(ELEMENTAL_TYPE.SOUND), costItems = listOf(Pair(2, Material.FORESTFRUIT),Pair(1, Material.FIREFRUIT), Pair(1,
                Material.ANIMELIABONE)), gameObjectData = GameObjectData(x=this.sprite.x.toInt() + 32, y = (this.sprite.y - 32f).toInt(), width = 32, height = 32), text = "Lion Roar") {
                generalSaveState.inventory.ownedAbilities.add(AbilityName.LionRoar)
                generalSaveState.updateSaveState()
            }
            shopItem.add()
        }
    }

    override fun render(batch: SpriteBatch) {
        super.render(batch)
        spear.draw(batch)
    }

}

