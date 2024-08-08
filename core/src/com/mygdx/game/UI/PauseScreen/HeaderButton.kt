
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.mygdx.game.DefaultTextureHandler

class HeaderButton(text: String, labelStyle: LabelStyle, val rowTable: Table): Label(text, labelStyle) {
    val myWidth = rowTable.width
    val myHeight = rowTable.height
    init {
        this.setSize(200f ,50f)
        this.setFontScale(0.5f)
        this.fontScaleX = 0.5f
        this.color = Color.WHITE
    }
}