package com.mygdx.game.GameObjects.Other

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.Collition.InputCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.DialogueSystem.Sentence
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameModes.TalkMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.UI.Conversation.Conversation
import com.mygdx.game.UI.Conversation.SpeechData
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

    var activeConversation: Conversation = Conversation(listOf(one, two, three, four, five, six, seven, eight, nine, ten, eleven,twelve))
}

class SpiritOfAnimeliaCollision(val spiritOfAnimelia: SpiritOfAnimelia): InputCollision(){
    override val keyCode = Input.Keys.ENTER
    override val insideText = "TALK"

    override fun collisionHappened(collidedObject: GameObject) {
        changeMode(TalkMode(spiritOfAnimelia.activeConversation, mainMode))
    }

}