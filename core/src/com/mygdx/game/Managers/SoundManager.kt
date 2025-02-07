package com.mygdx.game.Managers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.distance
import com.mygdx.game.player
import com.mygdx.game.zoomX

class SoundManager {
    companion object{
        fun playWorldSound(gameObject: GameObject, sound:Sound, pitch: Float, volume: Float){
            val distance = distance(gameObject.currentMiddle, player.currentMiddle)
            val soundDistance = Gdx.graphics.width / 2 / zoomX
            if(distance < soundDistance){
                val id = sound.play()
                sound.setPitch(id, pitch)
                val normalizedVolume = volume * (1 - (distance / soundDistance)) // Closer objects play louder
                sound.setVolume(id, normalizedVolume)
            }
        }
    }
}