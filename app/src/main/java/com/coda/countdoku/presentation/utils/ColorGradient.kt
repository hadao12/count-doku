package com.coda.countdoku.presentation.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush

fun createGradientBackground(
    colors: List<Color>,
    isVertical: Boolean = true
): Brush {
    return if (isVertical) {
        Brush.verticalGradient(colors = colors)
    } else {
        Brush.horizontalGradient(colors = colors)
    }
}