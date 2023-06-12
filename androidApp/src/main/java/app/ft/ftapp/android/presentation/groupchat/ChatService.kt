package app.ft.ftapp.android.presentation.groupchat

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import app.ft.ftapp.android.R
import java.util.*

class ChatService: Service() {
    private lateinit var timer: Timer
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val notification = createNotification()
        startForeground(1, notification)


        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                println("TAG_OF_CHAT adasda")
            }
        }, 0, 1000) // listen every second

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        stopSelf()
        super.onDestroy()
        timer.cancel()
    }

    private fun createNotification(): Notification {
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel("my_channel", "My Channel")
            } else {
                ""
            }

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Message Service")
            .setContentText("Listening for messages...")
            .setSmallIcon(R.drawable.become_travel)
            .build()
    }

    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        return channelId
    }
}