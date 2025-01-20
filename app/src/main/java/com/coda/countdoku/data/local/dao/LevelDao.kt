package com.coda.countdoku.data.local.dao

import android.content.Context
import android.database.Cursor
import com.coda.countdoku.data.local.DatabaseHelper
import com.coda.countdoku.models.Level

class LevelDao(private val context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun getDataLevel(level: Int): List<Level> {
        val db = dbHelper.openDatabase()
        val tableName = getTableNameForLevel(level)

        val cursor: Cursor = db.query(tableName, null, null, null, null, null, null)
        val levelList = mutableListOf<Level>()

        if (cursor.moveToFirst()) {
            do {
                val levelData = Level(
                    unique_id = cursor.getString(cursor.getColumnIndexOrThrow("unique_id")),
                    idx = cursor.getInt(cursor.getColumnIndexOrThrow("idx")),
                    level = cursor.getInt(cursor.getColumnIndexOrThrow("level")),
                    hint = cursor.getString(cursor.getColumnIndexOrThrow("hint")),
                    numbers = cursor.getString(cursor.getColumnIndexOrThrow("numbers")),
                    target = cursor.getInt(cursor.getColumnIndexOrThrow("target"))
                )
                levelList.add(levelData)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return levelList
    }

    private fun getTableNameForLevel(level: Int): String {
        return when (level) {
            1 -> "Level_1"
            2 -> "Level_2"
            3 -> "Level_3"
            4 -> "Level_4"
            5 -> "Level_5"
            6 -> "Level_6"
            7 -> "Level_7"
            8 -> "Level_8"
            9 -> "Level_9"
            10 -> "Level_10"
            11 -> "Level_11"
            12 -> "Level_12"
            13 -> "Level_13"
            14 -> "Level_14"
            15 -> "Level_15"
            16 -> "Level_16"
            17 -> "Level_17"
            18 -> "Level_18"
            19 -> "Level_19"
            20 -> "Level_20"
            21 -> "Level_21"
            else -> throw IllegalArgumentException("Unknown level: $level")
        }
    }
}
