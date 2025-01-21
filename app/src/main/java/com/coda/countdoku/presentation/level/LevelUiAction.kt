package com.coda.countdoku.presentation.level

sealed class LevelUiAction {
    data object RefreshLevel : LevelUiAction()
}