package com.coda.countdoku.presentation.puzzle

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PuzzleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow(PuzzleUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<PuzzleUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val levelSelectedToPlay = savedStateHandle.get<Int>("levelSelectedToPlay") ?: 0
        val currentTotalPuzzle = savedStateHandle.get<Int>("currentTotalPuzzle") ?: 0
        _uiState.update { it.copy(levelSelectedToPlay = levelSelectedToPlay) }
        _uiState.update { it.copy(currentTotalPuzzle = currentTotalPuzzle) }
    }


}