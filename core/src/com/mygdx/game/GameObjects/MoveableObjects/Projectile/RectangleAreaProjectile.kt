import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.CannotMoveStrategy.MoveRegardless
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.Projectile
import com.mygdx.game.setSize

class RectangleAreaProjectile(gameObjectData: GameObjectData, size: Vector2, shooter: GameObject, duration: Int, val expand: Boolean) : Projectile(gameObjectData, size, Vector2(0f,1f), shooter){
    override var speed = 0f
    override val cannotMoveStrategy = MoveRegardless()
    override val layer = Layer.ONGROUND
    override val damage = 12

    override val projectileLifespan = duration

    override fun frameTask() {
        if(expand){
            this.setSize(Vector2(this.sprite.width + 1.2f, this.sprite.height + 1.2f))
        }
        super.frameTask()
    }

    override fun render(batch: SpriteBatch) {
    }
    override var direction = Direction.DOWN
    override var canChangeDirection = true
}