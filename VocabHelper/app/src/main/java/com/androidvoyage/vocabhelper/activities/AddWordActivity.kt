package com.androidvoyage.vocabhelper.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.androidvoyage.vocabhelper.R
import com.androidvoyage.vocabhelper.database.AppDatabase
import com.androidvoyage.vocabhelper.model.WordData
import kotlinx.android.synthetic.main.activity_add_word.*
import kotlinx.android.synthetic.main.content_add_word.*
import java.util.*

class AddWordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)
        setSupportActionBar(toolbar)

        getIntentData()

        fab.setOnClickListener { view ->
            val wordData = WordData()

            if (tvWord.text.toString() != null && !tvWord.text.toString().equals(""))
                wordData.word = tvWord.text.toString()
            else {
                Toast.makeText(this, "Enter Word ", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (tvMeaning.text.toString() != null && !tvMeaning.text.toString().equals(""))
                wordData.wordMeaning = tvMeaning.text.toString()
            else {
                Toast.makeText(this, "Enter meaning ", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            wordData.wordSynonyms = tvSynonyms.text.toString()
            wordData.sentenceWithWord = tvSentence.text.toString()
            wordData.isDone = false
            wordData.createdAt = Date().time
            AppDatabase.getAppDatabase(this).wordsDao().insertWord(wordData)
            finish()
        }
    }

    private fun getIntentData() {
        val intent = getIntent()

        if (intent.extras != null && intent.extras.containsKey("WordId")) {
            val wordId = intent.extras.getLong("WordId")
            val wordData = AppDatabase.getAppDatabase(this).wordsDao().getWordsByID(wordId)

            tvWord.setText(wordData.word)
            tvMeaning.setText(wordData.wordMeaning)
            tvSynonyms.setText(wordData.wordSynonyms)
            tvSentence.setText(wordData.sentenceWithWord)
        }
    }
}
