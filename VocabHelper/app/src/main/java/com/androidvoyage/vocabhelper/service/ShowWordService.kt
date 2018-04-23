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
import com.androidvoyage.vocabhelper.activities.AddWordActivity
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
                handler.postDelayed(this, 10000) //now is every 15 minutes
            }

        }, 10000)

        return super.onStartCommand(intent, flags, startId)
    }

    private fun getWord() {
        var listOfWords = AppDatabase.getAppDatabase(this).wordsDao().getAllWords();
        if (listOfWords != null && listOfWords.size > 1) {
            val random = Random()
            val no = random.nextInt((listOfWords.size - 1))
            showNotification(listOfWords[no])
        }else{
            showNotification(listOfWords[0])
        }
    }

    private fun showNotification(data: WordData) {

        // Create an explicit intent for an Activity in your app
        var intent = Intent(this, AddWordActivity::class.java)
        intent.putExtra("NotiWordId", data.wordId.toString())
        intent.putExtra("From", "Notification")
        intent.setAction(data.wordId.toString())
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

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