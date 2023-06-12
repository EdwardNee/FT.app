package app.ft.ftapp.android.utils

import app.ft.ftapp.utils.BaseListener
import java.time.LocalDateTime

/**
 * Interface to run the notification.
 */
interface OnStartTimerNotification: BaseListener {
    /**
     * Runs the [AlarmManager] to start notification in a particular time.
     */
    fun onSetNotification(date: LocalDateTime?)

    fun onCancelNotification()
}