
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.GameObject.GameObject
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.LockedDoorData

class LockedDoor(val lockedDoorData: LockedDoorData): GameObject(Vector2(lockedDoorData.x.toFloat(), lockedDoorData.y.toFloat()), Vector2(32f,32f)) {
    override val texture = DefaultTextureHandler.getTexture("EmptyDoor.png")
    override val layer = Layer.ONGROUND
}