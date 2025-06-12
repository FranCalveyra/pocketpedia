package org.austral.pocketpedia.infrastructure.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import org.austral.pocketpedia.MainActivity
import org.austral.pocketpedia.R
import kotlin.random.Random

const val notificationChannelID = "pocketpedia_notification_channel"

class NotificationReceiver : BroadcastReceiver() {
    // Method called when the broadcast is received
    override fun onReceive(context: Context, intent: Intent) {
        val openAppIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context, 0, openAppIntent, PendingIntent.FLAG_IMMUTABLE
        )

        val notificationManager = context.getSystemService(NotificationManager::class.java)

        val notification = NotificationCompat.Builder(context, notificationChannelID)
            .setContentTitle(context.getString(R.string.don_t_slack_off_now))
            .setContentText(context.getString(R.string.become_a_pokemon_master_today))
            .setSmallIcon(R.drawable.pokeball)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }
}