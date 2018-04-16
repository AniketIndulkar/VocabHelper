package com.androidvoyage.vocabhelper.activities

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.androidvoyage.vocabhelper.R
import com.androidvoyage.vocabhelper.database.AppDatabase
import com.androidvoyage.vocabhelper.model.WordData
import kotlinx.android.synthetic.main.activity_add_word.*
import kotlinx.android.synthetic.main.content_add_word.*
import java.util.*

class AddWordActivity : AppCompatActivity() {

    var fromNotification: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)
        setSupportActionBar(toolbar)

        getIntentData()

        fab.setOnClickListener { view ->


            if (fromNotification) {
                if (intent.extras != null && intent.extras.containsKey("WordId")) {
                    val wordId = intent.extras.getLong("WordId")
                    val wordData = AppDatabase.getAppDatabase(this).wordsDao().getWordsByID(wordId)

                    if (!wordData.word.equals(tvWord.text.toString())) {
                        Toast.makeText(this, "Wrong word :P", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }

                    if (!wordData.wordMeaning.equals(tvMeaning.text.toString())) {
                        Toast.makeText(this, "Wrong meaning :P", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }

                    if (!wordData.wordSynonyms.equals(tvSynonyms.text.toString())) {
                        Toast.makeText(this, "Wrong synonyms :P", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }

                    fab.visibility = View.GONE
                    relativeSuccess.visibility = View.VISIBLE

                    object : CountDownTimer(2000, 100) {

                        override fun onTick(millisUntilFinished: Long) {
                            //here you can have your logic to set text to edittext
                        }

                        override fun onFinish() {
                            finish()
                        }

                    }.start()


                }
            } else {
                setDataToView()
            }
        }
    }

    private fun setDataToView() {
        val wordData = WordData()

        if (tvWord.text.toString() != null && !tvWord.text.toString().equals(""))
            wordData.word = tvWord.text.toString()
        else {
            Toast.makeText(this, "Enter Word ", Toast.LENGTH_LONG).show()
            return
        }

        if (tvMeaning.text.toString() != null && !tvMeaning.text.toString().equals(""))
            wordData.wordMeaning = tvMeaning.text.toString()
        else {
            Toast.makeText(this, "Enter meaning ", Toast.LENGTH_LONG).show()
            return
        }

        wordData.wordSynonyms = tvSynonyms.text.toString()
        wordData.sentenceWithWord = tvSentence.text.toString()
        wordData.isDone = false
        wordData.createdAt = Date().time
        AppDatabase.getAppDatabase(this).wordsDao().insertWord(wordData)
        finish()
    }

    private fun getIntentData() {
        val intent = getIntent()

        if (intent.extras != null && intent.extras.containsKey("From")) {
            headerText.setText("What was the word ?")
            fromNotification = true

        } else {
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
}
