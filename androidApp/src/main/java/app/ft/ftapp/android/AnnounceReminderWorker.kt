package app.ft.ftapp.android

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class AnnounceReminderWorker(
    private val context: Context,
    private val params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        // Get the bus information and time from the input data
        val busName = inputData.getString(KEY_BUS_NAME)
        val departureTime = inputData.getString(KEY_DEPARTURE_TIME)
        println(
            "TAG_OF_JOB ${
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }"
        )
        // Create the notification content
        val contentText = "Your bus ($busName) is departing at $departureTime."
//        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
//            .setContentTitle("Bus Reminder")
//            .setContentText(contentText)
//            .setSmallIcon(R.drawable.become_travel)
//            .build()

        // Show the notification

        createNotificationChannel(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
        }
        // Create a notification builder
        val builder = NotificationCompat.Builder(context, AnnounceRemainderReceiver.CHANNEL_ID)
            .setSmallIcon(R.drawable.become_travel)
            .setContentTitle("Напоминание о машине")
            .setContentText("Время начинать поездку!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        // Add any additional notification configuration

        // Create a notification manager
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Show the notification
        notificationManager.notify(1, builder.build())

        return Result.success()
    }

    private fun createNotificationChannel(context: Context) {
        // Check if the device is running Android Oreo or above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                AnnounceRemainderReceiver.CHANNEL_ID,
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
        const val KEY_BUS_NAME = "bus_name"
        const val KEY_DEPARTURE_TIME = "departure_time"
        const val CHANNEL_ID = "bus_reminder_channel"
        const val NOTIFICATION_ID = 0
    }
}