package com.mygdx.game.UI.Conversation

data class SpeechData(val speaker: String, val text: String)

data class Conversation(val speechDataList: List<SpeechData>)