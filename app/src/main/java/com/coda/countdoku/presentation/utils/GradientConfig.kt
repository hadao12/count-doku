package com.coda.countdoku.presentation.utils

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun getGradientForLevel(level: Int): Brush {
    val gradientData = when (level) {
        1 -> {
            Pair(
                listOf(Color(0xFFCFD9A9), Color(0xFF99CDA3), Color(0xFF1E897F)),
                listOf(0f, 0.3f, 1f)
            )
        }

        2 -> {
            Pair(
                listOf(Color(0xFFDEF9FF), Color(0xFF85A4DF), Color(0xFF378BA6), Color(0xFF0E40C2)),
                listOf(0f, 0.3f, 1f, 1f)
            )
        }

        3 -> {
            Pair(
                listOf(Color(0xFFFFDEFE), Color(0xFF9285DF), Color(0xFF5437A6)),
                listOf(0f, 0.6f, 1f)
            )
        }

        else -> {
            Pair(
                listOf(Color(0xFFEBFF9B), Color(0xFF8ED1D1), Color(0xFFBB79D2)),
                listOf(0f, 0.5f, 1f)
            )
        }
    }

    return createGradientWithColorsAndPercentages(
        colors = gradientData.first,
        percentages = gradientData.second
    )
}

