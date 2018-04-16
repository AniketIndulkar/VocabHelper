package com.androidvoyage.vocabhelper.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.androidvoyage.vocabhelper.R
import android.app.PendingIntent
import com.androidvoyage.vocabhelper.activities.HomeActivity
import android.support.v4.app.NotificationManagerCompat
import com.androidvoyage.vocabhelper.database.AppDatabase
import com.androidvoyage.vocabhelper.model.WordData
import java.util.*


class ShowWordService : Service() {

    var handler = Handler()
    var count: Int = 0

    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        handler.postDelayed(object : Runnable {
            override fun run() {

                getWord()
                Log.d("Runnnin", "Runnin " + count)
                count++
                handler.postDelayed(this, 15 * 60 * 1000) //now is every 15 minutes
            }

        }, 15 * 60 * 1000)

        return super.onStartCommand(intent, flags, startId)
    }

    private fun getWord() {
        var listOfWords = AppDatabase.getAppDatabase(this).wordsDao().getAllWords();
        if (listOfWords != null && listOfWords.size > 0) {
            val random = Random()
            val no = random.nextInt(listOfWords.size - 0 + 1) + 0
            showNotification(listOfWords[no])
        }
    }

    private fun showNotification(data: WordData) {

        // Create an explicit intent for an Activity in your app
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val mBuilder = NotificationCompat.Builder(this, "1111")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(data.word)
                .setContentText(data.wordMeaning)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(1111, mBuilder.build())
    }

}