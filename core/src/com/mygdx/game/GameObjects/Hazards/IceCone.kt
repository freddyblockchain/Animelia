package com.mygdx.game.GameObjects.Hazardsimport RemoveObjectSignalimport com.badlogic.gdx.math.Vector2import com.mygdx.game.Animation.EffectAnimationimport com.mygdx.game.Collition.MoveCollisionimport com.mygdx.game.DefaultParticleHandlerimport com.mygdx.game.DefaultSoundHandlerimport com.mygdx.game.DefaultTextureHandlerimport com.mygdx.game.Enums.Layerimport com.mygdx.game.GameObjectDataimport com.mygdx.game.GameObjects.GameObject.GameObjectimport com.mygdx.game.GameObjects.MoveableObjects.Projectile.Fireballimport com.mygdx.game.GameObjects.MoveableObjects.Projectile.RockProjectileimport com.mygdx.game.Managers.AnimationManagerimport com.mygdx.game.Managers.SignalManagerimport com.mygdx.game.Particles.AnimeliaEffectclass IceCone(gameObjectData: GameObjectData)    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())) {    override val texture = DefaultTextureHandler.getTexture("IceCone.png")    override val layer = Layer.ONGROUND    override val collision = IceConeCollision(this)    val particleEffect = DefaultParticleHandler.getParticle("iceexplode.p")    val iceEffect = AnimeliaEffect(particleEffect)}class IceConeCollision(val iceCone: IceCone): MoveCollision() {    override fun collisionHappened(collidedObject: GameObject) {        if(collidedObject is RockProjectile  || collidedObject is Fireball){            val breakingSound = DefaultSoundHandler.getSound("Sound/IceShatters/IceBreaking.ogg")            val id = breakingSound.play()            breakingSound.setVolume(id,0.5f)            val removeObjectSignal = RemoveObjectSignal(iceCone.gameObjectIid)            SignalManager.emitSignal(removeObjectSignal)            activateDestroyedEffect()        }    }    override var canMoveAfterCollision = false    fun activateDestroyedEffect(){        iceCone.iceEffect.start()        iceCone.iceEffect.particleEffect.emitters.forEach { it.reset()        }        iceCone.iceEffect.particleEffect.setPosition(iceCone.currentMiddle.x, iceCone.currentMiddle.y)        val animation = EffectAnimation(iceCone.iceEffect, 60)        AnimationManager.animationManager.add(animation)    }}