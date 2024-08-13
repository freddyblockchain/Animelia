package com.mygdx.game.Managers

import com.badlogic.gdx.audio.Music

class MusicManager {
    companion object {
        var currentTrack: Music? = null

        private fun stopTrack() {
            currentTrack?.stop()
            currentTrack?.dispose()
        }

        private fun changeTrack(music: Music) {
            currentTrack = music
        }

        private fun playMusic() {
            currentTrack!!.isLooping = true
            currentTrack!!.play()
        }

        fun changeAndPlay(music: Music) {
           /* if (music != currentTrack) {
                stopTrack()
                changeTrack(music)
                currentTrack!!.volume = 0.5f
                playMusic()
            }*/
        }
    }
}