package com.coda.countdoku.data.local.dao

import android.content.Context
import android.database.Cursor
import com.coda.countdoku.data.local.DatabaseHelper
import com.coda.countdoku.models.Level1

class Level1Dao(private val context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun getAllLevel1(): List<Level1> {
        val db = dbHelper.openDatabase()
        val cursor: Cursor = db.query("Level_1", null, null, null, null, null, null)
        val lever1List = mutableListOf<Level1>()

        if (cursor.moveToFirst()) {
            do {
                val lever1 = Level1(
                    unique_id = cursor.getString(cursor.getColumnIndexOrThrow("unique_id")),
                    idx = cursor.getInt(cursor.getColumnIndexOrThrow("idx")),
                    level = cursor.getInt(cursor.getColumnIndexOrThrow("level")),
                    hint = cursor.getString(cursor.getColumnIndexOrThrow("hint")),
                    numbers = cursor.getString(cursor.getColumnIndexOrThrow("numbers")),
                    target = cursor.getInt(cursor.getColumnIndexOrThrow("target"))
                )
                lever1List.add(lever1)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lever1List
    }
}