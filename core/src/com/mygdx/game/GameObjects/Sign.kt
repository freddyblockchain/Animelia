package com.mygdx.game.GameObjects

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collisions.DefaultAreaEntranceCollition
import com.mygdx.game.Collition.CollisionMask
import com.mygdx.game.Collition.OnlyPlayerCollitionMask
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Rendering.Renderable
import com.mygdx.game.UI.SignText
import com.mygdx.game.Utils.RenderGraph
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class Sign(gameObjectData: GameObjectData) : GameObject(gameObjectData, Vector2(gameObjectData.width.toFloat(), gameObjectData.height.toFloat())) {
    val text = Json.decodeFromJsonElement<SignCustomFields>(gameObjectData.customFields).Text
    override val texture = DefaultTextureHandler.getTexture("Sign.png")
    override val layer = Layer.ONGROUND
    override val collision = SignCollision(this)
    override val collisionMask = OnlyPlayerCollitionMask
    
}

class SignCollision(sign: Sign): DefaultAreaEntranceCollition(){
    val signText = SignText(sign.text, sign)
    override fun actionWhileInside() {
        RenderGraph.addToSceneGraph(signText)
    }

    override var canMoveAfterCollision = true

}

@Serializable
class SignCustomFields(val Text: String)