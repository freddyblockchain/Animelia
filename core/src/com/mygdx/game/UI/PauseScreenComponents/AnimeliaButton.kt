package com.mygdx.game.UI.PauseScreenComponents
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.mygdx.game.UI.Screens.UIScreen

class AnimeliaButton(text: String, labelStyle: LabelStyle, val screen: UIScreen, val activeIndex: Int): Label(text, labelStyle) {
    init {
        this.setFontScale(0.5f)
        this.fontScaleX = 0.5f
        this.color = Color.WHITE
        val thisInstance = this
        this.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                if(activeIndex != screen.activeButtonIndex){
                    screen.changeActive(activeIndex)
                    screen.activeButton = thisInstance
                }
            }
        })

    }
}