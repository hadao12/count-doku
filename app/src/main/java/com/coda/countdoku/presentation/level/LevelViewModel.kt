package com.coda.countdoku.presentation.level

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.coda.countdoku.data.local.dao.GameMetaDataDao
import com.coda.countdoku.data.preferences.PlayerProgressManager
import com.coda.countdoku.models.GameLevel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LevelViewModel(application: Application) : AndroidViewModel(application) {

    private val _gameLevel = MutableStateFlow<List<GameLevel>>(emptyList())
    val gameLevel: StateFlow<List<GameLevel>> = _gameLevel

    private val progressManager = PlayerProgressManager(application)

    private val _currentLevel = MutableStateFlow(progressManager.getCurrentLevel())
    val currentLevel: StateFlow<Int> = _currentLevel

    // Dùng bên PuzzleGameScreen
    fun updateCurrentLevel(newLevel: Int) {
        progressManager.saveCurrentLevel(newLevel)
        _currentLevel.value = newLevel
    }

    init {
        fetchGameLevelData()
    }

    private fun fetchGameLevelData() {
        viewModelScope.launch {
            val gameMetaDataDao = GameMetaDataDao(getApplication())
            _gameLevel.value = gameMetaDataDao.getAllLevel()
        }
    }
}