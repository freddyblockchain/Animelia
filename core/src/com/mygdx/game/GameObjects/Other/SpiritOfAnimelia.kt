package com.mygdx.game.GameObjects.Other

import com.badlogic.gdx.Input
import com.mygdx.game.Collition.InputCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameModes.UIMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.UI.Conversation.Conversation
import com.mygdx.game.UI.Conversation.SpeechData
import com.mygdx.game.UI.Scene2d.Screens.PickConversationScreen
import com.mygdx.game.mainMode

class SpiritOfAnimelia(val gameObjectData: GameObjectData): GameObject(gameObjectData) {
    override val layer = Layer.ONGROUND
    override val texture = DefaultTextureHandler.getTexture("ghost.png")

    override val collision = SpiritOfAnimeliaCollision(this)

    val speaker = "Spirit Of Animelia"
    val me = "Me"

    var one = SpeechData(speaker,"Welcome! I am the spirit of the Animelia Kingdom")
    var two = SpeechData(speaker,"This kingdom used to be a sprawling metropolis of animelias")
    var three = SpeechData(speaker,"One day, a horde of Animelia clones attacked the kingdom.")
    var four = SpeechData(speaker,"Everyone fleed, and this city was abandoned")
    var five = SpeechData(speaker,"I long to have everyone together again.")
    var six = SpeechData(speaker,"That's why I created you")
    var seven = SpeechData(me ,"You created me?")
    var eight = SpeechData(speaker,"Yes! I want you to reunite the kingdom by bringing back all the animelias")
    var nine = SpeechData(speaker,"I've given you the ability to reincarnate and anivolution")
    var ten = SpeechData(speaker,"These abilities allow you to shapeshift into animelias")
    var eleven = SpeechData(speaker,"Travel the kingdom and restore the kingdom to its former glory")
    var twelve = SpeechData(me, "Thanks for creating me! I'll do my best")

    val startConversation: Conversation = Conversation(listOf(one, two, three, four, five, six, seven, eight, nine, ten, eleven,twelve))


    val rone = SpeechData("Me", "Can you tell me more about Reincarnation?")
    val rrtwo = SpeechData(speaker, "Ofcourse! I gave you the ability to reincarnate")
    val rrrtwo = SpeechData(speaker, "Reincarnation allows you to be reborn as the base form of an animelia")
    val rtwo = SpeechData(speaker, "You do this by choosing an elemental egg, and the reincarnation begins.")
    val rthree = SpeechData(speaker, "There are four types of eggs: Fire, Fighting, Ice, and Sound")
    val rfour =  SpeechData(speaker, "Each egg lets you be reborn into a different animelia")
    val rfive =  SpeechData("me", "Okay! so i need to find these eggs, but how do i initiate the reincarnation?")
    val rsix =  SpeechData(speaker, "Reincarnation happens when you die, or if you activate it at the tombstone.")
    val reincarnationConversation = Conversation(listOf(rone, rrtwo,rrrtwo, rtwo,rthree,rfour,rfive,rsix))

    val aone = SpeechData("Me", "Can you tell me more about Anivolution?")
    val atwo = SpeechData(speaker, "Ofcourse! I gave you the ability to anivolve")
    val athree = SpeechData(speaker, "All animelia has a maturity indicator, called a stage")
    val afour =  SpeechData(speaker, "There are three stages: Junior, Master and GrandMaster")
    val aafour =  SpeechData(speaker, "All animelias except Junior animelias have conditions to fulfill")
    val afive =  SpeechData(speaker, "If you fulfill the conditions of a Master animelia")
    val asix =  SpeechData(speaker, "Then a Junior animelia will anivolve to that Master Animelia")
    val aseven =  SpeechData(speaker, "The same happens from a Master animelia to a GrandMaster animelia")
    val aeight = SpeechData(me, "Okay, I see! What stage am I at after reincarnation?")
    val anine = SpeechData(speaker, "You will always be at the Junior stage after reincarnation.")
    val anivolutionConversation = Conversation(listOf(aone,atwo,athree,afour,aafour,afive, asix, aseven, aeight, anine))
}

class SpiritOfAnimeliaCollision(val spiritOfAnimelia: SpiritOfAnimelia): InputCollision(){
    override val keyCode = Input.Keys.ENTER
    override val insideText = "TALK"

    override fun collisionHappened(collidedObject: GameObject) {
        val conversationMap = mapOf("Reincarnation" to spiritOfAnimelia.reincarnationConversation, "Anivolution" to spiritOfAnimelia.anivolutionConversation)
        val pickConversationScreen = PickConversationScreen(mainMode, conversationMap)

        changeMode(UIMode(pickConversationScreen, playConfirmationSound = true))
    }

}