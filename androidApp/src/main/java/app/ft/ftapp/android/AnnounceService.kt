package app.ft.ftapp.android

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class AnnounceService : Service() {

    override fun onCreate() {
        super.onCreate()

        val builder = NotificationCompat.Builder(this, AnnounceRemainderReceiver.CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setAutoCancel(true)
            .setOngoing(true)

        startWorker()
        startForeground(1, builder.build())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        startWorker()
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun startWorker() {
        val delayDuration = 10000L// Calculate the delay duration based on the desired time

        val data = Data.Builder()
            .putString(AnnounceReminderWorker.KEY_BUS_NAME, "Bus A")
            .putString(AnnounceReminderWorker.KEY_DEPARTURE_TIME, "10:00 AM")
            .build()

        val constraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<AnnounceReminderWorker>()
            .setInputData(data)
            .setConstraints(constraints)
            .setInitialDelay(delayDuration, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }
}