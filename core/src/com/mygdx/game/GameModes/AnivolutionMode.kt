package com.mygdx.game.GameModes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Animation.TextAnimation
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.getAnimeliaData
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.mainCamera
import com.mygdx.game.player
import com.mygdx.game.plus
import kotlin.math.min

class AnivolutionMode(val prevMode: GameMode, val animeliaEntity: ANIMELIA_ENTITY, val isReincarnating: Boolean = false): GameMode {
    override val spriteBatch = SpriteBatch()
    override val inputProcessor = DefaultInputProcessor()
    val origZoom = mainCamera.zoom

    var currentFrame = 0
    val playerSprite =  player.sprite
    var yOffsetUp = 0
    var yoffsetDown = 0

    override fun FrameAction() {
        if(currentFrame <= 90){
            mainCamera.zoom -= 0.005f
        }
        else if(currentFrame <= 150){
            playerSprite.setRegion(0, yOffsetUp,playerSprite.width.toInt(),playerSprite.height.toInt());
            yOffsetUp += 1
        }
        else if(currentFrame == 151){
            player.animeliaInfo = getAnimeliaData(animeliaEntity)
        }
        else if(currentFrame <= 210){
            playerSprite.setRegion(0, playerSprite.height.toInt() - (min(yoffsetDown, playerSprite.height.toInt())),playerSprite.width.toInt(),playerSprite.height.toInt());
            yoffsetDown += 1
        }
        else if(currentFrame <= 270){
            mainCamera.zoom += 0.005f
        }
        spriteBatch.begin()
        player.render(spriteBatch)
        spriteBatch.end()
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
            AnimationManager.animationManager.add(textAnimation)
        }
    }
}