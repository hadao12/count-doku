package com.coda.countdoku.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PlayerProgressManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("player_progress", Context.MODE_PRIVATE)

    fun saveCurrentLevel(level: Int) {
        sharedPreferences.edit() {
            putInt("current_level", level)
        }
    }

    fun getCurrentLevel(): Int {
        return sharedPreferences.getInt("current_level", 1)
    }
}