package com.mygdx.game.UI.Scene2d.Screens

import com.mygdx.game.GameModes.TalkMode
import com.mygdx.game.UI.Conversation.Conversation

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.mygdx.game.GameModes.AnimationModes.CartRidingAnimationMode
import com.mygdx.game.GameModes.GameMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.Structures.Railway.Cart
import com.mygdx.game.GameObjects.Structures.Railway.Railway
import com.mygdx.game.Inventory.RailwayTransportData
import com.mygdx.game.UI.Scene2d.PauseScreenComponents.AnimeliaButton
import com.mygdx.game.UI.Scene2d.bigLabel
import com.mygdx.game.UI.Scene2d.createBackgroundDrawable
import com.mygdx.game.UI.Scene2d.mediumLabel
import com.mygdx.game.generalSaveState
import com.mygdx.game.mainMode

fun PickConversationScreen(prevMode: GameMode?, conversations: Map<String, Conversation>): PickOptionsScreen<Conversation>{
    val changeModeFunction: (Conversation) -> TalkMode = { conversation ->
        TalkMode(conversation, prevMode!!)
    }
    return PickOptionsScreen<Conversation>(prevMode, conversations, changeModeFunction)
}

fun PickRailwayScreen(prevMode: GameMode?, cart: Cart, railway: Railway): PickOptionsScreen<RailwayTransportData>{
    val changeModeFunction: (RailwayTransportData) -> CartRidingAnimationMode = { railwayTransportData ->
        CartRidingAnimationMode(railwayTransportData.areaIdentifier,prevMode!!, spriteBatch = mainMode.spriteBatch, cart, railway)
    }
    val newMap = generalSaveState.inventory.railwayConnections.map { it.areaIdentifier to it }.toMap()
    return PickOptionsScreen<RailwayTransportData>(prevMode, newMap, changeModeFunction)
}

class PickOptionsScreen<T>(override var prevMode: GameMode?, val options: Map<String, T>, val changeModeFunction: (T) -> GameMode): UIScreen() {
    override var activeButton: Actor? = null

    override var renderPrevGameMode = true
    override fun create() {
        super.create()
       rootTable.background = createBackgroundDrawable(Color(0f,0f,0f,0f))
        val newBackground = createBackgroundDrawable(Color.BLACK)

        val wholeTable = Table()
        wholeTable.setBackground(newBackground)


        options.entries.withIndex().forEach { (index, entry) ->
            val button = AnimeliaButton(entry.key, bigLabel, this, index)

            button.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    // Define what should happen when the button is clicked
                    //val talkMode = TalkMode(entry.value, prevMode!!)
                    changeMode(changeModeFunction(entry.value))
                    val id = confirmSound.play()
                    confirmSound.setVolume(id, 0.2f)
                }
            })
            buttons.add(button)
            wholeTable.add(button).pad(20f)
            wholeTable.row()
        }


        val exitButton = AnimeliaButton("Exit", bigLabel, this, options.size)
        buttons.add(exitButton)
        wholeTable.add(exitButton).pad(20f)

        activeButtonIndex = 0
        activeButton = buttons[0]

        exitButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                changeMode(prevMode!!)
                val id = backSound.play()
                backSound.setVolume(id,0.2f)
            }
        })

        rootTable.add(wholeTable).expand().center().padRight(400f).padBottom(50f)
    }
}