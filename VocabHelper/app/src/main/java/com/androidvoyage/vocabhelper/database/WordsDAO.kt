package com.androidvoyage.vocabhelper.database

import android.arch.persistence.room.*
import com.androidvoyage.vocabhelper.model.WordData


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

    @Query("UPDATE Words SET Word =:word ,Meaning = :meaning , Synonyms =:synonyms,Sentence = :sentence WHERE id = :wordId")
    fun updateWord(word: String, meaning: String, synonyms: String, sentence: String, wordId: Long)


    @Update
    fun update(word: WordData): Int

    @Delete
    fun delete(wordData: WordData)
}