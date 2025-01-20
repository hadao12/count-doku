package com.coda.countdoku.presentation.puzzle

import com.coda.countdoku.models.Level

data class PuzzleUiState(
    val levelSelectedToPlay: Int = 0,
    val currentTotalPuzzle: Int = 0,
    val dataLevels: List<Level> = emptyList()
)