package org.austral.pocketpedia.ui.screens.profile

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import org.austral.pocketpedia.infrastructure.notification.NotificationReceiver
import javax.inject.Inject

@HiltViewModel
class ScheduleNotificationViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    @SuppressLint("ServiceCast")
    fun scheduleNotification(delayMinutes: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val triggerTime: Long = System.currentTimeMillis() + (60 * 1000 * delayMinutes).toLong()

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent
        )
    }
}