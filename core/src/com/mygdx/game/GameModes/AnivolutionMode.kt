package com.mygdx.game.GameModes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Animation.TextAnimation
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.getAnimeliaData
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Managers.MusicManager
import kotlin.math.min

class AnivolutionMode(val prevMode: GameMode, val animeliaEntity: ANIMELIA_ENTITY, val isReincarnating: Boolean = false): GameMode {
    override val spriteBatch = SpriteBatch()
    override fun render() {
        spriteBatch.begin()
        player.render(spriteBatch)
        spriteBatch.end()
    }

    init {
        MusicManager.currentTrack?.stop()
    }

    override val inputProcessor = DefaultInputProcessor()
    val origZoom = mainCamera.zoom

    var currentFrame = 0
    val playerSprite =  player.sprite
    var yOffsetUp = 0
    var yoffsetDown = 0
    val dissappearSound = DefaultSoundHandler.getSound("Sound/retro_beep_04.ogg")
    val appearSound = DefaultSoundHandler.getSound("Sound/retro_beep_04.ogg")



    override fun FrameAction() {
        if(currentFrame <= 90){
            mainCamera.zoom -= 0.005f
            if(currentFrame == 90){
                val id = dissappearSound.play()
                dissappearSound.setVolume(id, 0.5f)
            }
        }
        else if(currentFrame <= 150){
            playerSprite.setRegion(0, yOffsetUp,playerSprite.width.toInt(),playerSprite.height.toInt());
            yOffsetUp += 1
        }
        else if(currentFrame == 151){
            player.animeliaInfo = getAnimeliaData(animeliaEntity)
            generalSaveState.currentAnimelia = animeliaEntity
            generalSaveState.updateSaveState()

            val id = appearSound.play()
            appearSound.setVolume(id, 0.5f)
        }
        else if(currentFrame <= 210){
            playerSprite.setRegion(0, playerSprite.height.toInt() - (min(yoffsetDown, playerSprite.height.toInt())),playerSprite.width.toInt(),playerSprite.height.toInt());
            yoffsetDown += 1
        }
        else if(currentFrame <= 270){
            mainCamera.zoom += 0.005f
        }
        render()
        currentFrame += 1

        if(currentFrame > 270){
            mainCamera.zoom = origZoom
            changeMode(prevMode)
            val anivolvedText = if(isReincarnating) "reincarnated" else "anivolved"
            val textAnimation = TextAnimation(
                Color.GREEN,
                "You $anivolvedText into : " + animeliaEntity.name,
                player.currentMiddle + Vector2(0f,64f),
                false,
                240
            )
            MusicManager.currentTrack?.play()
            AnimationManager.animationManager.add(textAnimation)
            for(ability in player.activeAbilities.toMap()){
                if(ability?.value?.abilityName !in player.animeliaInfo.availableAbilities){
                    player.activeAbilities.remove(ability?.key)
                }
            }
            mainMode.abilityRowUi.updateToolTips()
        }
    }
}