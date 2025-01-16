package com.coda.countdoku.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream

class DatabaseHelper (private val context: Context) {
    companion object {
        private val DB_NAME = "levels.db"
    }
    fun openDatabase(): SQLiteDatabase{
        val dbFile = context.getDatabasePath(DB_NAME)
        val file = File(dbFile.toString())
        Timber.tag("DB").e(dbFile.toString())
        if (file.exists()){
            Timber.tag("DB").e("File exists!")
        } else {
            copyDatabase(dbFile)
        }
        return SQLiteDatabase.openDatabase(dbFile.path, null, SQLiteDatabase.OPEN_READWRITE)
    }

    private fun copyDatabase(dbFile: File?) {
        val openDB = context.assets.open(DB_NAME)
        val outputStream = FileOutputStream(dbFile)
        val buffer = ByteArray(1024)
        while (openDB.read(buffer)> 0) {
            outputStream.write((buffer))
            Timber.tag("DB").e("Writing")
        }
        outputStream.flush()
        outputStream.close()
        openDB.close()
        Timber.tag("DB").e("Copy DB Complete")
    }
}