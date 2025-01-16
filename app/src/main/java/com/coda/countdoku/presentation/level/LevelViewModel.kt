package com.coda.countdoku.presentation.level

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.coda.countdoku.data.local.dao.GameMetaDataDao
import com.coda.countdoku.models.GameLevel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LevelViewModel(application: Application) : AndroidViewModel(application) {
    private val _gameLevel = MutableStateFlow<List<GameLevel>>(emptyList())
    val gameLevel: StateFlow<List<GameLevel>> = _gameLevel

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