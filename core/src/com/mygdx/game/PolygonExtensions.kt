package com.mygdx.game

import com.badlogic.gdx.math.Polygon
import com.mygdx.game.Managers.CollitionManager.Companion.getPolygonPoints

fun Polygon.anyPointInPolygon(polygon: Polygon):Boolean{
    val points = getPolygonPoints(polygon)
    for(point in points){
        if(this.contains(point)){
            return true
        }
    }
    for (point in getPolygonPoints(this)){
        if(polygon.contains(point)){
            return true
        }
    }
    return false
}