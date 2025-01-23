package com.coda.countdoku.presentation.pass

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coda.countdoku.data.local.dao.GameMetaDataDao
import com.coda.countdoku.data.preferences.PlayerProgressManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PassViewModel @Inject constructor(
    private val progressManager: PlayerProgressManager,
    private val gameMetaDataDao: GameMetaDataDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(PassUiState())
    val uiState = _uiState.asStateFlow()

    init {
        initializeLevelData()
    }

    private fun initializeLevelData() {
        viewModelScope.launch {
            val currentLevel = progressManager.getCurrentLevel()
            val gameLevelList = gameMetaDataDao.getAllLevel()
            val currentTotalPuzzle = gameLevelList
                .find { it.level == currentLevel }
                ?.totalPuzzles ?: 0
            _uiState.value = PassUiState(
                currentLevel = currentLevel,
                currentTotalPuzzle = currentTotalPuzzle
            )
        }
    }
}