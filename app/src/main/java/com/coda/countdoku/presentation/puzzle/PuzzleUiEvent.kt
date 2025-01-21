package com.coda.countdoku.presentation.puzzle

sealed class PuzzleUiEvent {
    data class LevelUpdated(val newLevel: Int) : PuzzleUiEvent()
    object LevelMaxReached : PuzzleUiEvent()
    object NavigateBack : PuzzleUiEvent()
}