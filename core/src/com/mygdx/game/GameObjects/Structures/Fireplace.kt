package com.mygdx.game.GameObjects.Structures

import FireplaceLitSignal
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.Collition.MoveCollision
import com.mygdx.game.Collition.OnlyProjectileCollisionMask
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.MoveableObjects.Projectile.Fireball
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.Particles.AnimeliaEffect

class Fireplace(gameObjectData: GameObjectData) : GameObject(gameObjectData) {
    override val layer = Layer.ONGROUND

    var isLit = false
    var effect: ParticleEffect = ParticleEffect()
    lateinit var animeliaEffect: AnimeliaEffect

    override val collision = FireplaceCollision(this)
    override val collisionMask = OnlyProjectileCollisionMask

    override fun initObject() {
        super.initObject()
        effect.load(Gdx.files.internal("Particles/flames.p"), Gdx.files.internal("Particles"))
        animeliaEffect = AnimeliaEffect(effect)
        animeliaEffect.particleEffect.setPosition(this.currentMiddle.x, this.currentMiddle.y)
        animeliaEffect.particleEffect.start()

        if(SignalManager.pastSignals.filterIsInstance<FireplaceLitSignal>().isNotEmpty()){
            isLit = true
        }
    }

    override fun render(batch: SpriteBatch) {
        if(isLit){
            animeliaEffect.render(batch)
        }
    }
}

class FireplaceCollision(val fireplace: Fireplace): MoveCollision(){
    override var canMoveAfterCollision = true

    override fun collisionHappened(collidedObject: GameObject) {
        if(collidedObject is Fireball && !fireplace.isLit){
            SignalManager.emitSignal(FireplaceLitSignal())
            fireplace.isLit = true
        }
    }

}