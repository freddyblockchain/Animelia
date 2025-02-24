package com.mygdx.game.GameObjects.Structures

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.*
import com.mygdx.game.Collisions.DefaultAreaEntranceCollition
import com.mygdx.game.Collition.InputCollision
import com.mygdx.game.Collition.OnlyPlayerCollitionMask
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameModes.UIMode
import com.mygdx.game.GameModes.changeMode
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Particles.AnimeliaEffect
import com.mygdx.game.UI.Scene2d.Screens.DialogScreen
import com.mygdx.game.UI.Scene2d.Screens.ReincarnationScreen

class Fountain(val gameObjectData: GameObjectData)
    : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(),gameObjectData.height.toFloat())) {
    override val texture = DefaultTextureHandler.getTexture("toomstone.png")
    override val layer = Layer.ONGROUND
    override val collision = FountainCollision(this)

    override fun initObject() {
        super.initObject()
        val fountainHealingObject = FountainHealingObject(this)
        fountainHealingObject.add()
    }
}

class FountainCollision(val fountain: Fountain): InputCollision(){
    override val keyCode = Input.Keys.ENTER
    override val insideText = "REINCARNATE"

    override fun collisionHappened(collidedObject: GameObject) {
        if(generalSaveState.inventory.eggs.size > 0){
            generalSaveState.lastReincarnationEntityId = fountain.gameObjectIid
            generalSaveState.lastReincarnationLevelId = fountain.levelId
            generalSaveState.updateSaveState()

            val reincarnationMode = UIMode(ReincarnationScreen(mainMode))
            val dialogMode = UIMode(DialogScreen(currentGameMode, reincarnationMode,"Do you want to Reincarnate?"), playConfirmationSound = false)
            changeMode(dialogMode)
        }
    }

}

class FountainHealingObject(fountain: Fountain): GameObject(fountain.gameObjectData){
    override val layer = Layer.AIR
    override val collision = FountainHealingObjectCollision(this)

    override val collisionMask = OnlyPlayerCollitionMask

    var effect: ParticleEffect = ParticleEffect()
    var animeliaEffect: AnimeliaEffect
    var renderParticle = false

    init {
        effect.load(Gdx.files.internal("Particles/health.p"), Gdx.files.internal("Particles"))
        animeliaEffect = AnimeliaEffect(effect)
        animeliaEffect.particleEffect.setPosition(this.currentMiddle.x, this.topleft.y)
        animeliaEffect.particleEffect.start()
    }

    override fun render(batch: SpriteBatch) {
        if(renderParticle && player.currentHealth < player.maxHealth){
            animeliaEffect.particleEffect.setPosition(player.currentMiddle.x, player.sprite.y + player.sprite.height  / 2)
            animeliaEffect.render(batch)
        }
    }


}

class FountainHealingObjectCollision(val fountainHealingObject: FountainHealingObject): DefaultAreaEntranceCollition(){
    override var canMoveAfterCollision = true

    override fun movedInsideAction(objectEntered: GameObject) {
        super.movedInsideAction(objectEntered)
        fountainHealingObject.renderParticle = true
    }

    override fun movedOutsideAction(objectLeaved: GameObject) {
        super.movedOutsideAction(objectLeaved)
        fountainHealingObject.renderParticle = false
    }

    override fun actionWhileInside() {
        if(player.currentHealth < player.maxHealth){
            player.currentHealth += 0.25f
        }
        super.actionWhileInside()
    }

}