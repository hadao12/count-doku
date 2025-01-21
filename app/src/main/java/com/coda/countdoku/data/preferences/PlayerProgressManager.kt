package com.coda.countdoku.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerProgressManager @Inject constructor(
    context: Context
) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveCurrentLevel(level: Int) {
        sharedPreferences.edit {
            putInt(KEY_CURRENT_LEVEL, level)
        }
    }

    fun getCurrentLevel(): Int {
        return sharedPreferences.getInt(KEY_CURRENT_LEVEL, DEFAULT_LEVEL)
    }

    fun updateLevel() {
        val currentLevel = getCurrentLevel()
        if (currentLevel < 21) {
            saveCurrentLevel(currentLevel + 1)
        }
    }

    companion object {
        private const val PREF_NAME = "player_progress"
        private const val KEY_CURRENT_LEVEL = "current_level"
        private const val DEFAULT_LEVEL = 1
    }
}
