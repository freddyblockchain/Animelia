package com.mygdx.game.DialogueSystem

enum class SpeakableEntity{BROTHER, BUTLER, DOG, FATHER, MOTHER, PLAYER}
data class Dialogue(
    val Text: String,
    val Context: String
)

data class Sentence(
    val Text: String,
    val speakableEntity: SpeakableEntity
)

fun convertSpeakableEntity(articyString: String): SpeakableEntity{
    return when(articyString){
        "Player" -> SpeakableEntity.PLAYER
        "Butler" -> SpeakableEntity.BUTLER
        "Mother" -> SpeakableEntity.MOTHER
        "Father" -> SpeakableEntity.FATHER
        "Dog" -> SpeakableEntity.DOG
        "Brother" -> SpeakableEntity.BROTHER
        else -> SpeakableEntity.PLAYER
    }
}

interface Speakable