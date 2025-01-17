package com.coda.countdoku.presentation.level

import com.coda.countdoku.models.GameLevel

data class LevelUiState(
    val currentLevel: Int = 0,
    val gameLevels: List<GameLevel> = emptyList()
)