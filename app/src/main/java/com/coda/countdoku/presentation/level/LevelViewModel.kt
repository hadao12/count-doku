package com.coda.countdoku.presentation.level

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
class LevelViewModel @Inject constructor(
    private val progressManager: PlayerProgressManager,
    private val gameMetaDataDao: GameMetaDataDao
): ViewModel() {

    private val _uiState = MutableStateFlow(LevelUiState())
    val uiState = _uiState.asStateFlow()

    init {
        initializeLevelData()
    }

    fun onEvent(event: LevelUiAction ){
        when(event){
            LevelUiAction.RefreshLevel -> {
                initializeLevelData()
            }
        }
    }

    private fun initializeLevelData() {
        viewModelScope.launch {
            val currentLevel = progressManager.getCurrentLevel()
            val gameLevelList = gameMetaDataDao.getAllLevel()
            val currentTotalPuzzle = gameLevelList
                .find { it.level == currentLevel }
                ?.totalPuzzles ?: 0
            _uiState.value = LevelUiState(
                currentLevel = currentLevel,
                gameLevelList = gameLevelList,
                currentTotalPuzzle = currentTotalPuzzle
            )
        }
    }
}