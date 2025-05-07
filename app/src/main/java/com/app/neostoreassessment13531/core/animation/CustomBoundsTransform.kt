package com.app.neostoreassessment13531.core.animation

import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.ArcMode
import androidx.compose.animation.core.ExperimentalAnimationSpecApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalAnimationSpecApi::class)
val customBoundsTransform = BoundsTransform { initialBounds, targetBounds ->
    keyframes {
        durationMillis = 1000
        initialBounds at 0 using ArcMode.ArcBelow using FastOutSlowInEasing
        targetBounds at 1000
    }
}