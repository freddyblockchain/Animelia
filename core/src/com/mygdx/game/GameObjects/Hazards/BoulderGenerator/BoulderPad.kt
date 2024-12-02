package com.mygdx.game.GameObjects.Hazards.BoulderGenerator

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.Collisions.DefaultAreaEntranceCollition
import com.mygdx.game.Collition.OnlyPlayerCollitionMask
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.EntityRefCustomFields
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.AnimeliaPosition
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Managers.AreaManager
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class BoulderPad(gameObjectData: GameObjectData) : GameObject(gameObjectData) {
    override val layer = Layer.ONGROUND

    val posEntityRef = Json.decodeFromJsonElement<EntityRefCustomFields>(gameObjectData.customFields).Entity_ref
    lateinit var boulderGenerator: BoulderGenerator

    override val texture = DefaultTextureHandler.getTexture("GateButton.png")
    override val collisionMask = OnlyPlayerCollitionMask

    override val collision = BoulderPadCollision(this)

    override fun initObject() {
        super.initObject()
        boulderGenerator = AreaManager.getObjectWithIid(
            posEntityRef.entityIid,
            posEntityRef.levelIid
        ) as BoulderGenerator

    }
}

class BoulderPadCollision(val boulderPad: BoulderPad): DefaultAreaEntranceCollition(){
    override var canMoveAfterCollision = true

    override fun actionWhileInside() {
        boulderPad.boulderGenerator.triggerGenerator()
    }
}