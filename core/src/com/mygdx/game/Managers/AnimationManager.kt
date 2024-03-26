package com.mygdx.game.Managers

import com.mygdx.game.Animation.Animation
import com.mygdx.game.Utils.RenderGraph

class AnimationManager {
    companion object {
        val animationManager = mutableListOf<Animation>()

        fun addAnimationsToRender(){
            for(animation in animationManager.toList()){
                RenderGraph.addToSceneGraph(animation)
                animation.currentFrame++
                if(animation.currentFrame == animation.actionFrame){
                    animation.animationAction()
                }
                if(animation.currentFrame >= animation.duration){
                    animationManager.remove(animation)
                }
            }
        }
    }
}