package com.coda.countdoku.data.local.dao

import android.content.Context
import android.database.Cursor
import com.coda.countdoku.data.local.DatabaseHelper
import com.coda.countdoku.models.GameLevel

class GameMetaDataDao(private val context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun getAllLevel(): List<GameLevel> {
        val db = dbHelper.openDatabase()
        val cursor: Cursor = db.query("GameMetadata",null,null,null,null,null,null)
        val gameLevelList = mutableListOf<GameLevel>()

        if (cursor.moveToFirst()){
            do {
                val gameLevel = GameLevel(
                    level = cursor.getInt(cursor.getColumnIndexOrThrow("level")),
                    totalPuzzles = cursor.getInt(cursor.getColumnIndexOrThrow("totalPuzzles"))
                )
                gameLevelList.add(gameLevel)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return gameLevelList
    }
}