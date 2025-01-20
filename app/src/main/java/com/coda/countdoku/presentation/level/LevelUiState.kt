package com.coda.countdoku.presentation.level

import com.coda.countdoku.models.GameLevel

data class LevelUiState(
    val currentLevel: Int = 0,
    val gameLevelList: List<GameLevel> = emptyList(),
    val levelSelectedToPlay: Int = 0,
    val currentTotalPuzzle: Int = 0
)