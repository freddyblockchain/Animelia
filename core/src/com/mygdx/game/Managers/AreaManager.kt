package com.mygdx.game.Managers

import com.mygdx.game.BaseClasses.Area

class AreaManager {
    companion object {
        val areas = mutableListOf<Area>()
        var activeArea: Area? = null
    }
}