package com.mygdx.game

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Animation.TextAnimation
import com.mygdx.game.Collition.InputCollision
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Items.Material
import com.mygdx.game.Items.getMaterialTexture
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Rendering.Renderable
import com.mygdx.game.Utils.RenderGraph

class ShopItem(gameObjectData: GameObjectData, textureString: String, val costItems: List<Pair<Int, Material>>): GameObject(gameObjectData) {
    override val layer = Layer.ONGROUND
    override val texture = DefaultTextureHandler.getTexture(textureString)
    override val collision = ShopItemCollision(this)
}

class ShopItemBox(val costItems: List<Pair<Int, Material>>, position: Vector2): Renderable {
    override val layer = Layer.FOREGROUND
    val font = FontManager.SmallFont
    val texture = DefaultTextureHandler.getTexture("black-box.png")
    val sprite = Sprite(texture)

    init {
        sprite.setSize(64f,costItems.size * 32f)
        sprite.setPosition(position.x, position.y)
    }

    fun getColor(cost: Pair<Int, Material>): Color{
        val amountOfMaterials = generalSaveState.inventory.materialItems[cost.second]
        if(amountOfMaterials != null && amountOfMaterials >= cost.first){
            return Color.GREEN
        } else{
            return Color.RED
        }
    }

    override fun render(batch: SpriteBatch) {
        sprite.draw(batch)
        var offsetY = 0f
        for(cost in costItems){
            font.color = getColor(cost)
            val yPos = sprite.y + 32 - offsetY
            font.draw(batch, cost.first.toString(), sprite.x + 8f, yPos + 24f)
            val material = getMaterialTexture(cost.second)
            batch.draw(material, sprite.x + 32f, yPos)
            offsetY += 32
        }
        font.color = Color.WHITE
    }

}

class ShopItemCollision(val shopItem: ShopItem): InputCollision() {
    val shopItemPos = shopItem.currentPosition()
    val shopItemBox = ShopItemBox(shopItem.costItems, Vector2(shopItemPos.x + 32f, shopItem.y))
    override fun renderKeycodeToPress() {
        super.renderKeycodeToPress()
        RenderGraph.addToSceneGraph(shopItemBox)
    }
    override val insideText = "BUY"

    fun canBuy(costItems: List<Pair<Int, Material>>): Boolean{
        return costItems.all {
            val amount = generalSaveState.inventory.materialItems[it.second]
            (amount != null && amount >= it.first) }
    }

    override fun collisionHappened(collidedObject: GameObject) {

        if(canBuy(shopItem.costItems)){
            println("hello")

        } else {
            val textAnimation = TextAnimation(Color.RED, "You do not have the required materials", Vector2(shopItem.currentMiddle.x - 150f, shopItem.currentMiddle.y + 96f), false)
            if(!AnimationManager.animationManager.any { it is TextAnimation && textAnimation.text.startsWith("You do not")}){
                AnimationManager.animationManager.add(textAnimation)
            }
        }
    }

}