package com.mygdx.game.UI.Scene2d.PauseScreenComponents
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.mygdx.game.Managers.PlayerStatus
import com.mygdx.game.UI.Scene2d.createBackgroundDrawable
import com.mygdx.game.player

class StatusTable(color: Color): Table() {
    init {
        val localHeaderStyle = Label.LabelStyle(FontManager.SmallToMediumFont, Color.WHITE)
        val labelStyle = Label.LabelStyle(FontManager.ChapterFont, Color.WHITE)
        val statusText = Label("Status", labelStyle)
        this.add(statusText)
        this.background = createBackgroundDrawable(color)

        this.row()

        val combinedTable = Table()
        val animeliaTable = Table()
        val trainingTable = Table()

        val textureRegion = TextureRegion(player.animeliaInfo.gameTexture)

        // Create a TextureRegionDrawable from the TextureRegion
        val textureRegionDrawable = TextureRegionDrawable(textureRegion)
        textureRegionDrawable.setMinSize(128f,128f)

        // Create an Image from the TextureRegionDrawable
        val image = Image(textureRegionDrawable)
        val name =  player.animeliaInfo.animeliaEntity
        val stage = player.animeliaInfo.animeliaStage.name
        val types = player.animeliaInfo.elemental_types
        val typesAsString = types.toString()


        val nameFinal = Label("Name: $name", localHeaderStyle)
        val stageFinal = Label("Stage: $stage", localHeaderStyle)
        val typesFinal  = Label("Types: $typesAsString", localHeaderStyle)

        animeliaTable.add(image).expand().left().pad(50f)
        animeliaTable.row()
        animeliaTable.add(nameFinal).expand().left().pad(20f)
        animeliaTable.row()
        animeliaTable.add(stageFinal).expand().left().pad(20f)
        animeliaTable.row()
        animeliaTable.add(typesFinal).expand().left().pad(20f)

        val trainingTitle = Label("Stats Info:", localHeaderStyle)

        val trainingPoints = Label("TrainingPoints: ${PlayerStatus.tp}", localHeaderStyle)
        val offence = Label("Offence: ${player.stats.offence}", localHeaderStyle)
        val defence = Label("Defence: ${player.stats.defence}", localHeaderStyle)
        val speed = Label("Speed: ${player.stats.speed}", localHeaderStyle)
        val intelligence = Label("Intelligence: ${player.stats.intelligence}", localHeaderStyle)

        trainingTable.add(trainingPoints).pad(50f)
        trainingTable.row()
        trainingTable.add(offence).pad(20f)
        trainingTable.row()
        trainingTable.add(defence).pad(20f)
        trainingTable.row()
        trainingTable.add(speed).pad(20f)
        trainingTable.row()
        trainingTable.add(intelligence).pad(20f)

        combinedTable.add(animeliaTable).expand().top()
        combinedTable.add(trainingTable).expand()

        this.add(combinedTable).expand().fill()

    }

}