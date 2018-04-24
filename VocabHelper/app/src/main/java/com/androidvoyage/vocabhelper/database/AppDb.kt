package com.androidvoyage.vocabhelper.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.androidvoyage.vocabhelper.model.WordData
import android.arch.persistence.room.Room
import android.content.Context

@Database(entities = arrayOf(WordData::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun wordsDao(): WordsDAO

    companion object {

        private var INSTANCE: AppDatabase? = null
        fun getAppDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "WordsDatabase")
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE as AppDatabase
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}