package com.androidvoyage.vocabhelper.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.androidvoyage.vocabhelper.model.WordData
import android.arch.persistence.room.Delete


@Dao
interface WordsDAO {

    @Query("SELECT * FROM Words")
    fun getAllWords(): List<WordData>

    @Query("SELECT * FROM Words ORDER BY CreatedAt DESC")
    fun getAllWordsDec(): List<WordData>

    @Query("SELECT * FROM Words WHERE id =:id")
    fun getWordsByID(id: Long): WordData

    @Insert
    fun insertWord(wordData: WordData)

    @Query("SELECT COUNT(*) from WORDS")
    fun getWordCount(): Int

    @Delete
    fun delete(wordData: WordData)
}