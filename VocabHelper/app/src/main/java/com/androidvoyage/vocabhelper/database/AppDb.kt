package com.androidvoyage.vocabhelper.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Entity
import android.arch.persistence.room.RoomDatabase
import com.androidvoyage.vocabhelper.model.WordData
import android.arch.persistence.room.Room
import android.content.Context


//@Database(entities = arrayOf(WordData::class), version = 1, exportSchema = false)
//public abstract class AppDb : RoomDatabase() {
//
//    private var INSTANCE: AppDb? = null
//
//    abstract fun wordsDao(): WordsDAO
//
//    fun getAppDatabase(context: Context): AppDb {
//        if (INSTANCE == null) {
//            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDb::class.java, "WordsDb")
//                    // allow queries on the main thread.
//                    // Don't do this on a real app! See PersistenceBasicSample for an example.
//                    .allowMainThreadQueries()
//                    .build()
//        }
//        return INSTANCE as AppDb
//    }
//
//    fun destroyInstance() {
//        INSTANCE = null
//    }
//
//}


@Database(entities = arrayOf(WordData::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun wordsDao(): WordsDAO

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "WordsDatabase")
                        // allow queries on the main thread.
                        // Don't do this on a real app! See PersistenceBasicSample for an example.
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