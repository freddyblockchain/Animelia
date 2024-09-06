package com.mygdx.game.UI.Scene2d

import FontManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextField

val bigLabel = Label.LabelStyle(FontManager.ChapterFont, Color.WHITE)
val smallLabel = Label.LabelStyle(FontManager.TextFont, Color.WHITE)
val mediumLabel = Label.LabelStyle(FontManager.MediumFont, Color.WHITE)
val smallToMediumLabel = Label.LabelStyle(FontManager.SmallToMediumFont, Color.WHITE)

val textFieldStyle = TextField.TextFieldStyle(FontManager.MediumFont, Color.WHITE, null, null, null)