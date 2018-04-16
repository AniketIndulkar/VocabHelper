package com.androidvoyage.vocabhelper.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "Words")
class WordData {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var wordId: Long = 0

    @ColumnInfo(name = "Word")
    var word: String = ""

    @ColumnInfo(name = "Meaning")
    var wordMeaning: String = ""

    @ColumnInfo(name = "Synonyms")
    var wordSynonyms: String = ""

    @ColumnInfo(name = "Sentence")
    var sentenceWithWord: String = ""

    @ColumnInfo(name = "IsDone")
    var isDone: Boolean = false

    @ColumnInfo(name = "CreatedAt")
    var createdAt: Long = 0

    override fun toString(): String {
        return word + " | " + wordMeaning + " | " + wordSynonyms + " | " + sentenceWithWord
    }
}