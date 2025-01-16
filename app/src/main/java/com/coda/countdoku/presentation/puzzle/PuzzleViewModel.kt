package com.coda.countdoku.presentation.puzzle

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.coda.countdoku.data.local.dao.Level1Dao
import com.coda.countdoku.models.Level1
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PuzzleViewModel(application: Application) : AndroidViewModel(application) {
    private val _lever1List = MutableStateFlow<List<Level1>>(emptyList())
    val lever1List: StateFlow<List<Level1>> = _lever1List

    init {
        fetchLever1Data()
    }

    private fun fetchLever1Data() {
        viewModelScope.launch {
            val lever1Dao = Level1Dao(getApplication())
            _lever1List.value = lever1Dao.getAllLevel1()
        }
    }
}