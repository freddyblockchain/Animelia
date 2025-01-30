package com.mygdx.game.GameObjects.Other

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Collition.InputCollision
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameModes.TalkMode
import com.mygdx.game.GameModes.UIMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.SignCustomFields
import com.mygdx.game.UI.Conversation.Conversation
import com.mygdx.game.UI.Conversation.SpeechData
import com.mygdx.game.UI.Scene2d.Screens.PickConversationScreen
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class SpiritOfAnimelia(val gameObjectData: GameObjectData): GameObject(gameObjectData) {
    override val layer = Layer.ONGROUND
    override val texture = DefaultTextureHandler.getTexture("ghost.png")
    val type = Json.decodeFromJsonElement<SpiritOfAnimeliaCustomFields>(gameObjectData.customFields).SpiritType

    override val collision = SpiritOfAnimeliaCollision(this)

    val speaker = "Spirit Of Animelia"
    val me = "Me"

    var one = SpeechData(speaker,"Welcome! I am the spirit of the Animelia Kingdom")
    var two = SpeechData(speaker,"I am the spirit of all animelia. I have created you")
    var three = SpeechData(speaker,"I've given you the ability to reincarnate")
    var four = SpeechData(speaker,"By finding an egg, you can be reincarnated into an animelia!")
    var five = SpeechData(speaker,"There are 4 eggs, and 4 corresponding animelia")
    var six = SpeechData(speaker,"Try grabbing the fire egg over there")
    var seven = SpeechData(speaker,"Afterwards go to the tomb to reincarnate. ")

    val startConversation: Conversation = Conversation(listOf(one, two, three, four, five, six, seven))




    val rone = SpeechData(speaker, "Let me tell you about abilities")
    val rrtwo = SpeechData(speaker, "Throughout the world, there are abilities waiting for you")
    val rrrtwo = SpeechData(speaker, "Each ability has an elemental type")
    val rtwo = SpeechData(speaker, "Each animelia has a set of abilities, that they can learn. ")
    val rthree = SpeechData(speaker, "I see there is a fireball ability over there!")
    val rfour =  SpeechData(speaker, "As you are a fire animelia, you should be able to learn it!")
    val rfive =  SpeechData(speaker, "When you grab it, press SPACE and navigate to the ability page")
    val rsix =  SpeechData(speaker, "Click on the ability. You should be able to use it pressing 1 now")
    val rseven =  SpeechData(speaker, "Maybe you can destroy the ice in front of us?")

    val second = Conversation(listOf(rone, rrtwo,rrrtwo, rtwo,rthree,rfour,rfive,rsix, rseven))

    val aone = SpeechData(speaker, "I've given you another ability: anivolution")
    val atwo = SpeechData(speaker, "If you meet certain requirements your animelia can anivolve!")
    val athree = SpeechData(speaker, "Whenever you anivolve, you get access to a new animelia with new abilities, and more training points!")
    val afour = SpeechData(speaker, "Whenever you train, you get stronger!")
    val afive =  SpeechData(speaker, "In front of us, there are rocks! These can be broken by fighting type abilities")
    val asix =  SpeechData(speaker, "We can't learn the tail swipe ability, because we are not a fighting type")
    val aseven =  SpeechData(speaker, "Let's try to anivolve! You can go to the training statue and train offence up to 15")
    val aeight = SpeechData(speaker, "If you mess up the stats, don't worry, reincarnating resets your stats.")
    val anine =  SpeechData(speaker, "After anivolving you should have access to the tail swipe ability!")
    val third = Conversation(listOf(aone,atwo,athree,afour,afive, asix, aseven, aeight, anine))

    val fone = SpeechData(speaker, "I have now prepared you to exist in this world")
    val ftwo = SpeechData(speaker, "I will now tell you why I created you")
    val fthree = SpeechData(speaker, "A year ago the animelia kingdom was attacked by clones")
    val ffour = SpeechData(speaker, "The central city of the kingdom was abandoned")
    val ffive= SpeechData(speaker, "All the animelia living in the city scattered throughout the world")
    val fsix =  SpeechData(speaker, "The king managed to seal away the source of the clones right after the attack")
    val fseven =  SpeechData(speaker, "But there are still clones remaining in the world outside the sealed gate")
    val feight =  SpeechData(speaker, "I want you to reunite the animelia kingdom, and restore the city to its former glory!")
    val fnine =  SpeechData(speaker, "Try recruiting the animelia that you can find, and also find the king!")
    val ften = SpeechData(speaker, "Good luck! I will transport you to the central city")

    val endConvo = Conversation(listOf(fone, ftwo, fthree,ffour, ffive, fsix, fseven, feight, fnine, ften))

    val conversation = when(type){
        "One" -> startConversation
        "Two" -> second
        "Three"-> third
        else -> endConvo
    }
}
fun changeToMainArea(){
    changeArea(Vector2(120f,-200f), "World1")
}

class SpiritOfAnimeliaCollision(val spiritOfAnimelia: SpiritOfAnimelia): InputCollision(){
    override val keyCode = Input.Keys.ENTER
    override val insideText = "TALK"

    override fun collisionHappened(collidedObject: GameObject) {
        /*val conversationMap = mapOf("Reincarnation" to spiritOfAnimelia.reincarnationConversation, "Anivolution" to spiritOfAnimelia.anivolutionConversation)
        val pickConversationScreen = PickConversationScreen(mainMode, conversationMap)

        changeMode(UIMode(pickConversationScreen, playConfirmationSound = true))*/

        val conversation = spiritOfAnimelia.conversation

        if(spiritOfAnimelia.type == "Four"){
            changeMode(TalkMode(conversation, mainMode){ changeToMainArea() })
        }else{
            changeMode(TalkMode(conversation, mainMode))
        }
    }

}
@Serializable
class SpiritOfAnimeliaCustomFields(val SpiritType: String)