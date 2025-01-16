package com.coda.countdoku.presentation.utils

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun createGradientWithColorsAndPercentages(
    colors: List<Color>,
    percentages: List<Float>,
    isVertical: Boolean = true
): Brush {
    if (colors.size != percentages.size) {
        throw IllegalArgumentException("Colors and percentages must have the same size")
    }

    val sortedStops = colors.zip(percentages).sortedBy { it.second }
    val sortedColors = sortedStops.map { it.first }
    val sortedPercentages = sortedStops.map { it.second }

    return if (isVertical) {
        Brush.verticalGradient(
            colorStops = sortedColors.zip(sortedPercentages).map { it.second to it.first }.toTypedArray()
        )
    } else {
        Brush.horizontalGradient(
            colorStops = sortedColors.zip(sortedPercentages).map { it.second to it.first }.toTypedArray()
        )
    }
}