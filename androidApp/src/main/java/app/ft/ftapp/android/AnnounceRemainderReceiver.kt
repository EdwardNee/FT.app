package app.ft.ftapp.android

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import app.ft.ftapp.domain.models.Announce

class AnnounceRemainderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Retrieve details from the intent
        val announceDetails = intent?.getParcelableExtra<Announce>("busDetails")

        println("TAG_OF_PERMIS in onReceive $announceDetails")
        createNotificationChannel(context ?: return)

        // Create a notification builder
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.become_travel)
            .setContentTitle("Напоминание о машинеaaaaa")
            .setContentText("Время начинать поездку!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        builder.setChannelId(CHANNEL_ID)
        // Add any additional notification configuration

        // Create a notification manager
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Show the notification
        notificationManager.notify(announceDetails?.id ?: 1, builder.build())
    }

    private fun createNotificationChannel(context: Context) {
        // Check if the device is running Android Oreo or above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Taxi Reminder",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            // Configure any additional channel settings
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "bus_reminders_channel"
    }
}