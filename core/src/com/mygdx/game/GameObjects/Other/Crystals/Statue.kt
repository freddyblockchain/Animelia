package com.mygdx.game.GameObjects.Other.Crystals

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Collisions.CannotMoveCollision
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.minus
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class Statue(gameObjectData: GameObjectData) : GameObject(gameObjectData) {

    override val layer = Layer.ONGROUND
    override val texture = DefaultTextureHandler.getTexture("EmptyDoor.png")
    override val collision = CannotMoveCollision()
    val statueCustomFields = Json.decodeFromJsonElement<StatueCustomFields>(gameObjectData.customFields)

    val type = statueCustomFields.StatueType
    val crystalIndexes = mutableMapOf<Int,Int>()

    val crystals: MutableList<Sprite> = mutableListOf()
    val positions = getListOfCrystalPositions()

    fun addCrystal(crystal: Crystal){
        val crystalSprite = Sprite(crystal.texture)
        crystalSprite.setAlpha(0.4f)
        crystals.add(crystalSprite)

        val position = positions[crystal.index]
        crystalSprite.setPosition(position.x, position.y)

        crystalIndexes[crystal.index] = crystals.size
    }

    fun activateCrystal(crystal: Crystal){
        val crystalIndex = crystalIndexes[crystal.index]!! - 1
        crystals[crystalIndex].setAlpha(1f)

        if(crystals.all { it.color.a == 1f }){
            this.setPosition(this.currentPosition() - Vector2(64f,0f))
        }
    }

    fun animateAlpha(index: Int){
        val crystalIndex = crystalIndexes[index]!! - 1
        val sprite = crystals[crystalIndex]
        sprite.setAlpha(sprite.color.a + 0.01f)
    }

    fun getListOfCrystalPositions(): List<Vector2>{
        val pos1 = this.currentPosition() - Vector2(32f,0f)
        val pos2 = this.bottomright
        val pos3 = pos2 - Vector2(0f,32f)
        val pos4 = pos1 - Vector2(0f,32f)

        return listOf(pos1, pos2, pos3, pos4)
    }

    override fun initObject() {
        super.initObject()
        val texture = when(this.areaIdentifier){
            "Ice_castle" -> DefaultTextureHandler.getTexture("IceBirdStatue.png")
            "Vulcano" -> DefaultTextureHandler.getTexture("FireLionStatue.png")
            "Abandoned_House" -> DefaultTextureHandler.getTexture("SoundBatStatue.png")
            else -> DefaultTextureHandler.getTexture("FrostFireDragonStatue.png")
        }
        sprite.texture = texture
    }

    override fun render(batch: SpriteBatch) {
        super.render(batch)

        crystals.forEach { it.draw(batch) }
    }
}

@Serializable
class StatueCustomFields(val StatueType: String)