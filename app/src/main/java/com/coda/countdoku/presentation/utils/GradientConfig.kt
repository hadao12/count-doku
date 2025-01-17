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

        4 -> {
            Pair(
                listOf(Color(0xFFFFDEF4), Color(0xFFDF85D6), Color(0xFF3E023B)),
                listOf(0f, 0.6f, 1f)
            )
        }

        5 -> {
            Pair(
                listOf(Color(0xFFFFBDB4), Color(0xFFDF8585), Color(0xFFCE0C00)),
                listOf(0f, 0.6f, 1f)
            )
        }

        6 -> {
            Pair(
                listOf(Color(0xFFFFD771), Color(0xFFF5B880), Color(0xFFB75A5A), Color(0xFFEF8D8D)),
                listOf(0f, 0.4f, 1f, 1f)
            )
        }

        7 -> {
            Pair(
                listOf(Color(0xFFFFE871), Color(0xFFA8CC5C), Color(0xFFABC859), Color(0xFFC1AD44)),
                listOf(0f, 0.5f, 0.5f, 0.8f)
            )
        }

        8 -> {
            Pair(
                listOf(Color(0xFF71F6FF), Color(0xFF5CCC74), Color(0xFF3C5FBA)),
                listOf(0f, 0.3f, 1f)
            )
        }

        9 -> {
            Pair(
                listOf(Color(0xFF71F6FF), Color(0xFF58B9E2), Color(0xFFBA3CAD)),
                listOf(0f, 0.3f, 1f)
            )
        }

        10 -> {
            Pair(
                listOf(Color(0xFFEBFF9B), Color(0xFF8ED1D1), Color(0xFFBB79D2)),
                listOf(0f, 0.5f, 1f)
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

