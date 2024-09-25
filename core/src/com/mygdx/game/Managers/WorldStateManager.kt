package com.mygdx.game.Managers

import com.badlogic.gdx.math.Vector2

data class RailwayTransportData(val areaIdentifier: String, val pos: Vector2)
class WorldStateManager {
    companion object {
        val railwayTransportDataList = mutableListOf<RailwayTransportData>()
    }
}