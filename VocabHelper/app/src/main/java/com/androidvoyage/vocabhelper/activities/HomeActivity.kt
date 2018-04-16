package com.androidvoyage.vocabhelper.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.androidvoyage.vocabhelper.R
import com.androidvoyage.vocabhelper.adapters.WordRVAdapter
import com.androidvoyage.vocabhelper.database.AppDatabase
import com.androidvoyage.vocabhelper.model.WordData

import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*
import android.app.AlarmManager
import android.content.Context.ALARM_SERVICE
import android.app.PendingIntent
import android.content.Context
import com.androidvoyage.vocabhelper.service.ShowWordService
import java.util.*


class HomeActivity : AppCompatActivity(), WordRVAdapter.ItemClickListner {

    var wordsList: List<WordData>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        fab.setOnClickListener { view ->
            startActivity(Intent(this, AddWordActivity::class.java))
        }
    }


    override fun onResume() {
        super.onResume()
        loadSavedWords()
    }

    private fun loadSavedWords() {
        wordsList = AppDatabase.getAppDatabase(this).wordsDao().getAllWordsDec()

        if (wordsList != null && wordsList!!.size > 0) {
            val rvAdapter = WordRVAdapter(this)
            rvAdapter.wordList = wordsList
            rvAdapter.listner = this
            wordsRV.layoutManager = LinearLayoutManager(this)
            wordsRV.adapter = rvAdapter
            startService(Intent(this, ShowWordService::class.java))
        } else {
            stopService(Intent(this, ShowWordService::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(position: Int, wordData: WordData) {
        val intent = Intent(this, AddWordActivity::class.java)
        intent.putExtra("WordId", wordData.wordId)
        startActivity(intent)

    }
}
