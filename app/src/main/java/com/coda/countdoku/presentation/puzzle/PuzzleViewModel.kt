package com.coda.countdoku.presentation.puzzle

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.coda.countdoku.data.local.dao.LevelDao
import com.coda.countdoku.data.preferences.PlayerProgressManager
import com.coda.countdoku.domain.usecase.GenerateQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PuzzleViewModel @Inject constructor(
    private val generateQuestionsUseCase: GenerateQuestionsUseCase,
    savedStateHandle: SavedStateHandle,
    private val levelDao: LevelDao,
    private val playerProgressManager: PlayerProgressManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(PuzzleUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<PuzzleUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val levelSelectedToPlay = savedStateHandle.get<Int>("levelSelectedToPlay") ?: 0
        val currentTotalPuzzle = savedStateHandle.get<Int>("currentTotalPuzzle") ?: 0
        _uiState.update {
            it.copy(levelSelectedToPlay = levelSelectedToPlay, currentTotalPuzzle = currentTotalPuzzle)
        }
        loadLevelData()
    }

    private fun loadLevelData() {
        val levelSelectedToPlay = _uiState.value.levelSelectedToPlay
        val dataLevels = levelDao.getDataLevel(levelSelectedToPlay)

        val selectedQuestions = generateQuestionsUseCase(
            level = levelSelectedToPlay,
            dataLevels = dataLevels,
            totalQuestions = _uiState.value.currentTotalPuzzle
        )

        _uiState.update { it.copy(dataLevels = dataLevels, selectedQuestions = selectedQuestions) }

    }

    fun updateLevel() {
        playerProgressManager.updateLevel()
        val newLevel = playerProgressManager.getCurrentLevel()
        if (newLevel < 21) {
            _uiEvent.trySend(PuzzleUiEvent.LevelUpdated(newLevel))
        } else {
            _uiEvent.trySend(PuzzleUiEvent.LevelMaxReached)
        }
    }

}